package com.somnus.solo.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
 
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.somnus.solo.support.util.MapUtils;

public class HttpClientUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	/**
     * application/x-www-form-urlencoded post请求
     * @param url
     * @param map
     * @return
     */
    public static String doFormPost(String url, MultiValueMap<String, Object> map) {
    	return doPost(url, MapUtils.map2url(map), MediaType.APPLICATION_FORM_URLENCODED);
    }
	
	
    /**
     * application/json post请求
     * @param url
     * @param map
     * @return
     */
    public static String doJsonPost(String url, Map<String, Object> map) {
    	return doPost(url, JSON.toJSONString(map), MediaType.APPLICATION_JSON_UTF8);
    }
    
    public static String doPost(String url, String request, MediaType mediaType) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(mediaType);
    	
    	HttpEntity<String> formEntity = new HttpEntity<String>(request, headers);
    	
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);
		
		HttpStatus httpStatus = responseEntity.getStatusCode();
		
		String body = responseEntity.getBody();
		switch (httpStatus) {
		case OK:
			LOGGER.info("[status:200]" + url);
			return body;
		default:
			LOGGER.error("[status:" + httpStatus + "]" );
			/** 异常响应 **/
			throw new HttpClientErrorException(httpStatus,
					String.format("\n\tStatus:%s\n\tError Message:%s", httpStatus,body));
		}
    }
    
    public static void main(String[] args) {
    	MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
    	multiValueMap.add("name", "somnus");
    	multiValueMap.add("hobby", "sing");
    	multiValueMap.add("hobby", "swimg");
    	System.out.println(doFormPost("http://localhost:8080/SpringMVC/array/test", multiValueMap));
		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("username", "somnus");
		map.put("password", "sing");
		System.out.println(doJsonPost("http://localhost:8080/SpringMVC/databind/requestbodybind", map));
	}
    
}
