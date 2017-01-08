package com.somnus.solo.memcached.cache;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**  
 * @Description: TODO
 * @author Somnus
 * @date 2016年5月5日 下午11:29:14 
 * @version 1.0 
 */
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {  
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);   
    }  
}
