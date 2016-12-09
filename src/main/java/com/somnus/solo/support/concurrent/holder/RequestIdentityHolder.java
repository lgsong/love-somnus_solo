package com.somnus.solo.support.concurrent.holder;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 请求信息持有者
 */
public final class RequestIdentityHolder {

	private final static Logger LOGGER = LoggerFactory.getLogger(RequestIdentityHolder.class);
	private final static ThreadLocalMap HOLDER = new ThreadLocalMap();
	protected static String NAME = "";
	protected static String VERSION = "";
	public final static String RID = "rid";
	public final static String RIP = "rip";
	public final static String RNAME = "rname";
	public final static String RVERSION = "rversion";
	private final static String CONFIG = "event/requestinfo.properties";

	static{
		try {
			String url = RequestIdentityHolder.class.getClassLoader().getResource(CONFIG).getPath();
            PropertiesConfiguration config = new PropertiesConfiguration(url);
            if(LOGGER.isDebugEnabled()){
				LOGGER.debug("classpath:requestinfo.properties配置文件内容:{}", config.toString());
			}
            NAME = (String) config.getString("name");
			VERSION = (String) config.getString("version");
		} catch (Exception e) {
			if(LOGGER.isErrorEnabled()){
				LOGGER.error("加载classpath:requestinfo.properties文件失败", e);
			}
		}
	}
	
	/**
	 * 私有构造方法,不允许外部构造
	 */
	private RequestIdentityHolder(){
	}
	
	/**
	 * 设置当前请求信息
	 * @param requestInfo
	 */
	public static void set(RequestInfo requestInfo){
		if(requestInfo != null){
			MDC.put(RID, requestInfo.getId());
			MDC.put(RIP, StringUtils.trimToEmpty(requestInfo.getIp()));
			MDC.put(RNAME, StringUtils.trimToEmpty(requestInfo.getName()));
			MDC.put(RVERSION, StringUtils.trimToEmpty(requestInfo.getVersion()));
			HOLDER.set(requestInfo);
		}else{
			clear();
		}
	}
	
	/**
	 * 获取当前请求信息
	 * @return
	 */
	public static RequestInfo get(){
		return HOLDER.get();
	}
	
	/**
	 * 获取当前请求信息
	 * @param autoCreate 如果为null,是否自动创建并自动与当前线程绑定
	 * @return
	 */
	public static RequestInfo get(boolean autoCreate){
		RequestInfo ri = get();
		if(ri == null && autoCreate){
			ri = generateNew();
			set(ri);
		}
		return ri;
	}
	
	/**
	 * 生成一个新的RequestInfo,但不自动与当前线程绑定
	 * @return
	 */
	public static RequestInfo generateNew(){
		RequestInfo requestInfo = new RequestInfo(generateRid());
		requestInfo.setName(NAME);
		requestInfo.setVersion(VERSION);
		return requestInfo;
	}
	
	public static String generateRid(){
		return RandomStringUtils.randomNumeric(10);
	}
	
	/**
	 * 加入一个已存在请求调用序列
	 * @param requestInfo 已存在请求调用序列的请求信息
	 * @return 当前线程私有的请求信息,不同于参数中指定的RequestInfo
	 */
	public static RequestInfo join(RequestInfo requestInfo){
		if(requestInfo != null){
			requestInfo = requestInfo.clone();
			set(requestInfo);
		}
		return requestInfo;
	}
	
	public static void clear()	{
		HOLDER.set(null);
		MDC.remove(RID);
		MDC.remove(RIP);
		MDC.remove(RNAME);
		MDC.remove(RVERSION);
	}
	
	public static String getName(){
		return NAME;
	}
	
	public static String getVersion(){
		return VERSION;
	}
	
}