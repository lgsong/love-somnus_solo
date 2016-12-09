package com.somnus.solo.support.scheduler;

import org.springframework.context.ApplicationEvent;

public class NotifyEvent  extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;

	private String receiver;
	
	private String content;

	public NotifyEvent(Object source, String receiver, String content) {
		super(source);
		this.receiver = receiver;
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
