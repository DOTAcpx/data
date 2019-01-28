package com.cn.jm.web.core.dao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;

import com.cn.jm.core.utils.util.JMIControllerAction;
import com.cn.jm.core.utils.util.JMSnowflake;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class JMBaseDao implements JMIControllerAction {

	public static Logger log = Logger.getLogger(JMBaseDao.class);

	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private static JMSnowflake snowflake;

	public static JMSnowflake getSnowflake() {
		if (snowflake == null) {
			snowflake = new JMSnowflake(0, 0);
		}
		return snowflake;
	}

	/**
	 * 根据数据量计算分多少次批处理
	 * 
	 * @param dataSource
	 * @param sql
	 * @param batchSize
	 *            每次数据多少条
	 * @return
	 */
	public static long getBatchCount(String dataSource, String sql, int batchSize) {
		long batchCount = 0;
		long count = Db.use(dataSource).queryNumber(" select count(*) " + sql).longValue();
		if (count % batchSize == 0) {
			batchCount = count / batchSize;
		} else {
			batchCount = count / batchSize + 1;
		}
		return batchCount;
	}

	public <T> T converModel(Class<T> clazz, Record record) {
		try {
			return convertMap(clazz, record.getColumns());
		} catch (Exception e) {
			log.error("Record 转 T 异常... ", e);
		}

		return null;
	}

	private <T> T convertMap(Class<T> type, Map<String, Object> map) throws Exception {
		T obj = type.newInstance();

		// 获取类属性
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				String value = ConvertUtils.convert(map.get(propertyName));
				Object[] args = new Object[1];
				args[0] = ConvertUtils.convert(value, descriptor.getPropertyType());

				descriptor.getWriteMethod().invoke(obj, args);

			}
		}
		return obj;
	}

	public static void main(String[] args) {
		JMSnowflake snowflake = getSnowflake();
		for (int i = 0; i < 100; i++) {
			System.err.println(snowflake.nextId());
		}

	}

	@Override
	public boolean isave(Model<?> model, boolean result) {
		return result;
	}

	@Override
	public boolean isaves(List<? extends Model<?>> modelList, int[] results) {
		return false;
	}

	@Override
	public boolean iupdate(Model<?> model, boolean result) {
		return result;
	}

	@Override
	public boolean iupdates(List<? extends Model<?>> modelList, int[] results) {
		return false;
	}

	@Override
	public boolean idelete(long id, boolean result) {
		return result;
	}

	@Override
	public boolean ideletes(List<Long> ids, boolean result) {
		return result;
	}

}
