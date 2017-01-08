package com.somnus.solo.rocketmq.support.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.somnus.solo.rocketmq.support.converter.MessageBodyConverter;
import com.somnus.solo.support.common.Constants;
import com.somnus.solo.support.util.SessionUtil;

public abstract class AbstractJmsReceiveListener<T> implements MessageListenerConcurrently {

    protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private MessageBodyConverter<?> messageBodyConverter;
    
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(
            List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
    	MessageExt msg = msgs.get(0);
        try {
        	MDC.put(Constants.SESSION_ID, SessionUtil.getSessionId());
        	
        	@SuppressWarnings("unchecked")
			T body = (T) this.messageBodyConverter.fromByteArray(msg.getBody());
        	
			execute(body);
		} catch (Throwable e) {
			logger.error("MQ消息处理异常：{}", msg.toString(), e);
		} finally {
            MDC.remove(Constants.SESSION_ID);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public MessageBodyConverter<?> getMessageBodyConverter() {
        return this.messageBodyConverter;
    }

    public void setMessageBodyConverter(MessageBodyConverter<?> messageBodyConverter) {
        this.messageBodyConverter = messageBodyConverter;
    }
    
    protected abstract void execute(T body) throws Exception;
}
