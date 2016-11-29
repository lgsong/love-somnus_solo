package com.somnus.solo.message;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Message implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 发送系统编号*/
	@NotEmpty
	@JsonInclude(Include.NON_EMPTY)
    private String sysCode;
	
    /** 前置机编号*/
    @NotEmpty
    @JsonInclude(Include.NON_EMPTY)
    private String frontName;
    
    /** 前置机时间*/
    @NotEmpty
    @Pattern(regexp = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|"
    		+ "(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|"
    		+ "[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s+([01][0-9]|"
    		+ "2[0-3]):[0-5][0-9]:[0-5][0-9]$",message = "must be yyyy-MM-dd HH:mm:ss" )
    @JsonInclude(Include.NON_EMPTY)
    private String frontTime;
    
    /** 响应码*/
    private String repCode;
    
    /** 响应消息*/
    private String repMsg;
    
    /** 签名*/
    @NotEmpty
    @JsonInclude(Include.NON_EMPTY)
    private String sign;

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getFrontName() {
		return frontName;
	}

	public void setFrontName(String frontName) {
		this.frontName = frontName;
	}

	public String getFrontTime() {
		return frontTime;
	}

	public void setFrontTime(String frontTime) {
		this.frontTime = frontTime;
	}

	public String getRepCode() {
		return repCode;
	}

	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}

	public String getRepMsg() {
		return repMsg;
	}

	public void setRepMsg(String repMsg) {
		this.repMsg = repMsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
    
}
