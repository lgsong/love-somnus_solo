package com.somnus.solo.memcached;

import java.util.concurrent.Callable;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class MemcachedCache implements Cache {
	
	private final String name;
	
    private final MemCache memCache;
  
    public MemcachedCache(String name, int expire, MemcachedClient memcachedClient) {
        this.name = name;
        this.memCache = new MemCache(name, expire, memcachedClient);
    }  
  
    @Override  
    public void clear() {
        memCache.clear();
    }
  
    @Override  
    public void evict(Object key) {
        memCache.delete(key.toString());
    }
  
    @Override  
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        Object value = memCache.get(key.toString());
        if (value != null) {
            wrapper = new SimpleValueWrapper(value);
        }
        return wrapper;
    }  
  
    @Override
    public String getName() {
        return this.name;
    }
  
    @Override  
    public MemCache getNativeCache() {
        return this.memCache;  
    }
  
    @Override  
    public void put(Object key, Object value) {
        memCache.put(key.toString(), value);
    }  
  
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        Object cacheValue = this.memCache.get(key.toString());
        Object value = (cacheValue != null ? cacheValue : null);
        if (type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);  
        }
        return (T) value;
    }
    
    static class MemCache {
    	private static Logger log = LoggerFactory.getLogger(MemCache.class);
  	  
        private final String name;
        
        private final int expire;
        
        private final MemcachedClient memcachedClient;
      
        public MemCache(String name, int expire, MemcachedClient memcachedClient) {
            this.name = name;
            this.expire = expire;
            this.memcachedClient = memcachedClient;
        }  
      
        public Object get(String key) {
        	key = StringUtils.isEmpty(key) ? null : this.getKey(key);
        	if(log.isDebugEnabled()){
				log.debug("获取缓存数据{}", key);
			}
    		return memcachedClient.get(key);
    	}
    	
    	public void clean() {
    		memcachedClient.flush();
    	}
      
    	public void put(String key, Object value) {
    		if (StringUtils.isNotBlank(key)){
    			key = this.getKey(key);
    			memcachedClient.set(key, expire, value);
    			if(log.isDebugEnabled()){
    				log.debug("添加缓存{} , 过期时间={}秒", key, expire);
    			}
    		}else if(log.isDebugEnabled()){
    			log.debug("无效缓存key,忽略");
    		}
    	}
      
        public void clear() { 
        	memcachedClient.flush();
        }  
      
        public void delete(String key) {  
        	if (StringUtils.isNotBlank(key)){
    			key = this.getKey(key);
    			memcachedClient.delete(key);
    		}else if(log.isDebugEnabled()){
    			log.warn("无效缓存key,忽略");
    		}
        }  
      
        private String getKey(String key) {
            return name + "_" + key;
        }
    }

	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
