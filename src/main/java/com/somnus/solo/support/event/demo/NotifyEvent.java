package com.somnus.solo.support.event.demo;

import com.somnus.solo.support.concurrent.event.RetryableBaseEvent;

public class NotifyEvent extends RetryableBaseEvent{
	
	private static final long serialVersionUID = 1L;

	private String receiver;
	
	private String content;

	public NotifyEvent(Object source, String receiver, String content) {
		super(source,"通知第三方");
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
