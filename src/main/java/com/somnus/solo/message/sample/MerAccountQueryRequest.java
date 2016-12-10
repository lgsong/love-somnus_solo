package com.somnus.solo.message.sample;

import org.hibernate.validator.constraints.NotEmpty;

import com.somnus.solo.message.Message;

public class MerAccountQueryRequest extends Message {

	private static final long serialVersionUID = 1L;
	
    @NotEmpty
    private String acctCode;
    
    /**当前页码*/
	private int pageNum = 1;
    
	/**每页大小*/
	private int pageSize = 25;
    
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
