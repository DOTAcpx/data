package com.cn.jm.core.utils.cache.core;

/**
 * 缓存类型配置
 * 
 * @author 李劲
 *
 * 开发时间：2018年6月20日
 */
public class JMCacheConfig {

	public static final String TYPE_EHCACHE = "ehcache";
	public static final String TYPE_REDIS = "redis";
	public static final String TYPE_EHREDIS = "ehredis";
	public static final String TYPE_J2CACHE = "j2cache";

	private String type = TYPE_EHCACHE;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
