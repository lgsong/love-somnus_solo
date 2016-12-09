package com.somnus.solo.support.concurrent;

/**
 * @description: 抽象任务
 * @author:  Somnus
 * @version: 1.0
 * @createDate: 2016年4月19日 下午3:16:25 
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年4月19日       Somnus                             1.0            
 */
public abstract class Task implements Runnable{
	
	/** 任务名称*/
	private String name;
	
	/** 延迟执行时间*/
	private long delay;
	
	/**
	 * @param name  任务名称
	 */
	public Task(String name){
		this.name = name;
	}
	
	/**
	 * @param name  任务名称
	 * @param delay 延迟执行时间
	 */
	public Task(String name, long delay){
		this(name);
		this.delay = delay >= 0 ? delay : 0;
	}
	
	public String getName() {
		return name;
	}
	
	public long getDelay() {
		return delay;
	}

	/**
	 * 设置延迟时间(毫秒)
	 * @param delay 延迟时间(毫秒)
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	@Override
	public abstract void run();

}
