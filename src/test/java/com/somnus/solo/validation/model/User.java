package com.somnus.solo.validation.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.somnus.solo.validation.constraints.Future;

public class User {
	
	@NotNull 
    @Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "{user.username.illegal}")    
    private String username;  
	
    @Size(min = 6, max=10)    
    private String password;
    
    @Future(message="tranDate must be today or later")
    private Date tranDate;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}
	
	public User(String username, String password, Date tranDate) {
		super();
		this.username = username;
		this.password = password;
		this.tranDate = tranDate;
	}

	public User() {
		super();
	}
	
	public String toString() {  
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);   
    } 
}
