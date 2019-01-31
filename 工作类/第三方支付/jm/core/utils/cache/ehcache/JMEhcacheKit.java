package com.cn.jm.core.utils.cache.ehcache;

import java.util.List;

import com.cn.jm.core.utils.cache.core.JMICache;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

public class JMEhcacheKit implements JMICache{
	
	
	@Override
	public void put(String cacheName, Object key, Object value) {
		CacheKit.put(cacheName, key, value);
		
	}

	@Override
	public void put(String cacheName, Object key, Object value, long time) {
		CacheKit.put(cacheName, key, value);
	}

	@Override
	public List getKeys(String cacheName) {
		return CacheKit.getKeys(cacheName);
	}

	@Override
	public <T> T get(String cacheName, Object key) {
		return CacheKit.get(cacheName, key);
	}

	@Override
	public <T> T get(String cacheName, Object key, IDataLoader dataLoader) {
		return CacheKit.get(cacheName, key, dataLoader);
	}

	@Override
	public <T> T get(String cacheName, Object key, IDataLoader dataLoader, long time) {
		return CacheKit.get(cacheName, key, dataLoader);
	}

	@Override
	public void remove(String cacheName, Object key) {
		CacheKit.remove(cacheName, key);
	}

	@Override
	public void removeAll(String cacheName) {
		CacheKit.removeAll(cacheName);
		
	}

}
