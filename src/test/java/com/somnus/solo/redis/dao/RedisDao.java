package com.somnus.solo.redis.dao;

public interface RedisDao {
	
	public <V> void save(final String key,final V value);
	
	public <V> void save(final String key,final V value,final int expire);
	
	public <V> V get(final String key, Class<V> clazz);
	
	public void delete(final String key);

}
