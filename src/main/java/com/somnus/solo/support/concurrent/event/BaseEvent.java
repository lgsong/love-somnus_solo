package com.somnus.solo.support.concurrent.event;

import org.springframework.context.ApplicationEvent;

public abstract class BaseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	/** 事件名称 */
	protected String name;
	
	/** 默认使用异步模式 */
	protected boolean async = true;
	
	/** 延迟执行时间，默认0s(不延时)*/
	protected long delay = 0L;
	
	/**
	 * @param source
	 * @param name 事件名称
	 */
	public BaseEvent(Object source, String name) {
		super(source);
		this.name = name;
	}
	
	/**
	 * @param source
	 * @param name  事件名称
	 * @param async 异步模式
	 * @param delay 延迟执行时间
	 */
	public BaseEvent(Object source, String name, boolean async, long delay) {
		super(source);
		this.name = name;
		this.async = async;
		this.delay = delay;
	}

	public String getName() {
		return name;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public boolean isAsync() {
		return async;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
}
