package com.somnus.solo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.solo.support.holder.ApplicationContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisTemplateTest {
	
	@Test
	public void stringSet(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		
		template.opsForValue().set("username", "Somnus");//添加记录,如果记录已存在将覆盖原有的value
		
		System.out.println(template.opsForValue().get("username"));
	}
	
	@Test
	public void stringSetNx(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		
		System.out.println(template.opsForValue().setIfAbsent("username", "somnus"));//添加一条记录，仅当给定的key不存在时才插入
		
		System.out.println(template.opsForValue().get("username"));
	}
	
	@Test
	public void stringSetEx() throws InterruptedException{
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.opsForValue().set("password", "passw0rd", 10, TimeUnit.SECONDS);//添加有过期时间的记录
		
		Thread.sleep(15*1000);
		
		System.out.println(template.opsForValue().get("password"));
	}
	
	@Test
	public void stringSetrange(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.opsForValue().set("email", "928200207@qqq.com");
		template.opsForValue().set("email", "163", 10);//从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据
		
		System.out.println(template.opsForValue().get("email"));
		System.out.println(template.opsForValue().get("email",0, 8));//对指定key对应的value进行截取
	}
	
	@Test
	public void stringMset(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "1");
		map.put("key2", "2");
		map.put("key3", "3");
		map.put("key4", "4");
		template.opsForValue().multiSet(map);//批量存储记录
		
		System.out.println(template.opsForValue().multiGet(//批量获取记录,如果指定的key不存在返回List的对应位置将是null
				Arrays.asList(new String[]{"key1","key2","key3","key4"})));
	}
	
	@Test
	public void stringMsetnx(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "1");
		map.put("key2", "2");
		map.put("key3", "3");
		map.put("key5", "5");
		System.out.println(template.opsForValue().multiSetIfAbsent(map));//批量存储记录
		
		System.out.println(template.opsForValue().multiGet(//批量获取记录,如果指定的key不存在返回List的对应位置将是null
				Arrays.asList(new String[]{"key1","key2","key3","key5"})));
	}
	
	@Test
	public void stringGetset(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		System.out.println(template.opsForValue().getAndSet("email", "love@qq.com"));//获取并设置指定key对应的value
		
		System.out.println(template.opsForValue().get("email"));
	}
	
	@Test
	public void stringIncrBy(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.opsForValue().set("age", "25");
		
		System.out.println(template.opsForValue().increment("age", 2));//将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		
		System.out.println(template.opsForValue().increment("age", -4));//将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
	}
	
	@Test
	public void stringAppend(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		
		System.out.println(template.opsForValue().append("email", ".cn"));//在指定的key中追加value
	}
	
	@Test
	public void stringLen(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		
		System.out.println(template.opsForValue().size("email"));//获取key对应的值的长度
	}
	// ******************对存储结构为String类型的操作结束*********************************************//
	@Test
	public void hashSet(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.opsForHash().put("user", "username", "admin");//添加一个对应关系
		template.opsForHash().put("user", "password", "123456");//添加一个对应关系
		template.opsForHash().put("user", "age", "25");//添加一个对应关系
		
		System.out.println(template.opsForHash().get("user", "username"));//返回hash中指定存储位置的值
		System.out.println(template.opsForHash().get("user", "password"));
		System.out.println(template.opsForHash().get("user", "age"));
	}
	
	@Test
	public void hashSetnx(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		System.out.println(template.opsForHash().putIfAbsent("user", "username", "guest"));//添加对应关系，只有在fieid不存在时才执行
		System.out.println(template.opsForHash().putIfAbsent("user", "password", "passw0rd"));//添加对应关系，只有在fieid不存在时才执行
		
		System.out.println(template.opsForHash().get("user", "username"));
		System.out.println(template.opsForHash().get("user", "password"));
	}
	
	@Test
	public void hashMset(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", "admin");
		map.put("password", "passw0rd");
		template.opsForHash().putAll("people", map);//添加对应关系，如果对应关系已存在，则覆盖
		
		System.out.println(template.opsForHash().multiGet(//根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
				"people", Arrays.asList(new Object[]{"username","password"})));
	}
	
	@Test
	public void hashHincrBy(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		System.out.println(template.opsForHash().increment("user", "age", 2));//指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
	}
	
	@Test
	public void hashExists(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		System.out.println(template.opsForHash().hasKey("user", "age"));//测试hash中指定的存储是否存在
		System.out.println(template.opsForHash().hasKey("people", "age"));//测试hash中指定的存储是否存在
	}
	
	@Test
	public void hashLen(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		System.out.println(template.opsForHash().size("user"));//获取hash中存储的个数，类似Map中size方法
		System.out.println(template.opsForHash().size("people"));//获取hash中存储的个数，类似Map中size方法
	}
	
	@Test
	public void hashDel(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.opsForHash().delete("user", "username", "age");//从hash中删除指定的存储
		System.out.println(template.opsForHash().hasKey("user", "username"));//测试hash中指定的存储是否存在
		System.out.println(template.opsForHash().hasKey("user", "password"));//测试hash中指定的存储是否存在
		System.out.println(template.opsForHash().hasKey("user", "age"));//测试hash中指定的存储是否存在
	}
	
	@Test
	public void hashKeys(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		System.out.println(template.opsForHash().keys("user"));//返回指定hash中的所有存储名字,类似Map中的keySet方法
		System.out.println(template.opsForHash().values("user"));//获取hash中value的集合
		System.out.println(template.opsForHash().entries("user"));//以Map的形式返回hash中的存储和值
	}
	// *****************对存储结构为HashMap类型的操作结束*********************************************//
	@Test
	public void listLpush(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().leftPush("arraylist", "a"));//向List尾部追加记录
		System.out.println(template.opsForList().leftPush("arraylist", "b"));//向List尾部追加记录
		System.out.println(template.opsForList().leftPush("arraylist", "c"));//向List尾部追加记录
		System.out.println(template.opsForList().leftPush("arraylist", "d"));//向List尾部追加记录
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listRpush(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));//向List头部追加记录
		System.out.println(template.opsForList().rightPush("arraylist", "b"));//向List头部追加记录
		System.out.println(template.opsForList().rightPush("arraylist", "c"));//向List头部追加记录
		System.out.println(template.opsForList().rightPush("arraylist", "d"));//向List头部追加记录
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listInsert(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		
		System.out.println(template.opsForList().leftPush("arraylist", "a", "1"));//在value的相对位置前面插入记录
		System.out.println(template.opsForList().rightPush("arraylist", "b", "2"));//在value的相对位置后面插入记录
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listSet(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		
		template.opsForList().set("arraylist", 1, "$");//覆盖操作,将覆盖List中指定位置的值
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listRem(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
		
		System.out.println(template.opsForList().remove("arraylist", 2, "b"));//删除List中c条记录，被删除的记录值为value
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listTrim(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		System.out.println(template.opsForList().rightPush("arraylist", "c"));
		System.out.println(template.opsForList().rightPush("arraylist", "d"));
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
		
		template.opsForList().trim("arraylist", 1, 2);//算是删除吧，只保留start与end之间的记录
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listPop(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		System.out.println(template.opsForList().rightPush("arraylist", "c"));
		System.out.println(template.opsForList().rightPush("arraylist", "d"));
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
		
		System.out.println(template.opsForList().leftPop("arraylist"));//将List中的第一条记录移出List
		System.out.println(template.opsForList().rightPop("arraylist"));//将List中最后第一条记录移出List
		
		System.out.println(template.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listIndex(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("arraylist");
		System.out.println(template.opsForList().rightPush("arraylist", "a"));
		System.out.println(template.opsForList().rightPush("arraylist", "b"));
		System.out.println(template.opsForList().rightPush("arraylist", "c"));
		System.out.println(template.opsForList().rightPush("arraylist", "d"));
		
		System.out.println(template.opsForList().index("arraylist8",2));//获取List中指定位置的值
		System.out.println(template.opsForList().size("arraylist"));//List长度
	}
	// ******************对存储结构为List类型的操作 结束***********************************************//
	@Test
	public void setAdd(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("hashset");
		System.out.println(template.opsForSet().add("hashset", "a","b","c","c"));
		
		System.out.println(template.opsForSet().remove("hashset", "a"));
		
		System.out.println(template.opsForSet().members("hashset"));
		
		System.out.println(template.opsForSet().pop("hashset"));//从集合中随机弹出（删除）成员
		
		System.out.println(template.opsForSet().members("hashset"));
		
	}
	
	@Test
	public void setDiff(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("hashset");
		template.delete("hashset2");
		template.delete("hashset3");
		System.out.println(template.opsForSet().add("hashset", "a","b"));
		System.out.println(template.opsForSet().add("hashset2", "b","c"));
		
		//返回从第一组和所有的给定集合之间的差异的成员
		System.out.println(template.opsForSet().difference("hashset", "hashset2"));
		//这个命令等于sdiff,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
		System.out.println(template.opsForSet().differenceAndStore("hashset", "hashset2", "hashset3"));
		System.out.println(template.opsForSet().members("hashset3"));
	}
	
	@Test
	public void setInter(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("hashset");
		template.delete("hashset2");
		template.delete("hashset3");
		System.out.println(template.opsForSet().add("hashset", "a","b"));
		System.out.println(template.opsForSet().add("hashset2", "b","c"));
		
		//返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
		System.out.println(template.opsForSet().intersect("hashset", "hashset2"));
		//这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
		System.out.println(template.opsForSet().intersectAndStore("hashset", "hashset2", "hashset3"));
		System.out.println(template.opsForSet().members("hashset3"));
	}
	
	@Test
	public void setUnion(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("hashset");
		template.delete("hashset2");
		template.delete("hashset3");
		System.out.println(template.opsForSet().add("hashset", "a","b"));
		System.out.println(template.opsForSet().add("hashset2", "b","c"));
		
		//合并多个集合并返回合并后的结果，合并后的结果集合并不保存
		System.out.println(template.opsForSet().union("hashset", "hashset2"));
		//合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
		System.out.println(template.opsForSet().unionAndStore("hashset", "hashset2", "hashset3"));
		System.out.println(template.opsForSet().members("hashset3"));
	}
	
	@Test
	public void setMove(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("hashset");
		template.delete("hashset2");
		System.out.println(template.opsForSet().add("hashset", "a","b"));
		System.out.println(template.opsForSet().add("hashset2", "c","d"));
		
		System.out.println(template.opsForSet().move("hashset", "a","hashset2"));//将成员从源集合移出放入目标集合 
		System.out.println(template.opsForSet().members("hashset2"));
		System.out.println(template.opsForSet().size("hashset2"));//获取给定key中元素个数
		
		System.out.println(template.opsForSet().isMember("hashset2","a"));//确定一个给定的值是否存在
	}
	// *****************对存储结构为Set(排序的)类型的操作结束*******************************************//
	@Test
	public void sortSetAdd(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("sortset");
		//向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
		System.out.println(template.opsForZSet().add("sortset", "a", 1));
		System.out.println(template.opsForZSet().add("sortset", "b", 2));
		System.out.println(template.opsForZSet().add("sortset", "c", 3));
		System.out.println(template.opsForZSet().add("sortset", "d", 3));
		
		System.out.println(template.opsForZSet().remove("sortset", "c"));//从集合中删除成员
		
		System.out.println(template.opsForZSet().range("sortset",0 , -1));//返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
	}
	
	@Test
	public void sortSetIncrby(){
		StringRedisTemplate template = (StringRedisTemplate) ApplicationContextHolder.getBean(StringRedisTemplate.class);
		template.delete("sortset");
		//向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
		System.out.println(template.opsForZSet().add("sortset", "a", 1));
		System.out.println(template.opsForZSet().add("sortset", "b", 2));
		System.out.println(template.opsForZSet().add("sortset", "c", 3));
		System.out.println(template.opsForZSet().add("sortset", "d", 4));
		System.out.println(template.opsForZSet().add("sortset", "c", 5));
		
		System.out.println(template.opsForZSet().incrementScore("sortset", "b", 2));//权重增加给定值，如果给定的member已存在

		System.out.println(template.opsForZSet().range("sortset",0 , -1));//返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
		
		System.out.println(template.opsForZSet().rank("sortset", "b"));//获取指定值在集合中的位置，集合排序从低到高
		System.out.println(template.opsForZSet().reverseRank("sortset", "b"));//获取指定值在集合中的位置，集合排序从高到低
		
		System.out.println(template.opsForZSet().size("sortset"));

	}
}
