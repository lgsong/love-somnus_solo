package com.somnus.solo.support.concurrent.task;

/**
 * @description: 可重试任务
 * @author:  Somnus
 * @version: 1.0
 * @createDate: 2016年4月19日 下午3:16:25 
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年4月19日       Somnus                             1.0            
 */
public abstract class RetryableTask extends Task implements Retryable {

	/** 循环总次数*/
	private int total;
	
	/** 间隔时间*/
	private long interval;
	
	/** 当前执行次数*/
	private int current;
	
	/**
	 * @param name  任务名称
	 */
	public RetryableTask(String name) {
		super(name);
		current = 0;
	}
	
	/**
	 * @param name  任务名称
	 * @param delay 延迟执行时间
	 */
	public RetryableTask(String name, long delay) {
		super(name,delay);
		current = 0;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrent() {
		return current;
	}
	
	public void increaseCurrent() {
		this.current += 1;
	}

	public boolean canRetry(){
		return this.current < this.total;
	}
	
}
