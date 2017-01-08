package com.somnus.solo.rocketmq.support.builder;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.somnus.solo.rocketmq.support.converter.MessageBodyConverter;

public class MessageBuilder {
	
    private final String topic;
    
    private String tag;
    
    private String key;
    
    private Object body;
    
    private byte[] payload;

    private MessageBuilder(String topic) {
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("blank topic");
        }
        this.topic = topic;
    }

    public static final MessageBuilder withTopic(String topic) {
        return new MessageBuilder(topic);
    }


    public final MessageBuilder withTag(String tag) {
        this.tag = tag;
        return this;
    }
    
    public final MessageBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public final MessageBuilder withBody(Object obj) {
        if (this.payload != null) {
            throw new IllegalArgumentException("Payload is exists.");
        }
        this.body = obj;
        return this;
    }

    public final MessageBuilder withPayload(byte[] payload) {
        if (this.body != null) {
            throw new IllegalArgumentException("Message body is exists.");
        }
        this.payload = payload;
        return this;
    }

    public Message build() {
        return this.build(null);
    }

    @SuppressWarnings("unchecked")
	public <T> Message build(MessageBodyConverter<T> converter) {
        if (StringUtils.isBlank(this.topic)) {
            throw new IllegalArgumentException("Blank topic");
        }
        if (this.body == null && this.payload == null) {
            throw new IllegalArgumentException("Empty payload");
        }
        byte[] payload = this.payload;
        if (payload == null && converter != null) {
            try {
                payload = converter.toByteArray((T) this.body);
            }
            catch (MQClientException e) {
                throw new IllegalStateException("Convert message body failed.", e);
            }
        }

        if (payload == null) {
            throw new IllegalArgumentException("Empty payload");
        }
        return new Message(this.topic, this.tag, this.key, payload);
    }
    
}
