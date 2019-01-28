package com.cn.jm.core.utils.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.cn.jm.web.core.JMConsts;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

public class JMCacheKit {

	public static void put(String cacheName, Object key, Object value) {
		ConcurrentHashMap<Object, Object> data = CacheKit.get(JMConsts.projectName, cacheName);
		if (data == null) {
			data = new ConcurrentHashMap<Object, Object>();
			data.put(key, value);
		} else {
			data.put(key, value);
		}
		CacheKit.put(JMConsts.projectName, cacheName, data);
	}

	public static void put(String cacheName, Object key, Object value, long time) {
		ConcurrentHashMap<Object, Object> data = CacheKit.get(JMConsts.projectName, cacheName);
		if (data == null) {
			data = new ConcurrentHashMap<Object, Object>();
			data.put(key, value);
		} else {
			data.put(key, value);
		}
		CacheKit.put(JMConsts.projectName, cacheName, data);
	}

	public static Set<Object> getKeys(String cacheName) {
		ConcurrentHashMap<Object, Object> data = CacheKit.get(JMConsts.projectName, cacheName);
		return data.keySet();
	}

	public static <T> T get(String cacheName, Object key) {
		ConcurrentHashMap<Object, Object> data = CacheKit.get(JMConsts.projectName, cacheName);
		return data != null ? (T) data.get(key) : null;
	}

	public static <T> T get(String cacheName, Object key, IDataLoader dataLoader) {
		Object data = get(cacheName, key);
		if (data == null) {
			data = dataLoader.load();
			if (data != null) {
				put(cacheName, key, data);
			}
		}
		return (T) data;
	}

	public static <T> T get(String cacheName, Object key, IDataLoader dataLoader, long time) {
		Object data = get(cacheName, key);
		if (data == null) {
			data = dataLoader.load();
			if (data != null) {
				put(cacheName, key, data);
			}
		}
		return (T) data;
	}

	public static void remove(String cacheName, Object key) {
		ConcurrentHashMap<Object, Object> data = CacheKit.get(JMConsts.projectName, cacheName);
		if (data != null) {
			data.remove(key);
		}
	}

	public static void removeAll(String cacheName) {
		ConcurrentHashMap<Object, Object> data = CacheKit.get(JMConsts.projectName, cacheName);
		if (data != null) {
			data.clear();
		}
	}
	
	public static void clearCache(String cacheName,Object cacheNameId) {
		removeAll(cacheName + cacheNameId);
		removeAll(cacheName);
	}

}
