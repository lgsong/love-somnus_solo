package com.somnus.solo.rocketmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.somnus.solo.rocketmq.support.builder.MessageBuilder;
import com.somnus.solo.rocketmq.support.template.RocketMQTemplate;

@Service
public class RocketMqServiceImpl implements RocketMqService{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    @Value("${rocketmq.topic}")
    private String rocketmqTopic;
    
	@Override
	public void sendMessage(String message) {
		log.info("---------------生产者发了一个消息：" + message);
		SendResult sendResult = null;
		try {
			sendResult = rocketMQTemplate.send(MessageBuilder.withTopic(rocketmqTopic).withBody(message));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		}
        // check result
		System.out.println(sendResult);
	}
	
}
