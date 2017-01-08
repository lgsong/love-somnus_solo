package com.somnus.solo.memcached.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AccountCache {
	
	@Cacheable(value = "cache.somnus.account", key = "#p0")
	public Account getAccount(String username){
		Account account = new Account();
		System.out.println("缓存里没有拿到值，需要从数据库中查，这里我们模仿自己设置出一个值来");
		account.setUsername(username);
		account.setPassword(username+"123456");
		return account;
	}
}
