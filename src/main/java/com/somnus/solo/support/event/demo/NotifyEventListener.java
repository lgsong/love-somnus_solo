package com.somnus.solo.support.event.demo;

import org.springframework.stereotype.Component;

import com.somnus.solo.support.concurrent.event.AsyncEventListener;

@Component
public class NotifyEventListener extends AsyncEventListener<NotifyEvent>{

	@Override
	protected void handle(NotifyEvent event) {
        System.out.println("通知到的接收人为:"+event.getReceiver());  
        
        System.out.println("通知的内容是:"+event.getContent());
        
        sb();
	}
	
	/** 为了抛异常,从而可以看到失败轮询*/
	private void sb(){
		System.out.println(1/0);
	}
	
}
