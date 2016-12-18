package com.somnus.solo.async.handler;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Override
	public void handleUncaughtException(Throwable throwable, Method method,
			Object... args) {
		System.out.println("调用异步任务出错了, message : " + throwable.getMessage()); 
	}

}
