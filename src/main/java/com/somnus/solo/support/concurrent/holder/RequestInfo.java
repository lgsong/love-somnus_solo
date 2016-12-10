package com.somnus.solo.support.concurrent.holder;

import java.io.Serializable;

/**
 * 请求信息
 */
public class RequestInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 事件名称
	 */
	private String name;

	public RequestInfo(String id){
		this.id = id;
	}
	
	public RequestInfo(String id, String name){
		this(id);
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}


	public RequestInfo clone(){
		RequestInfo requestInfo = new RequestInfo(this.id, this.name);
		return requestInfo;
	}

}