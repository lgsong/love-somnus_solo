package com.somnus.solo.rocketmq.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.somnus.solo.rocketmq.support.listener.AbstractJmsReceiveListener;

public class SampleStringAction extends AbstractJmsReceiveListener<String>{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected void execute(String msg) throws Exception {
		log.info("接收消息：{}", msg);
		//TODO
		//消费者拿到想要的Obejct,至于怎么处理就是你自己的事情了
	}

}
