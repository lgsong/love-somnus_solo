package com.somnus.solo.support.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.somnus.solo.support.exceptions.HttpStatusException;

/**
 * @ClassName:     HttpClientUtils.java
 * @Description:   TODO
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年10月13日 下午3:40:17
 */
public class HttpClientUtils {
	
	private transient static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
	
	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws HttpHostConnectException
	 * 				连接不可用
	 * @throws IOException
	 * 				网络传输出错
	 */
	public static String doJsonPost(String url, Map<String,String> param) throws HttpHostConnectException, IOException{
		Validate.notNull(url, "url is required.");
		Validate.notEmpty(param);
		return doJsonPost(url,JSON.toJSONString(param));
	}
	
	/**
	 * @param url
	 * @param json
	 * @return
	 * @throws HttpHostConnectException
	 * 				连接不可用
	 * @throws IOException
	 * 				网络传输出错
	 */
	public static String doJsonPost(String url, String json) throws HttpHostConnectException, IOException{
		Validate.notNull(url, "url is required.");
		Validate.notNull(json, "json is required.");
		//创建HttpClient对象
        CloseableHttpClient httpclient = HttpConnectionManager.getHttpClient();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
        	// 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json,ContentType.APPLICATION_JSON));
            
            // 开始执行http请求
            long startTime = System.currentTimeMillis();  
            httpResponse = httpclient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            
            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();  
            log.info("statusCode:" + statusCode);  
            log.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            
            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity,Charset.forName("UTF-8"));
            
            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
            	throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode,resultString));
            } 
        } finally{
        	if(httpResponse != null){
        		httpResponse.close();
        	}
        	httpclient.close();
        }
		return resultString;
    }
	
	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws HttpHostConnectException
	 * 				连接不可用
	 * @throws IOException
	 * 				网络传输出错
	 * @throws URISyntaxException
	 * 				URI格式出错
	 */
	public static String doGet(String url, Map<String,String> param) throws HttpHostConnectException, IOException, URISyntaxException{
		Validate.notNull(url, "url is required.");
		//创建HttpClient对象
		CloseableHttpClient httpclient = HttpConnectionManager.getHttpClient();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
        	//创建uri
        	URIBuilder builder = new URIBuilder(url);
            if(MapUtils.isNotEmpty(param)){
            	for(String key :param.keySet()){
            		builder.addParameter(key, param.get(key));
            	}
            }
            URI uri = builder.build();
            // 创建httpGet请求
            HttpGet httpGet = new HttpGet(uri);
            
            // 开始执行http请求
            long startTime = System.currentTimeMillis();
            httpResponse = httpclient.execute(httpGet);
            long endTime = System.currentTimeMillis();
            
            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();  
            log.info("statusCode:" + statusCode);  
            log.info("调用API花费时间(单位：毫秒)：" + (endTime - startTime));
            
            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity,Charset.forName("UTF-8"));
            // 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            resultString.replaceAll("\r", "");
            
            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
            	throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode,resultString));
            }
        } finally{
        	if(httpResponse != null){
        		httpResponse.close();
        	}
        	httpclient.close();
        }
        return resultString;
    }
	
	/**
	 * @param url
	 * @return
	 * @throws HttpHostConnectException
	 * 				连接不可用
	 * @throws IOException
	 * 				网络传输出错
	 * @throws URISyntaxException
	 * 				URI格式出错
	 */
	public static String doGet(String url) throws HttpHostConnectException, IOException, URISyntaxException{
		return doGet(url,null);
	}
	
	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws HttpHostConnectException
	 * 				连接不可用
	 * @throws IOException
	 * 				网络传输出错
	 */
	public static String doPost(String url, Map<String,String> param) throws HttpHostConnectException, IOException{
		Validate.notNull(url, "url is required.");
		//创建HttpClient对象
        CloseableHttpClient httpclient = HttpConnectionManager.getHttpClient();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
        	// 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            
            if(MapUtils.isNotEmpty(param)){
            	List<NameValuePair> params = new ArrayList<NameValuePair>();
            	for(String key :param.keySet()){
            		params.add(new BasicNameValuePair(key, param.get(key)));
            	}
            	httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
            }
            
            // 开始执行http请求
            long startTime = System.currentTimeMillis();  
            httpResponse = httpclient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            
            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();  
            log.info("statusCode:" + statusCode);  
            log.info("调用API花费时间(单位：毫秒)：" + (endTime - startTime));
            
            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity,Charset.forName("UTF-8"));
            
            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
            	throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode,resultString));
            } 
        } finally{
        	if(httpResponse != null){
        		httpResponse.close();
        	}
        	httpclient.close();
        }
		return resultString;
    }

}
