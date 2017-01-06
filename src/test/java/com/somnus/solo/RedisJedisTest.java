package com.somnus.solo;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.solo.redis.JedisUtil;

import redis.clients.jedis.BinaryClient.LIST_POSITION;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisJedisTest {
	
	@Test
	public void stringSet(){
		System.out.println(JedisUtil.Strings.set("username", "admin"));
		
		System.out.println(JedisUtil.Strings.get("username"));
	}
	
	@Test
	public void stringSetnx(){
		System.out.println(JedisUtil.Strings.setnx("username", "somnus"));
		
		System.out.println(JedisUtil.Strings.get("username"));
	}
	
	@Test
	public void stringSetex() throws InterruptedException{
		System.out.println(JedisUtil.Strings.setex("password", 10, "passw0rd"));
		
		Thread.sleep(15*1000);
		
		System.out.println(JedisUtil.Strings.get("password"));
	}
	
	@Test
	public void stringSetrange(){
		System.out.println(JedisUtil.Strings.set("email", "928200207@qqq.com"));
		System.out.println(JedisUtil.Strings.setrange("email", 10, "163"));
		
		System.out.println(JedisUtil.Strings.get("email"));
		System.out.println(JedisUtil.Strings.getrange("email", 0, 8));
	}
	
	@Test
	public void stringMset(){
		System.out.println(JedisUtil.Strings.mset("key1","1","key2","2","key3","3","key4","4"));
		
		System.out.println(JedisUtil.Strings.mget("key1","key2","key3","key4"));
	}
	
	@Test
	public void stringMsetnx(){
		System.out.println(JedisUtil.Strings.msetnx("key1","1","key2","2","key3","3","key5","5"));
		
		System.out.println(JedisUtil.Strings.mget("key1","key2","key3","key4"));
	}
	
	@Test
	public void stringGetset(){
		System.out.println(JedisUtil.Strings.getSet("email", "love@qq.com"));
		
		System.out.println(JedisUtil.Strings.get("email"));
	}
	
	@Test
	public void stringIncrBy(){
		System.out.println(JedisUtil.Strings.set("age", "25"));
		
		System.out.println(JedisUtil.Strings.incrBy("age", 2));
		
		System.out.println(JedisUtil.Strings.decrBy("age", 4));
	}
	
	@Test
	public void stringAppend(){
		System.out.println(JedisUtil.Strings.append("email", ".cn"));
	}
	
	@Test
	public void stringLen(){
		System.out.println(JedisUtil.Strings.strlen("email"));
	}
	// ******************对存储结构为String类型的操作结束*********************************************//
	@Test
	public void hashSet(){
		System.out.println(JedisUtil.Hash.hset("user", "username", "admin"));
		System.out.println(JedisUtil.Hash.hset("user", "password", "123456"));
		System.out.println(JedisUtil.Hash.hset("user", "age", "25"));
		
		System.out.println(JedisUtil.Hash.hget("user", "username"));
		System.out.println(JedisUtil.Hash.hget("user", "password"));
		System.out.println(JedisUtil.Hash.hget("user", "age"));
	}
	
	@Test
	public void hashSetnx(){
		System.out.println(JedisUtil.Hash.hsetnx("user", "username", "guest"));
		System.out.println(JedisUtil.Hash.hset("user", "password", "passw0rd"));
		
		System.out.println(JedisUtil.Hash.hget("user", "username"));
		System.out.println(JedisUtil.Hash.hget("user", "password"));
	}
	
	@Test
	public void hashMset(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", "admin");
		map.put("password", "passw0rd");
		System.out.println(JedisUtil.Hash.hmset("people", map));
		
		System.out.println(JedisUtil.Hash.hmget("people", "username", "password"));
	}
	
	@Test
	public void hashIncrBy(){
		System.out.println(JedisUtil.Hash.hincrBy("user", "age", 2));
	}
	
	@Test
	public void hashExists(){
		System.out.println(JedisUtil.Hash.hexists("user", "age"));
		System.out.println(JedisUtil.Hash.hexists("people", "age"));
	}
	
	@Test
	public void hashLen(){
		System.out.println(JedisUtil.Hash.hlen("user"));
		System.out.println(JedisUtil.Hash.hlen("people"));
	}
	
	@Test
	public void hashDel(){
		System.out.println(JedisUtil.Hash.hdel("user", "age"));
		System.out.println(JedisUtil.Hash.hdel("people"));
	}
	
	@Test
	public void hashKeys(){
		System.out.println(JedisUtil.Hash.hkeys("user"));
		System.out.println(JedisUtil.Hash.hvals("user"));
		System.out.println(JedisUtil.Hash.hgetAll("user"));
	}
	// *****************对存储结构为HashMap类型的操作结束*********************************************//
	@Test
	public void listLpush(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.lpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.lpush("arraylist", "b"));
		System.out.println(JedisUtil.Lists.lpush("arraylist", "c"));
		System.out.println(JedisUtil.Lists.lpush("arraylist", "d"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
	}
	
	@Test
	public void listRpush(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "c"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "d"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist2", 0, -1));
	}
	
	@Test
	public void listInsert(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		
		System.out.println(JedisUtil.Lists.linsert("arraylist", LIST_POSITION.BEFORE, "a", "1"));
		System.out.println(JedisUtil.Lists.linsert("arraylist", LIST_POSITION.AFTER, "b", "2"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
	}
	
	@Test
	public void listSet(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		
		System.out.println(JedisUtil.Lists.lset("arraylist", 1, "$"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
	}
	
	@Test
	public void listRem(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
		
		System.out.println(JedisUtil.Lists.lrem("arraylist", 2, "b"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
	}
	
	@Test
	public void listTrim(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "c"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "d"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
		
		System.out.println(JedisUtil.Lists.ltrim("arraylist", 1, 2));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
	}
	
	@Test
	public void listPop(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "c"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "d"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
		
		System.out.println(JedisUtil.Lists.lpop("arraylist"));
		System.out.println(JedisUtil.Lists.rpop("arraylist"));
		
		System.out.println(JedisUtil.Lists.lrange("arraylist", 0, -1));
	}
	
	@Test
	public void listIndex(){
		JedisUtil.Keys.del("arraylist");
		System.out.println(JedisUtil.Lists.rpush("arraylist", "a"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "b"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "c"));
		System.out.println(JedisUtil.Lists.rpush("arraylist", "d"));
		
		
		System.out.println(JedisUtil.Lists.lindex("arraylist8",2));
		System.out.println(JedisUtil.Lists.llen("arraylist8"));
	}
	// ******************对存储结构为List类型的操作 结束***********************************************//
	@Test
	public void setAdd(){
		JedisUtil.Keys.del("hashset");
		System.out.println(JedisUtil.Sets.sadd("hashset", "a"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "b"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "c"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "c"));
		
		System.out.println(JedisUtil.Sets.srem("hashset", "a"));
		
		System.out.println(JedisUtil.Sets.smembers("hashset"));
		
		System.out.println(JedisUtil.Sets.spop("hashset"));
		
		System.out.println(JedisUtil.Sets.smembers("hashset"));
	}
	
	@Test
	public void setDiff(){
		JedisUtil.Keys.del("hashset");
		JedisUtil.Keys.del("hashset2");
		JedisUtil.Keys.del("hashset3");
		System.out.println(JedisUtil.Sets.sadd("hashset", "a"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "b"));
		
		System.out.println(JedisUtil.Sets.sadd("hashset2", "b"));
		System.out.println(JedisUtil.Sets.sadd("hashset2", "c"));
		
		System.out.println(JedisUtil.Sets.sdiff("hashset", "hashset2"));
		
		System.out.println(JedisUtil.Sets.sdiffstore("hashset3","hashset", "hashset2"));
		System.out.println(JedisUtil.Sets.smembers("hashset3"));
	}
	
	@Test
	public void setInter(){
		JedisUtil.Keys.del("hashset");
		JedisUtil.Keys.del("hashset2");
		JedisUtil.Keys.del("hashset3");
		System.out.println(JedisUtil.Sets.sadd("hashset", "a"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "b"));
		
		System.out.println(JedisUtil.Sets.sadd("hashset2", "b"));
		System.out.println(JedisUtil.Sets.sadd("hashset2", "c"));
		
		System.out.println(JedisUtil.Sets.sinter("hashset", "hashset2"));
		
		System.out.println(JedisUtil.Sets.sinterstore("hashset3","hashset", "hashset2"));
		System.out.println(JedisUtil.Sets.smembers("hashset3"));
	}
	
	@Test
	public void setUnion(){
		JedisUtil.Keys.del("hashset");
		JedisUtil.Keys.del("hashset2");
		JedisUtil.Keys.del("hashset3");
		System.out.println(JedisUtil.Sets.sadd("hashset", "a"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "b"));
		
		System.out.println(JedisUtil.Sets.sadd("hashset2", "b"));
		System.out.println(JedisUtil.Sets.sadd("hashset2", "c"));
		
		System.out.println(JedisUtil.Sets.sunion("hashset", "hashset2"));
		
		System.out.println(JedisUtil.Sets.sunionstore("hashset3","hashset", "hashset2"));
		System.out.println(JedisUtil.Sets.smembers("hashset3"));
	}
	
	@Test
	public void setMove(){
		JedisUtil.Keys.del("hashset");
		JedisUtil.Keys.del("hashset2");
		System.out.println(JedisUtil.Sets.sadd("hashset", "a"));
		System.out.println(JedisUtil.Sets.sadd("hashset", "b"));
		
		System.out.println(JedisUtil.Sets.sadd("hashset2", "c"));
		System.out.println(JedisUtil.Sets.sadd("hashset2", "d"));
		
		System.out.println(JedisUtil.Sets.smove("hashset", "hashset2", "a"));
		
		System.out.println(JedisUtil.Sets.smembers("hashset2"));
		
		System.out.println(JedisUtil.Sets.scard("hashset2"));
		
		System.out.println(JedisUtil.Sets.sismember("hashset2","a"));
	}
	// *****************对存储结构为Set(排序的)类型的操作结束*******************************************//
	@Test
	public void sortSetAdd(){
		JedisUtil.Keys.del("sortset");
		System.out.println(JedisUtil.SortSet.zadd("sortset", 1, "a"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 2, "b"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 3, "c"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 3, "d"));
		
		System.out.println(JedisUtil.SortSet.zrem("sortset", "c"));
		
		System.out.println(JedisUtil.SortSet.zrange("sortset",0 , -1));
	}
	
	@Test
	public void sortSetIncrby(){
		JedisUtil.Keys.del("sortset");
		System.out.println(JedisUtil.SortSet.zadd("sortset", 1, "a"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 2, "b"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 3, "c"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 4, "d"));
		System.out.println(JedisUtil.SortSet.zadd("sortset", 5, "c"));
		
		System.out.println(JedisUtil.SortSet.zincrby("sortset", 2, "b"));
		
		System.out.println(JedisUtil.SortSet.zrange("sortset",0 , -1));
		
		System.out.println(JedisUtil.SortSet.zrank("sortset","b"));
		System.out.println(JedisUtil.SortSet.zrevrank("sortset","b"));
		
		System.out.println(JedisUtil.SortSet.zcard("sortset"));
	}
}
