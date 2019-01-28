package com.cn.jm.core.utils.cache.core;

import java.util.List;

import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.ehcache.IDataLoader;

public interface JMICache extends ICache{

    public void put(String cacheName, Object key, Object value);
    public void put(String cacheName, Object key, Object value, long time);

    public List getKeys(String cacheName);
    
    public <T> T get(String cacheName, Object key);
	public <T> T get(String cacheName, Object key, IDataLoader dataLoader);
    public <T> T get(String cacheName, Object key, IDataLoader dataLoader, long time);
    
    public void remove(String cacheName, Object key);
    public void removeAll(String cacheName);


}
