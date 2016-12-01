package com.somnus.solo;

import java.util.concurrent.Future;

import org.junit.Test;

import com.somnus.solo.annotation.event.EmailEvent;
import com.somnus.solo.async.AsyncCallbackTask;
import com.somnus.solo.async.AsyncTask;
import com.somnus.solo.support.event.demo.NotifyEvent;
import com.somnus.solo.support.holder.ApplicationContextHolder;

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
	public void asyncTask() throws Exception{
		AsyncTask task = ApplicationContextHolder.getBean(AsyncTask.class);
		task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();
	}
	
	@Test
	public void asyncCallbackTask() throws Exception{
		AsyncCallbackTask task = ApplicationContextHolder.getBean(AsyncCallbackTask.class);
		long start = System.currentTimeMillis();

	    Future<String> task1 = task.doTaskOne();
	    Future<String> task2 = task.doTaskTwo();
	    Future<String> task3 = task.doTaskThree();

	    while(true) {
	        if(task1.isDone() && task2.isDone() && task3.isDone()) {
	            // 三个任务都调用完成，退出循环等待
	            break;
	        }
	        Thread.sleep(1000);
	    }

	    long end = System.currentTimeMillis();

	    System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
		
	}
	
}
