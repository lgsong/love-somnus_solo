package com.somnus.solo.memcached;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
	
    private Map<String, Integer> expireMap = new HashMap<String, Integer>();   //缓存的时间  
    
    /**memcached的客户端  */
    private MemcachedClient memcachedClient;
    
    @Override  
    protected Collection<? extends Cache> loadCaches() {
        Collection<Cache> values = cacheMap.values();
        return values;
    }  
  
    @Override  
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            Integer expire = expireMap.get(name);
            if (expire == null) {
                expire = 60 * 60 * 24;
                expireMap.put(name, expire);
            }
            log.info("初始化Memcached缓存[{}]",name);
            cache = new MemcachedCache(name, expire.intValue(), memcachedClient);
            cacheMap.put(name, cache);
        }
        return cache;
    }
  
    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }  
  
    public void setExpireMap(Map<String, Integer> expireMap) {
        this.expireMap = expireMap;
    }
}
