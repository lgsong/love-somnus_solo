package com.somnus.solo.message.sample;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.somnus.solo.message.Message;

public class MeracctRequest extends Message {

	private static final long serialVersionUID = 1L;
	
	/** 交易账户号*/
	@NotEmpty
    private String acctCode;
    
    /** 商户号*/
	@NotEmpty
    private String merCode;
    
    /** 交易账户名*/
	@NotEmpty
    private String acctName;
    
    /** 币种_156：人民币*/
	@NotEmpty
    private String currency;
    
    /** 开户银行*/
	@NotEmpty
    private String bankCode;
    
    /** 开户银行名称*/
	@NotEmpty
    private String bankName;
    
    /** 银行联行号*/
	@NotEmpty
    private String bankKey;
    
    /** 分行编号_开户行编号*/
	@NotEmpty
    private String branchCode;
    
    /** 分行名称_开户行名称*/
	@NotEmpty
    private String branchName;
    
    /** 商户银行账号*/
	@NotEmpty
    private String bankAcctNo;
    
    /** 商户银行户名*/
	@NotEmpty
    private String bankAcctName;
    
    /** 版本状态_1:审核中 2:有效 3:无效*/
	@NotEmpty
	@Pattern(regexp = "^[123]$",message = "must be either 1, 2, 3" )
    private String status;
	
	/** 修改人 */
    private String modifyBy;
    
    /** 修改时间*/
    private Date modifyTime;
    
	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankKey() {
		return bankKey;
	}

	public void setBankKey(String bankKey) {
		this.bankKey = bankKey;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankAcctNo() {
		return bankAcctNo;
	}

	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	public String getBankAcctName() {
		return bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
