package com.somnus.solo.rocketmq.support.converter;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQClientException;

public class StringMessageBodyConverter implements MessageBodyConverter<String> {

	private transient Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public byte[] toByteArray(String body) throws MQClientException {
		if(body == null)
			return null;
		try {
			return body.getBytes("utf-8");
		} catch (Exception e) {
			log.error("rocketmq消息格式格式化错误", e);
		}
		return null;
	}

	@Override
	public String fromByteArray(byte[] bs) throws MQClientException {
		try {
			return new String(bs,"utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("rocketmq消息格式转换错误", e);
		}
		return null;
	}
}
