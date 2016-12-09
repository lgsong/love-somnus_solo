package com.somnus.solo;

import org.junit.Test;

import com.somnus.solo.support.event.demo.NotifyEvent;
import com.somnus.solo.support.holder.ApplicationContextHolder;
import com.somnus.solo.support.scheduler.AsyncExecutor;
import com.somnus.solo.support.scheduler.EmailEvent;

public class SpringTest extends AbstractTestSupport {
	
	@Test
	public void handleEmail(){
		ApplicationContextHolder.getApplicationContext().publishEvent(
				new EmailEvent(this, "928200207@qq.com", "I love you"));
		
		System.out.println("上面的，你走你的异步去，我已经执行了，come on！");
		try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void reEvent(){
		ApplicationContextHolder.getApplicationContext().publishEvent(
				new NotifyEvent(this, "Somnus", "I love you"));
		
		try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void exe(){
		AsyncExecutor executor = ApplicationContextHolder.getBean(AsyncExecutor.class);
		executor.execute();
		System.out.println("我已经执行了！");
		
		try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
