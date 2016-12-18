package com.somnus.solo;

import org.junit.Test;

import com.somnus.solo.annotation.event.EmailEvent;
import com.somnus.solo.support.event.demo.NotifyEvent;
import com.somnus.solo.support.holder.ApplicationContextHolder;

public class EventTest extends AbstractTestSupport {
	
	@Test
	public void handleEmail(){
		ApplicationContextHolder.getApplicationContext().publishEvent(
				new EmailEvent(this, "10000@qq.com", "I love you"));
		
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
	public void handleEmailUndo(){
		ApplicationContextHolder.getApplicationContext().publishEvent(
				new EmailEvent(this, "10086@qq.com", "I love you"));
		
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
	
}
