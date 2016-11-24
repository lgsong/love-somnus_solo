package com.somnus.solo.service;

import com.somnus.solo.message.sample.MerAccountQueryRequest;
import com.somnus.solo.message.sample.MerAccountQueryResponse;
import com.somnus.solo.message.sample.MeracctRequest;

public interface MerAccountService {

	/**
	 * 根据交易账户号查询交易账户
	 * 
	 * @param acctCode
	 * @return
	 */
	MerAccountQueryResponse selectByAcctcode(MerAccountQueryRequest request);

	void createMeracct(MeracctRequest request) throws Exception;

}
