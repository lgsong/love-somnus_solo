package com.somnus.solo.memcached;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.somnus.solo.support.holder.ApplicationContextHolder;

public class MemCachedUtil {

	private static Logger log = LoggerFactory.getLogger(MemCachedUtil.class);

    //默认设置缓存有效时间 1 天 单位s
    public final static int        DEFAULT_CACHE_TIME = 60 * 60 * 24;   

    private static MemCachedUtil   instance              = null;

    private MemcachedClient memcachedClient;
    
    private MemCachedUtil(){}

    /**
     * 初始化 MemCacheUtil 单例 对象
     * @return
     */
    public static synchronized MemCachedUtil getInstance() {
        if (instance == null) {
        	instance = new MemCachedUtil();
        	instance.getMemCacheInstance();
        }
        return instance;
    }

    /**
     * 利用 Spring 上下文 根据beanName 获取memCahcheClient 实体
     */
    public void getMemCacheInstance() {
        if (null == memcachedClient) {
            memcachedClient = (MemcachedClient) ApplicationContextHolder.getBean("memcachedClient");
        }
    }

    /**
     * 默认设置memcache 缓存
     * @param key
     *           缓存的key
     * @param value
     *           缓存的value  
     * @return
     */
    public static void put(String key, Object value) {
    	put(key, value, DEFAULT_CACHE_TIME);
    }

    /**
     * 设置memcache 缓存包括有效时间
     * @param key
     *           缓存的key
     * @param value
     *           缓存的value
     * @param exp
     *           缓存的有效时间
     * @return 
     */
    public static void put(String key, Object value, int expire) {
        if (StringUtils.isNotBlank(key)){
        	MemCachedUtil.getInstance().memcachedClient.set(key, expire, value);
			if(log.isDebugEnabled()){
				log.debug("添加缓存{} , 过期时间={}秒", key, expire);
			}
		} else if(log.isDebugEnabled()){
			log.debug("无效缓存key,忽略");
		}
    }

    /**
     * 获取缓存对象
     *
     * @param key
     *           缓存的key
     * @return  value
     */
    public static Object get(String key) {
        Object result = null;
        if (StringUtils.isNotBlank(key)) {
            result = MemCachedUtil.getInstance().memcachedClient.get(key);
        } else if(log.isDebugEnabled()){
			log.debug("无效缓存key,忽略");
		}
        return result;
    }

    /**
     * 清空某缓存对象
     *
     * @param key
     *          缓存的key
     * @return
     */
    public static void remove(String key) {
    	if (StringUtils.isNotBlank(key)) {
    		MemCachedUtil.getInstance().memcachedClient.delete(key);
        } else if(log.isDebugEnabled()){
			log.debug("无效缓存key,忽略");
		}
    }

    /**
     * 清空所有缓存对象
     * @return
     */
    public static void clean() {
    	MemCachedUtil.getInstance().memcachedClient.flush();
    }
    
    /**
     * <pre>
     * 自动统计数量，步长为1
     * </pre>
     * @param key
     */
    public static void incr(String key) {
    	if (StringUtils.isNotBlank(key)) {
    		MemCachedUtil.getInstance().memcachedClient.incr(key, 1);
        } else if(log.isDebugEnabled()){
			log.debug("无效缓存key,忽略");
		}
    }
    
    /**
     * <pre>
     * 自动减少数量，步长为1
     * </pre>
     * @param key
     */
    public static void decr(String key) {
    	if (StringUtils.isNotBlank(key)) {
    		MemCachedUtil.getInstance().memcachedClient.decr(key, 1);
        } else if(log.isDebugEnabled()){
			log.debug("无效缓存key,忽略");
		}
    }

}