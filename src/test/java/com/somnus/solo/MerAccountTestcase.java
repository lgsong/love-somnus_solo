package com.somnus.solo;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.somnus.solo.message.sample.MerAccountQueryRequest;
import com.somnus.solo.message.sample.MeracctRequest;
import com.somnus.solo.support.http.HttpClientUtils;

public class MerAccountTestcase extends AbstractTestSupport {
	
	@Test
    public void selectByAcctcode() throws Exception {
		MerAccountQueryRequest request = new MerAccountQueryRequest();
    	request.setSysCode("11111111111");
    	request.setFrontName("test");
    	request.setFrontTime("test");
    	
    	request.setAcctCode("1020550016");
    	request.setPageNum(1);
    	request.setPageSize(10);
    	request.setSign("365318FE009A25FE2DA498962FA92D01");
    	
    	System.out.println("个人开户请求>>>:"+ JSON.toJSONString(request));
    	@SuppressWarnings("unchecked")
		Map<String,String> map = BeanUtils.describe(request);
		map.remove("class");
    	String message = HttpClientUtils.doPost("http://localhost:8080/solo/merAccount2/selectByAcctcode", map );
        System.out.println("个人开户响应<<<:"+message);
    }
	
	
	@Test
    public void createMeracct() throws Exception {
		MeracctRequest request = new MeracctRequest();
    	request.setSysCode("test");
    	request.setFrontName("test");
    	request.setFrontTime("test");
    	
    	request.setAcctCode("1020550026");
    	request.setMerCode("100091");
    	request.setAcctName("开发环境T1-0427");
    	request.setCurrency("156");
    	request.setBankCode("1103");
    	request.setBankName("华夏银行");
    	request.setBankKey("11");
    	request.setBranchCode("1111");
    	request.setBranchName("陆家嘴支行");
    	request.setBankAcctNo("1103");
    	request.setBankAcctName("11111");
    	request.setStatus("1");
    	request.setSign("26E258A9BD07294B3A5149316E1D2F18");
    	
    	System.out.println("个人开户请求>>>:"+ JSON.toJSONString(request));
    	@SuppressWarnings("unchecked")
		Map<String,String> map = BeanUtils.describe(request);
		map.remove("class");
    	String message = HttpClientUtils.doPost("http://localhost:8080/solo/merAccount2/createMeracct", map );
        System.out.println("个人开户响应<<<:"+message);
    }
	
}
