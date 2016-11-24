package com.somnus.solo.message.sample;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.somnus.solo.message.Message;

public class MerAccountQueryResponse extends Message {
	
	private static final long serialVersionUID = 1L;
	
	/** 当前页码*/
	@JsonInclude(Include.NON_DEFAULT)
	private int pageNum;
	
	/** 每页大小*/
	@JsonInclude(Include.NON_DEFAULT)
	private int pageSize;
	
	/** 总条数*/
	@JsonInclude(Include.NON_DEFAULT)
	private int rowCount;
	
	/** 查询结果*/
	@JsonInclude(Include.NON_EMPTY)
	private List<MerAccount> merAccounts;
	
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

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List<MerAccount> getMerAccounts() {
		return merAccounts;
	}

	public void setMerAccounts(List<MerAccount> merAccounts) {
		this.merAccounts = merAccounts;
	}
	
}
