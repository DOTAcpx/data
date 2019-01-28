package com.cn.jm.web.core.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.cn.jm.core.tool.JMToolString;
import com.cn.jm.core.tool.security.JMToolMD5;
import com.cn.jm.core.utils.cache.JMCacheKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.ehcache.IDataLoader;

/**
 * 处理Sql Map
 * 
 * @author 李劲 说明：加载sql map中的sql到map中，并提供动态长度sql处理
 */
public class JMToolSql {

	protected static final Logger log = Logger.getLogger(JMToolSql.class);

	public static <T> T get(String cacheName, Model<?> model, String sql) {
		String key = JMToolMD5.getMD5(sql);
		return JMCacheKit.get(cacheName, key, new IDataLoader() {
			@Override
			public Object load() {
				return model.findFirst(sql);
			}
		});
	}

	public static <T> List<T> list(String cacheName, Model<?> model, String sql) {
		String key = JMToolMD5.getMD5(sql);
		return JMCacheKit.get(cacheName, key, new IDataLoader() {
			@Override
			public Object load() {
				return model.find(sql);
			}
		});
	}

	public static <T> Page<T> page(String cacheName, Model<?> model, int pageNumber, int pageSize, String select,
			String from) {
		String key = JMToolMD5.getMD5(pageNumber + pageSize + select + from);
		return JMCacheKit.get(cacheName, key, new IDataLoader() {
			@Override
			public Object load() {
				return model.paginate(pageNumber, pageSize, select.toString(), from);
			}
		});
	}

	public static boolean deletes(String tableName, List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return false;
		}
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				StringBuilder sql = new StringBuilder("delete from " + tableName + " where id in");
				joinIds(ids, sql);
				return Db.update(sql.toString()) > 0;
			}
		});
		return flag;
	}

	public static String format(String format, Object... values) {
		String[] mValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			mValues[i] = TransactSQLInjection(values[i].toString()).toString();
		}
		System.err.println(mValues[0] + "---" + mValues.length);
		return String.format(format, mValues);
	}

	public static void onDelete(long id, boolean result, JMMainTable mainTable, String dbName) {
		if (mainTable != null) {
			List<JMSubTable> tableList = mainTable.getSubTableNameList();
			for (int i = 0; i < tableList.size(); i++) {
				if (result) {
					JMSubTable subTable = tableList.get(i);
					int action = subTable.getAction();
					String tableName = subTable.getTableName();
					String subKeyName = subTable.getSubKeyName();

					List<Record> modelList = new ArrayList<>();
					if (JMToolString.isNotEmpty(subKeyName)) {
						modelList = Db.use(dbName)
								.find("SELECT * FROM " + tableName + " WHERE " + subKeyName + " = " + id);
					} else {
						modelList = Db.use(dbName).find(
								"SELECT * FROM " + tableName + " WHERE " + mainTable.getSubKeyName() + " = " + id);
					}

					switch (action) {
					case JMSubTable.CASCADE:
						if (modelList != null && modelList.size() > 0) {
							List<Long> ids = new ArrayList<Long>();
							for (int j = 0; j < modelList.size(); j++) {
								ids.add(modelList.get(j).getLong("id"));
							}
							result = JMToolSql.deletes(tableName, ids);
						}
						break;

					case JMSubTable.NO_ACTION:
						break;

					case JMSubTable.SET_NULL:
						if (modelList != null && modelList.size() > 0) {
							for (int j = 0; j < modelList.size(); j++) {
								if (JMToolString.isNotEmpty(subKeyName)) {
									modelList.get(j).set(subKeyName, -1000);
								} else {
									modelList.get(j).set(mainTable.getSubKeyName(), -1000);
								}
							}
							Db.use(dbName).batchUpdate(tableName, modelList, 1000);
						}
						break;

					default:
						break;
					}
				}
			}
		}
	}

	/**
	 * 参数拼装
	 * 
	 * @param param
	 * @return
	 */
	public static StringBuilder whereAND(HashMap<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		if (param != null && !param.isEmpty()) {
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = param.get(key);
				sb.append(" AND ").append(key).append(" = '").append(TransactSQLInjection(value.toString()))
						.append("'");
			}
		}
		return sb;
	}

	public static StringBuilder whereLike(String attrName, String value) {
		StringBuilder sb = new StringBuilder();
		if (JMToolString.isNotEmpty(attrName) && JMToolString.isNotEmpty(value)) {
			sb.append(" AND ").append(attrName + " LIKE '%").append(TransactSQLInjection(value.toString()))
					.append("%'");
		}
		return sb;
	}

	public static StringBuilder whereLike(HashMap<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		if (param == null || param.isEmpty()) {
			return sb;
		}
		for (Entry<String, Object> entry : param.entrySet()) {
			Object value = entry.getValue();
			sb.append(" AND ").append(entry.getKey() + " LIKE '%").append(TransactSQLInjection(value.toString()))
					.append("%'");
		}
		return sb;
	}

	public static StringBuilder whereOR(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		if (param != null && !param.isEmpty()) {
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = param.get(key);
				sb.append(" OR ").append(key).append(" = '").append(TransactSQLInjection(value.toString())).append("'");
			}
		}
		return sb;
	}

	public static void joinIds(List<Long> idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (Long id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append(TransactSQLInjection(id.toString()));
		}
		ret.append(")");
	}

	/**
	 * sql代码过滤防止sql代码注入
	 * 
	 * @param sql
	 * @return
	 */
	public static StringBuilder TransactSQLInjection(String sql) {
		return new StringBuilder(sql.replaceAll(".*([';]+|(--)+).*", " "));
	}

	/**
	 * 
	 * @Description:JMCOMMONDao put like
	 * @param whereParam
	 * @param key
	 * @param value
	 */
	public static void putLike(Map<String, Object> whereParam, String key, String value) {

		if (StringUtils.isEmpty(value)) {
			return;
		}
		whereParam.put(key + " like ", "%" + value + "%");
	}

	/**
	 * 
	 * @Description: 判断表是否存在
	 * @param tableName
	 * @return
	 */
	public static boolean tableExists(String tableName) {
		return Db.findFirst(" show tables like '" + tableName + "'") == null ? false : true;
	}

	public static String getName(Class<? extends Model<?>> clazz) {

		Table t = TableMapping.me().getTable(clazz);
		return t == null ? null : t.getName() + " ";

	}

	public static void putIn(LinkedHashMap<String, Object> whereParam, String key, String ids) {

		if (StringUtils.isEmpty(ids))
			return;

		String[] idstrs = ids.split(",");

		List<String> param = new ArrayList<>();

		if (idstrs.length == 1) {
			param.add(idstrs[0]);
			whereParam.put(key + " in ( ? )", param);
			return;
		}

		int index = 1;

		String sql = "";

		for (String idstr : idstrs) {
			if (index == 1)
				sql = key + " in ( ?";
			else
				sql += ",?";

			if (index == idstrs.length)
				sql += ")";

			param.add(idstr);

			index++;

		}

		whereParam.put(sql, param);
	}

	public static void putIn(LinkedHashMap<String, Object> whereParam, String ids) {
		putIn(whereParam, "id", ids);
	}

}
