package com.somnus.solo.support.concurrent.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import com.somnus.solo.support.concurrent.TaskExecutor;
import com.somnus.solo.support.concurrent.task.Retryable;
import com.somnus.solo.support.concurrent.task.RetryableTask;
import com.somnus.solo.support.concurrent.task.Task;

/**
 * @description: 支持异步处理的时间监听器
 */
public abstract class AsyncEventListener<T extends BaseEvent> implements ApplicationListener<T>{

	private final static Logger LOGGER = LoggerFactory.getLogger(AsyncEventListener.class);

	@Override
	public void onApplicationEvent(final T event) {
		LOGGER.info("收到[{}]发起的[{}]事件通知,即将响应", event.getSource().getClass().getSimpleName(), event.getName());
		final AsyncEventListener<T> listener = this;
		RetryableTask task = new RetryableTask(event.getName() + "事件响应",event.getDelay()) {
			
			@Override
			public void run() {
				listener.doIt(event, this);
			}
			
		};
		if(event instanceof Retryable && ((Retryable) event).getTotal() > 0){
			Retryable retryable = (Retryable) event;
			task.setTotal(retryable.getTotal());
			task.setInterval(retryable.getInterval());
			LOGGER.info("当前任务失败时可重试:[总重试次数={} , 重试间隔:{}ms]", task.getTotal(), task.getInterval());
		}
		if(event.isAsync()){
			TaskExecutor.run(task);
		}else {
			doIt(event, task);
		}
	}

	private void doIt(T event, Task task){
		try {
			handle(event);
			LOGGER.info("[{}]事件响应处理完成", event.getName());
			retry(event, task, null);
		} catch (Exception e) {
			if(event.isAsync()){
				LOGGER.warn("[" + event.getName() + "]事件响应失败", e);
				retry(event, task, e);
			}else {
				LOGGER.warn("[" + event.getName() + "]事件响应失败:{}", e.getMessage());
				throw new RuntimeException(e);
			}
		}
	}
	
	private void retry(T event, Task task, Exception e){
		boolean isRetry = task instanceof RetryableTask;
		if(!isRetry){
			return;
		}
		RetryableTask retryAbleTask = (RetryableTask) task;
		int retryCount = retryAbleTask.getCurrent();
		if(e == null){
			if(retryCount > 0){
				LOGGER.info("[" + event.getName() + "]事件在重试时处理成功");
			}
		}else {
			if(retryAbleTask.canRetry()){
				task.setDelay(retryAbleTask.getInterval());
				retryAbleTask.increaseCurrent();
				LOGGER.info("失败的任务[{}]即将进行第[{}]次重试", retryAbleTask.getName(), retryAbleTask.getCurrent());
				if(event.isAsync()){
					TaskExecutor.run(task);
				}else {
					doIt(event, task);
				}
			} else {
				LOGGER.warn("失败的任务[{}]已进行了[{}]次重试,不允许继续重试", retryAbleTask.getName(), retryAbleTask.getCurrent());
				String message = retryAbleTask.getName() + "重试" + retryAbleTask.getCurrent() + "次后仍然失败(" + e.getMessage() + ")";
				LOGGER.warn(message);
			}
		}
	}
	
	protected abstract void handle(T event);
	
}
