package com.somnus.solo.support.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncExecutor {

	@Async
	public void execute(){
		try {
            Thread.sleep(60000);
            System.out.println("让程序暂停1分钟，相当于执行一个很耗时的任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
}
