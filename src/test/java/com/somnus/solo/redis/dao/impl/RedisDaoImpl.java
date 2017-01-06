package com.somnus.solo.redis.dao.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.somnus.solo.redis.dao.RedisDao;

@Repository
public class RedisDaoImpl implements RedisDao {
	
	@Autowired
	protected RedisTemplate<String, Serializable> redisTemplate;
	
	private final RedisSerializer<Object> valueSerializer = new JdkSerializationRedisSerializer();
	
	@Override
	public <V> void save(final String key,final V value) {
		if(value instanceof Map){
			redisTemplate.opsForHash().putAll(key, (Map<?,?>) value );
		}else{
			redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.set(redisTemplate.getStringSerializer().serialize(key),
	                			   valueSerializer.serialize(value));
					return null;
				}
			});
		}
	}
	
	public <V> void save(final String key,final V value,final int expire){
		if(value instanceof Map){
			redisTemplate.opsForHash().putAll(key, (Map<?,?>) value );
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}else{
			redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.setEx(redisTemplate.getStringSerializer().serialize(key), 
	                				 expire, valueSerializer.serialize(value));
					return null;
				}
			});
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> V get(final String key, Class<V> clazz){
		if(Map.class.isAssignableFrom(clazz)){
			V v = (V) redisTemplate.opsForHash().entries(key);
			return v;
		}else{
			return redisTemplate.execute(new RedisCallback<V>() {
				@Override
				public V doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] rawKey = redisTemplate.getStringSerializer().serialize(key);
					if (connection.exists(rawKey)) {
						byte[] value = connection.get(rawKey);
						V v = (V) valueSerializer.deserialize(value);
                        return v;
					}
					return null;
				}
			});
		}
	}
    
	@Override  
	public void delete(final String key) {
		redisTemplate.delete(key);
	}
}