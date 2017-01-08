package com.somnus.solo.activemq;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsServiceImpl implements JmsService{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    /**
	 * 发送一条字符串消息到指定的队列（目标）
	 * @param message 消息内容
	 */
	public void sendStringQueueMessage(final String message){
		log.info("---------------生产者发了一个字符串消息：" + message);
		jmsTemplate.send("queue.somnus.string.sample", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
	/**
	 * 发送一条字符串消息到指定的主题（目标）
	 * @param message 消息内容
	 */
	public void sendStringTopicMessage(final String message){
		log.info("---------------生产者发了一个字符串消息：" + message);
		jmsTemplate.send("topic.somnus.string.sample", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
	
	/**
	 * 发送一条序列化的 Java对象消息到指定的队列（目标）
	 * @param message 消息内容
	 */
	public void sendObjectQueueMessage(Serializable message){
		log.info("---------------生产者发了一个对象消息：" + message);
		jmsTemplate.convertAndSend("queue.somnus.object.sample", message);
	}
	
	/**
	 * 发送一条序列化的 Java对象消息到指定的主题（目标）
	 * @param message 消息内容
	 */
	public void sendObjectTopicMessage(Serializable message){
		log.info("---------------生产者发了一个对象消息：" + message);
		jmsTemplate.convertAndSend("topic.somnus.object.sample", message);
	}

}
