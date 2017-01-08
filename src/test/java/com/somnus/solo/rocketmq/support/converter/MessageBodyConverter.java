package com.somnus.solo.rocketmq.support.converter;

import com.alibaba.rocketmq.client.exception.MQClientException;

public interface MessageBodyConverter<T> {
    /**
     * Convert a message object to byte array.
     * 
     * @param body
     * @return
     * @throws MetaClientException
     */
    public byte[] toByteArray(T body) throws MQClientException;


    /**
     * Convert a byte array to message object.
     * 
     * @param bs
     * @return
     * @throws MetaClientException
     */
    public T fromByteArray(byte[] bs) throws MQClientException;
}

