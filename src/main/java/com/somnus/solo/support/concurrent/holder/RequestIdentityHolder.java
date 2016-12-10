package com.somnus.solo.support.concurrent.holder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.somnus.solo.support.concurrent.task.Task;
import com.somnus.solo.support.util.SessionUtil;

/**
 * 请求信息持有者
 */
public final class RequestIdentityHolder {

	private final static ThreadLocalMap HOLDER = new ThreadLocalMap();
	public final static String RID = "rid";
	public final static String RNAME = "rname";

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
			MDC.put(RNAME, StringUtils.trimToEmpty(requestInfo.getName()));
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
	public static RequestInfo get(Task task, boolean autoCreate){
		RequestInfo ri = get();
		if(ri == null && autoCreate){
			ri = generateNew(task);
			set(ri);
		}
		return ri;
	}
	
	/**
	 * 生成一个新的RequestInfo,但不自动与当前线程绑定
	 * @return
	 */
	public static RequestInfo generateNew(Task task){
		RequestInfo requestInfo = new RequestInfo(SessionUtil.getSessionId(),task.getName());
		return requestInfo;
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
		MDC.remove(RNAME);
	}
	
}