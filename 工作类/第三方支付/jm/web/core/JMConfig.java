package com.cn.jm.web.core;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.List;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.cn.jm.core.tool.JMToolString;
import com.cn.jm.core.tool.ToolDruid;
import com.cn.jm.core.utils.util.ClassScaner;
import com.cn.jm.web.core.db.JMDbSourceMeta;
import com.cn.jm.web.core.handle.CosHandler;
import com.cn.jm.web.core.router.JMRouterMapping;
import com.cn.jm.web.plugin.message.MessagePlugin;
import com.cn.jm.web.plugin.quartz.QuartzPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.ModelRecordElResolver;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.activerecord.tx.TxByMethods;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

/**
 * API引导式配置
 */
public abstract class JMConfig extends JFinalConfig {

	public static Log log = Log.getLog(JMConfig.class);
	private WallFilter wallFilter;

	@Override
	public void configEngine(Engine me) {

	}

	/**
	 * 配置常量
	 */
	public void configConstant(Constants constants) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("db.properties");
		constants.setDevMode(PropKit.getBoolean("devMode", false));
		constants.setViewType(ViewType.JSP);
		constants.setEncoding("UTF-8");
		set404XPConfig(constants);
		set500XPConfig(constants);

		constants.setJsonFactory(MixedJsonFactory.me());
	}

	/**
	 * 配置路由
	 */
	@SuppressWarnings("unchecked")
	public void configRoute(Routes routes) {
		List<Class<Controller>> controllerClassList = ClassScaner.scanSubClass(Controller.class);
		if (controllerClassList != null) {
			for (Class<?> clazz : controllerClassList) {
				JMRouterMapping urlMapping = clazz.getAnnotation(JMRouterMapping.class);
				if (null != urlMapping && JMToolString.isNotEmpty(urlMapping.url())) {
					if (StrKit.notBlank(urlMapping.viewPath())) {
						routes.add(urlMapping.url(), (Class<? extends Controller>) clazz, urlMapping.viewPath());
					} else {
						routes.add(urlMapping.url(), (Class<? extends Controller>) clazz);
					}
				}
			}
		}
	}

	// public static C3p0Plugin createC3p0Plugin() {
	// return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
	// PropKit.get("password").trim());
	// }

	public abstract List<JMDbSourceMeta> createDbSourceMeta();

	private void activeRecordPlugin(Plugins me) {
		JMConsts.dbSourceMetaList = createDbSourceMeta();
		for (int i = 0; i < JMConsts.dbSourceMetaList.size(); i++) {
			JMDbSourceMeta dbSourceMeta = JMConsts.dbSourceMetaList.get(i);
			ActiveRecordPlugin arp = createDbSource(me, dbSourceMeta.getDbConfigName(), dbSourceMeta.getUrl(),
					dbSourceMeta.getUserName(), dbSourceMeta.getPassword());
			dbSourceMeta.getMappingKit().mapping(arp);
		}
	}

	private ActiveRecordPlugin createDbSource(Plugins me, String dbConfigName, String url, String username,
			String password) {
		DruidPlugin druidPlugin = new DruidPlugin(url, username, password);
		wallFilter = new WallFilter(); // 加强数据库安全
		wallFilter.setDbType("mysql");
		druidPlugin.addFilter(wallFilter);
		druidPlugin.addFilter(new StatFilter()); // 添加 StatFilter 才会有统计数据
		me.add(druidPlugin);
		
//		druidPlugin.setMaxActive(1000);
//		druidPlugin.setMaxWait(3000);
//		

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dbConfigName, druidPlugin);
		arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
		arp.setDialect(new MysqlDialect());
		me.add(arp);

		// 所有配置在 MappingKit 中搞定
		arp.setShowSql(PropKit.getBoolean("devSql", false));
		return arp;
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		activeRecordPlugin(me);

		// // 缓存
		me.add(new EhCachePlugin());

		// 消息驱动
		me.add(new MessagePlugin());

		me.add(new QuartzPlugin());// 定时器

	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors interceptors) {
		log.info("configInterceptor 配置开启事物规则");
		interceptors.add(new Tx());
		interceptors.add(new TxByMethods("save", "update", "delete"));
		interceptors.add(new TxByMethodRegex("(.*save.*|.*update.*|.*delete.*)")); // 2.1只支持单实例添加，多方法名匹配使用一个正则匹配

		// interceptors.add(new ParamPkgInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers handlers) {
		// handlers.add(new ActionCacheHandler());
		// handlers.add(new RequestParamHandler());
		handlers.add(ToolDruid.getDruidStatViewHandler()); // druid 统计页面功能
		handlers.add(new CosHandler());
	}

	@Override
	public void afterJFinalStart() {
//		ModelRecordElResolver.setResolveBeanAsModel(true);
		// 让 druid 允许在 sql 中使用 union
		// https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
		wallFilter.getConfig().setSelectUnionCheck(false);

		JMConsts.projectName = PropKit.get("projectName");
		onJfinalStartAfter();
	}

	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
		onJfinalStopBefore();
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		if (drivers != null) {
			while (drivers.hasMoreElements()) {
				try {
					Driver driver = drivers.nextElement();
					DriverManager.deregisterDriver(driver);
				} catch (Exception e) {
					log.error("deregisterDriver error in beforeJFinalStop() method.", e);
				}
			}
		}
	}

	public abstract void set404XPConfig(Constants constants);;

	/**
	 * 
	 * @param constants
	 */
	public abstract void set500XPConfig(Constants constants);;

	/**
	 * 系统启动后
	 */
	public abstract void onJfinalStartAfter();

	/**
	 * 系统停止前
	 */
	public abstract void onJfinalStopBefore();;

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
