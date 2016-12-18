package com.somnus.solo.async;

import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ListenableFutureTask {

	public static Random random = new Random();

	@Async
	public ListenableFuture<String> doTask() throws Exception {
		System.out.println("开始做任务");
		long start = System.currentTimeMillis();
		Thread.sleep(random.nextInt(10000));
		long end = System.currentTimeMillis();
		System.out.println("完成任务，耗时：" + (end - start) + "毫秒");
		return new AsyncResult<String>("任务完成");
	}

}
