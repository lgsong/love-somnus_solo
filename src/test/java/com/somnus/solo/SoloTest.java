package com.somnus.solo;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.somnus.solo.message.sample.MerAccountQueryRequest;
import com.somnus.solo.message.sample.MeracctRequest;

public class SoloTest {
	
	@Test
    public void selectByAcctcode() throws Exception {
		MerAccountQueryRequest request = new MerAccountQueryRequest();
    	request.setSysCode("1111");
    	request.setFrontName("abc");
    	request.setFrontTime("2016-11-11 11:11:11");
    	request.setAcctCode("1020550016");
    	
    	request.setSign("203D7438EA5FA1EA10D072F7F50F44B9");
    	
    	System.out.println("账户查询请求>>>:"+ JSON.toJSONString(request));
    	
    	String message = Request.Post("http://localhost:8080/solo/merAccount/selectByAcctcode")
    							.bodyString(JSON.toJSONString(request),ContentType.APPLICATION_JSON)
    							.execute().returnContent().asString();
    	
        System.out.println("账户查询响应<<<:"+message);
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
        
    	String message = Request.Post("http://localhost:8080/solo/merAccount/createMeracct")
				.bodyString(JSON.toJSONString(request),ContentType.APPLICATION_JSON)
				.execute().returnContent().asString();
    	
    	System.out.println("个人开户响应<<<:"+message);
    }
	
}
