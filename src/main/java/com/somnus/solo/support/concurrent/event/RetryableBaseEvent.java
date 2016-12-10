package com.somnus.solo.support.concurrent.event;

import com.somnus.solo.support.concurrent.task.Retryable;

public abstract class RetryableBaseEvent extends BaseEvent implements Retryable {
	
	private static final long serialVersionUID = 1L;
	
	/** 循环总次数*/
	protected int total;
	
	/** 间隔时间*/
	protected long interval;
	
	/**
	 * @param source
	 * @param name 事件名称
	 */
	public RetryableBaseEvent(Object source, String name) {
		super(source, name);
		this.total = 5;
		this.interval = 30000;
	}
	
	/**
	 * @param source
	 * @param name     事件名称
	 * @param async    异步模式
	 * @param delay    延迟执行时间
	 * @param total    循环总次数
	 * @param interval 间隔时间
	 */
	public RetryableBaseEvent(Object source, String name, boolean async, long delay, 
			int total, long interval) {
		super(source, name,async, delay);
		this.total = total;
		this.interval = interval;
	}
	
	@Override
	public int getTotal() {
		return total;
	}

	@Override
	public long getInterval() {
		return interval;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
	
}
