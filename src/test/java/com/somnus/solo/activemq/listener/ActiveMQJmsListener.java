package com.somnus.solo.activemq.listener;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.somnus.solo.memcached.cache.Account;

@Component
public class ActiveMQJmsListener{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
	
	@JmsListener(destination = "queue.somnus.string.sample")
	protected void execute(Message message) throws Exception {
		log.info("接收字符串消息：{}", ((TextMessage)message).getText());
		//TODO
		//消费者拿到想要的字符串,至于怎么处理就是你自己的事情了
	}
	
	@JmsListener(destination = "queue.somnus.object.sample")
	protected void execute(Account account) throws Exception {
		log.info("接收序列化的 Java对象消息：{}", account);
		//TODO
		//消费者拿到想要的Obejct,至于怎么处理就是你自己的事情了
	}
}
