package com.somnus.solo.activemq;

import java.io.Serializable;

/** 
 * @Title: ProducerService.java 
 * @Package com.somnus.activemq 
 * @Description: TODO
 * @author Somnus
 * @date 2015年7月27日 上午10:44:45 
 * @version V1.0 
 */
public interface JmsService {
	
	public void sendStringMessage(final String message);
	
	
	public void sendObjectMessage(Serializable message);
}
