package com.somnus.solo;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.solo.redis.dao.RedisDao;
import com.somnus.solo.redis.dao.impl.JsonRedisDaoImpl;
import com.somnus.solo.redis.dao.impl.RedisDaoImpl;
import com.somnus.solo.redis.model.User;
import com.somnus.solo.support.holder.ApplicationContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisSpringTest {
	
	@Test
	public void testObject(){
		RedisDao redis = (RedisDao) ApplicationContextHolder.getBean(RedisDaoImpl.class);
		User user = new User();
        user.setUsername("Somnus");
        user.setPassword("passw0rd");
		redis.save("user:1", user);
		
		User quser = redis.get("user:1",User.class);
		System.out.println(quser);
	}
	
	@Test
	public void testMap(){
		RedisDao redis = (RedisDao) ApplicationContextHolder.getBean(RedisDaoImpl.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", "Somnus");
		map.put("password", "123456");
		redis.save("map:1", map);
		
		@SuppressWarnings("unchecked")
		HashMap<String, String> qmap = redis.get("map:1",HashMap.class);
		System.out.println(qmap);
	}
	
	@Test
	public void testObject2(){
		RedisDao redis = (RedisDao) ApplicationContextHolder.getBean(JsonRedisDaoImpl.class);
		User user = new User();
        user.setUsername("Somnus");
        user.setPassword("passw0rd");
		redis.save("user:2", user);
		
		User quser = redis.get("user:2",User.class);
		System.out.println(quser);
	}
}
