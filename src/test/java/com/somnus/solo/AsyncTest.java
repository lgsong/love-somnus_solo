package com.somnus.solo;

import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.somnus.solo.async.AsyncCallbackTask;
import com.somnus.solo.async.AsyncTask;
import com.somnus.solo.async.ListenableFutureTask;
import com.somnus.solo.support.holder.ApplicationContextHolder;

public class AsyncTest extends AbstractTestSupport {

	@Test
	public void asyncTask() throws Exception {
		AsyncTask task = ApplicationContextHolder.getBean(AsyncTask.class);
		task.doTaskOne();
		task.doTaskTwo();
		task.doTaskThree();
	}

	@Test
	public void asyncCallbackTask() throws Exception {
		AsyncCallbackTask task = ApplicationContextHolder.getBean(AsyncCallbackTask.class);
		long start = System.currentTimeMillis();

		Future<String> task1 = task.doTaskOne();
		Future<String> task2 = task.doTaskTwo();
		Future<String> task3 = task.doTaskThree();

		while (true) {
			if (task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}

		long end = System.currentTimeMillis();

		System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

	}

	@Test
	public void listenableFutureTask() throws Exception {
		ListenableFutureTask task = ApplicationContextHolder.getBean(ListenableFutureTask.class);

		ListenableFuture<String> listenableFuture = task.doTask();

		SuccessCallback<String> successCallback = new SuccessCallback<String>() {
			@Override
			public void onSuccess(String str) {
				System.out.println("异步回调成功了, return : " + str);
			}
		};
		FailureCallback failureCallback = new FailureCallback() {
			@Override
			public void onFailure(Throwable throwable) {
				System.out.println("异步回调失败了, exception message : "
						+ throwable.getMessage());
			}
		};

		listenableFuture.addCallback(successCallback, failureCallback);

		Assert.assertEquals("任务完成", listenableFuture.get());
	}

}
