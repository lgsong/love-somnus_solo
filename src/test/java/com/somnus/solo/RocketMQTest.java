package com.somnus.solo;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.somnus.solo.rocketmq.RocketMqService;
import com.somnus.solo.rocketmq.RocketMqServiceImpl;
import com.somnus.solo.support.holder.ApplicationContextHolder;

public class RocketMQTest {
	
	@Test
	public void producer1() throws MQClientException{
		/**
		 * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
		 * 注意：ProducerGroupName需要由应用来保证唯一<br>
		 * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
		 * 因为服务器会回查这个Group下的任意一个Producer
		 */
		DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		producer.setNamesrvAddr("172.16.235.77:9876;172.16.235.78:9876");
		producer.setInstanceName("Producer");

		/**
		 * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		 * 注意：切记不可以在每次发送消息时，都调用start方法
		 */
		producer.start();
		long start = System.currentTimeMillis();
		/**
		 * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
		 * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
		 * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
		 * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
		 */
		for (int i = 0; i < 10; i++) {
			try {
				{
					Message msg = new Message("TopicTest1",// topic
							"TagA",							// tag
							"OrderID001",					// key
							("Hello MetaQ").getBytes());	// body
					SendResult sendResult = producer.send(msg);
					System.out.println(sendResult);
				}
				/*{
					Message msg = new Message("TopicTest11",// topic
							"TagB",							// tag
							"OrderID001",					// key
							("Hello MetaQ").getBytes());	// body
					SendResult sendResult = producer.send(msg);
					System.out.println(sendResult);
				}
				{
					Message msg = new Message("TopicTest12",// topic
							"TagC",							// tag
							"OrderID002",					// key
							("Hello MetaQ").getBytes());	// body
					SendResult sendResult = producer.send(msg);
					System.out.println(sendResult);
				}*/
				Thread.sleep(1000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("总耗时："+(end-start)/1000+"s");
		/**
		 * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
		 * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
		 */
		producer.shutdown();
	}
	
	/**
	 * Producer，发送顺序消息
	 */
	@Test
	public void producer2() throws MQClientException, RemotingException, MQBrokerException, InterruptedException{

    	DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		producer.setNamesrvAddr("172.16.235.77:9876;172.16.235.78:9876");
		producer.setInstanceName("Producer");

        producer.start();

        String[] tags = new String[] { "TagA", "TagB", "TagC"};

        for (int i = 0; i < 3; i++) {
            // 订单ID相同的消息要有序
            Message msg = new Message("OrderTopic", tags[i], "10001",
                        ("Hello RocketMQ 10001").getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, 10001);

            System.out.println(sendResult);
        }
        
        for (int i = 0; i < 3; i++) {
            // 订单ID相同的消息要有序
            Message msg = new Message("OrderTopic", tags[i], "10002",
                        ("Hello RocketMQ 10002").getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, 10002);

            System.out.println(sendResult);
        }
        
        for (int i = 0; i < 3; i++) {
            // 订单ID相同的消息要有序
            Message msg = new Message("OrderTopic", tags[i], "10003",
                        ("Hello RocketMQ 10003").getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, 10003);

            System.out.println(sendResult);
        }
        
        for (int i = 0; i < 3; i++) {
            // 订单ID相同的消息要有序
            Message msg = new Message("OrderTopic", tags[i], "10004",
                        ("Hello RocketMQ 10004").getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, 10004);

            System.out.println(sendResult);
        }

        producer.shutdown();
    
	}
	
	@Test
	public void consumer1() throws MQClientException{
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
        consumer.setNamesrvAddr("172.16.235.77:9876;172.16.235.78:9876");
        consumer.setInstanceName("Consumber");

        consumer.subscribe("OrderTopic", "TagA|| TagB || TagC");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            	
            	MessageExt msg = msgs.get(0);
            	
                System.out.println(new String(msg.getBody())+"==>"+msg);

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();

        System.out.println("Consumer Started.");
	}
	
	@Test
	public void consumer2(){
		for(int i=0;i<5;i++){
    		new Thread(new Runnable() {
                @Override
                public void run() {
                	String uuid = UUID.randomUUID().toString();
                	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
                    consumer.setNamesrvAddr("172.16.235.77:9876;172.16.235.78:9876");
                    consumer.setInstanceName("Consumber" + uuid);

                    try {
            			consumer.subscribe("TopicTest1", "TagA || TagB || TagC");
            		} catch (MQClientException e) {
            			e.printStackTrace();
            		}

                    consumer.registerMessageListener(new MessageListenerConcurrently() {

                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(
                                List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                            MessageExt msg = msgs.get(0);
                            if (msg.getTopic().equals("TopicTest1")) {
                                // 执行TopicTest1的消费逻辑
                                if (msg.getTags() != null 
                                		&& msg.getTags().equals("TagA")) {
                                    // 执行TagA的消费
                                    System.out.println(Thread.currentThread().getName() + msg);
                                } else if (msg.getTags() != null 
                                		&& msg.getTags().equals("TagB")) {
                                    // 执行TagB的消费
                                	System.out.println(Thread.currentThread().getName() + msg);
                                }
                            }
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    });

                    try {
            			consumer.start();
            		} catch (MQClientException e) {
            			e.printStackTrace();
            		}

                    System.out.println("Consumer " + uuid + " Started ...");
                }
            }).start();
    	}
	}
	
	@Test
	public void consumer3(){
		new Thread(new Runnable() {
            @Override
            public void run() {
            	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
                consumer.setNamesrvAddr("172.16.235.77:9876;172.16.235.78:9876");
                consumer.setInstanceName("Consumber22");

                try {
        			consumer.subscribe("TopicTest11", "TagA || TagB || TagC");
        		} catch (MQClientException e) {
        			e.printStackTrace();
        		}

                consumer.registerMessageListener(new MessageListenerConcurrently() {

                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(
                            List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                        MessageExt msg = msgs.get(0);
                        if (msg.getTopic().equals("TopicTest11")) {
                            // 执行TopicTest1的消费逻辑
                            if (msg.getTags() != null 
                            		&& msg.getTags().equals("TagA")) {
                                // 执行TagA的消费
                                System.out.println(Thread.currentThread().getName() + "==>TopicTest11=>" + msg.getStoreHost()+ "=>" + msg.getTags() + "=>" + new String(msg.getBody()));
                            } else if (msg.getTags() != null 
                            		&& msg.getTags().equals("TagB")) {
                                // 执行TagB的消费
                            	System.out.println(Thread.currentThread().getName() + "==>TopicTest11=>" + msg.getStoreHost()+ "=>" + msg.getTags() + "=>" + new String(msg.getBody()));
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                });

                try {
        			consumer.start();
        		} catch (MQClientException e) {
        			e.printStackTrace();
        		}

                System.out.println("Consumer Started 2.");
            }
        }).start();
	}
	
	@Test
	public void consumer4(){
		new Thread(new Runnable() {
            @Override
            public void run() {
            	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
                consumer.setNamesrvAddr("172.16.235.77:9876;172.16.235.78:9876");
                consumer.setInstanceName("Consumber33");

                try {
        			consumer.subscribe("TopicTest11", "TagA || TagB || TagC");
        		} catch (MQClientException e) {
        			e.printStackTrace();
        		}

                consumer.registerMessageListener(new MessageListenerConcurrently() {

                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(
                            List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                        MessageExt msg = msgs.get(0);
                        if (msg.getTopic().equals("TopicTest11")) {
                            // 执行TopicTest1的消费逻辑
                            if (msg.getTags() != null 
                            		&& msg.getTags().equals("TagA")) {
                                // 执行TagA的消费
                                System.out.println(Thread.currentThread().getName() + "==>TopicTest11=>" + msg.getStoreHost()+ "=>" + msg.getTags() + "=>" + new String(msg.getBody()));
                            } else if (msg.getTags() != null 
                            		&& msg.getTags().equals("TagB")) {
                                // 执行TagB的消费
                            	System.out.println(Thread.currentThread().getName() + "==>TopicTest11=>" + msg.getStoreHost()+ "=>" + msg.getTags() + "=>" + new String(msg.getBody()));
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                });

                try {
        			consumer.start();
        		} catch (MQClientException e) {
        			e.printStackTrace();
        		}

                System.out.println("Consumer Started 3.");
            }
        }).start();
	}
    
    @Test
    public void testSend() {
    	RocketMqService jms = (RocketMqService) ApplicationContextHolder.getBean(RocketMqServiceImpl.class);  
        jms.sendMessage("{\"username\":\"admin\"}");
        jms.sendMessage("{\"username\":\"admin\"}");
        jms.sendMessage("{\"username\":\"admin\"}");
        jms.sendMessage("{\"username\":\"admin\"}");
        try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
