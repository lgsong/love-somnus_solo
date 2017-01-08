package com.somnus.solo.rocketmq.support.template;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.somnus.solo.rocketmq.support.builder.MessageBuilder;
import com.somnus.solo.rocketmq.support.converter.MessageBodyConverter;
import com.somnus.solo.rocketmq.support.utils.ThreadUtils;

public class RocketMQTemplate implements DisposableBean {
	
	private String producerGroup;
	
	private String namesrvAddr;
	
	private String instanceName;
	
	private MessageBodyConverter<?> messageBodyConverter;

    private boolean shareProducer = false;

    private volatile DefaultMQProducer sharedProducer;
    
    public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
     * Returns the message body converter.The default is an instance of
     * 
     * @return
     */
    public MessageBodyConverter<?> getMessageBodyConverter() {
        return this.messageBodyConverter;
    }

    /**
     * Set message body converter.
     * 
     * @param messageBodyConverter
     */
    public void setMessageBodyConverter(MessageBodyConverter<?> messageBodyConverter) {
        if (messageBodyConverter == null) {
            throw new IllegalArgumentException("Null messageBodyConverter");
        }
        this.messageBodyConverter = messageBodyConverter;
    }

    /**
     * returns if share a message producer between topics.It's false by default.
     * 
     * @return
     */
    public boolean isShareProducer() {
        return this.shareProducer;
    }

    /**
     * If true, the template will share a message producer between topics.It's
     * false by default.
     * 
     * @param producerPerTopic
     */
    public void setShareProducer(boolean producerPerTopic) {
        this.shareProducer = producerPerTopic;
    }

    private final ConcurrentHashMap<String, FutureTask<DefaultMQProducer>> producers =
            new ConcurrentHashMap<String, FutureTask<DefaultMQProducer>>();

    /**
     * Returns or create a message producer for topic.
     * @param topic
     * @return
     */
    public DefaultMQProducer getOrCreateProducer(final String topic) {
        if (!this.shareProducer) {
            FutureTask<DefaultMQProducer> task = this.producers.get(topic);
            if (task == null) {
                task = new FutureTask<DefaultMQProducer>(new Callable<DefaultMQProducer>() {

                    @Override
                    public DefaultMQProducer call() throws Exception {
                    	DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
                		producer.setNamesrvAddr(namesrvAddr);
                		producer.setInstanceName(instanceName);
                		producer.start();
                        return producer;
                    }

                });
                FutureTask<DefaultMQProducer> oldTask = this.producers.putIfAbsent(topic, task);
                if (oldTask != null) {
                    task = oldTask;
                }
                else {
                    task.run();
                }
            }

            try {
            	DefaultMQProducer producer = task.get();
                return producer;
            }
            catch (ExecutionException e) {
                throw ThreadUtils.launderThrowable(e.getCause());
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        else {
            if (this.sharedProducer == null) {
                synchronized (this) {
                    if (this.sharedProducer == null) {
                    	sharedProducer = new DefaultMQProducer(producerGroup);
                    	sharedProducer.setNamesrvAddr(namesrvAddr);
                    	sharedProducer.setInstanceName(instanceName);
                    }
                }
            }
            try {
				this.sharedProducer.start();
			} catch (MQClientException e) {
				e.printStackTrace();
			}
            return this.sharedProducer;
        }
        throw new IllegalStateException("Could not create producer for topic '" + topic + "'");
    }

    /**
     * Send message built by message builder.Returns the sent result.
     * 
     * @param builder
     * @return
     * @throws InterruptedException
     * @throws MQBrokerException 
     * @throws RemotingException 
     * @throws MQClientException 
     */
    public SendResult send(MessageBuilder builder) throws InterruptedException, MQClientException, RemotingException, MQBrokerException {
        Message msg = builder.build(this.messageBodyConverter);
        final String topic = msg.getTopic();
        DefaultMQProducer producer = this.getOrCreateProducer(topic);
        return producer.send(msg);
    }


    /**
     * Send message built by message builder.Returns the sent result.
     * 
     * @param builder
     * @param timeout
     * @throws InterruptedException 
     * @throws MQBrokerException 
     * @throws RemotingException 
     * @throws MQClientException 
     */
    public SendResult send(MessageBuilder builder, long timeout) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Message msg = builder.build(this.messageBodyConverter);
        final String topic = msg.getTopic();
        DefaultMQProducer producer = this.getOrCreateProducer(topic);
        return producer.send(msg, timeout);
    }


    @Override
    public void destroy() throws Exception {
        if (this.sharedProducer != null) {
            this.sharedProducer.shutdown();
            this.sharedProducer = null;
        }
        for (FutureTask<DefaultMQProducer> task : this.producers.values()) {
            try {
            	DefaultMQProducer producer = task.get(5000, TimeUnit.MILLISECONDS);
                if (producer != null) {
                    producer.shutdown();
                }
            }
            catch (Exception e) {
                // ignore
            }
        }
        this.producers.clear();

    }

}
