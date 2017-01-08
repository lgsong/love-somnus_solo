package com.somnus.solo.rocketmq.support.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.somnus.solo.rocketmq.support.converter.MessageBodyConverter;

public class MessageListenerContainer implements InitializingBean, DisposableBean {
	
	private String namesrvAddr;

    private MessageBodyConverter<?> messageBodyConverter;

    private Map<RocketMQTopic/* topic */, ? extends AbstractJmsReceiveListener<?>> subscribers =
            new HashMap<RocketMQTopic, AbstractJmsReceiveListener<?>>();

    private boolean shareConsumer = false;

    private transient Logger log = LoggerFactory.getLogger(this.getClass());

    private volatile DefaultMQPushConsumer sharedConsumer;

    private RocketMQTopic defaultTopic;

    private AbstractJmsReceiveListener<?> defaultMessageListener;

    protected final CopyOnWriteArraySet<DefaultMQPushConsumer> consumers = new CopyOnWriteArraySet<DefaultMQPushConsumer>();

    /**
     * Returns the default topic
     * 
     * @return
     */
    public RocketMQTopic getDefaultTopic() {
        return this.defaultTopic;
    }

    /**
     * Set the default topic when sharing consumers.
     * 
     * @param defaultTopic
     */
    public void setDefaultTopic(RocketMQTopic defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    /**
     * Returns the default listener
     * 
     * @return
     */
    public AbstractJmsReceiveListener<?> getDefaultMessageListener() {
        return this.defaultMessageListener;
    }

    /**
     * Set default message listener when sharing consumer.
     * 
     * @param defaultMessageListener
     */
    public void setDefaultMessageListener(AbstractJmsReceiveListener<?> defaultMessageListener) {
        this.defaultMessageListener = defaultMessageListener;
    }

    protected DefaultMQPushConsumer getDefaultMQPushConsumer(RocketMQTopic topic) throws MQClientException {
        DefaultMQPushConsumer consumer = this.getDefaultMQPushConsumer0(topic);
        return consumer;
    }

    private DefaultMQPushConsumer getDefaultMQPushConsumer0(RocketMQTopic topic) throws MQClientException {
        if (this.shareConsumer) {
            if (this.sharedConsumer == null) {
                if (this.defaultTopic == null) {
                    throw new IllegalArgumentException("Please provide default topic when sharing consumer.");
                }
                synchronized (this) {
                    if (this.sharedConsumer == null) {
                        this.sharedConsumer = new DefaultMQPushConsumer(topic.getGroup());
                        sharedConsumer.setNamesrvAddr(namesrvAddr);
                        sharedConsumer.setInstanceName(topic.getInstance());
                        if (!StringUtils.isBlank(this.defaultTopic.getTopic())) {
                            this.sharedConsumer.subscribe(this.defaultTopic.getTopic(),this.defaultTopic.getTags());
                        }
                        this.consumers.add(this.sharedConsumer);
                    }
                }
            }
            return this.sharedConsumer;
        }
        else {
            if (this.defaultMessageListener != null || this.defaultTopic != null) {
                throw new IllegalStateException(
                        "You can't provide default topic or message listener when not sharing consumer.");
            }
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(topic.getGroup());
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setInstanceName(topic.getInstance());
            this.consumers.add(consumer);
            return consumer;
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.sharedConsumer != null) {
            this.shutdownConsumer(this.sharedConsumer);
            this.sharedConsumer = null;
        }
        for (DefaultMQPushConsumer consumer : this.consumers) {
            this.shutdownConsumer(consumer);
        }
        this.consumers.clear();
    }

    private void shutdownConsumer(DefaultMQPushConsumer consumer) {
        consumer.shutdown();
    }

    /**
     * Returns if share consumer between topics.When share consumer, all topics
     * will be subscribed by the default topic's group.
     * 
     * @return
     */
    public boolean isShareConsumer() {
        return this.shareConsumer;
    }

    /**
     * Set to be true if you want to share consumer between topics.When share
     * consumer, all topics will be subscribed by the default topic's group.
     * 
     * @param shareConsumer
     */
    public void setShareConsumer(boolean shareConsumer) {
        this.shareConsumer = shareConsumer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Start to initialize message listener container.");
        if (this.subscribers != null) {
            Set<DefaultMQPushConsumer> consumers = new HashSet<DefaultMQPushConsumer>();
            for (Map.Entry<RocketMQTopic, ? extends AbstractJmsReceiveListener<?>> entry : this.subscribers.entrySet()) {
                final RocketMQTopic topic = entry.getKey();
                final AbstractJmsReceiveListener<?> listener = entry.getValue();
                if (topic == null) {
                    throw new IllegalArgumentException("Topic is null");
                }
                if (StringUtils.isBlank(topic.getTopic())) {
                    throw new IllegalArgumentException("Blank topic");
                }
                DefaultMQPushConsumer consumer = this.getDefaultMQPushConsumer(topic);
                if (consumer == null) {
                    throw new IllegalStateException("Get or create consumer failed");
                }
                log.info("Subscribe topic=" + topic.getTopic() + " with group=" + topic.getGroup());
                if (listener.getMessageBodyConverter() == null) {
                    listener.setMessageBodyConverter(this.messageBodyConverter);
                }
                consumer.subscribe(topic.getTopic(),topic.getTags());
                consumer.registerMessageListener(listener);
                consumers.add(consumer);
            }
            for (DefaultMQPushConsumer consumer : consumers) {
                consumer.start();
            }
        }
        log.info("Initialize message listener container successfully.");
    }

    /**
     * returns the message body converter.It's null by default.
     * 
     * @return
     */
    public MessageBodyConverter<?> getMessageBodyConverter() {
        return this.messageBodyConverter;
    }

    public Map<RocketMQTopic, ? extends AbstractJmsReceiveListener<?>> getSubscribers() {
        return this.subscribers;
    }

    /**
     * Configure subscribers.
     * 
     * @param listeners
     */
    public void setSubscribers(Map<RocketMQTopic, ? extends AbstractJmsReceiveListener<?>> subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * Set message body converter.If listener doesn't have a converter,it will
     * use this one.It's null by default.
     * 
     * @param messageBodyConverter
     */
    public void setMessageBodyConverter(MessageBodyConverter<?> messageBodyConverter) {
        this.messageBodyConverter = messageBodyConverter;
    }

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

}
