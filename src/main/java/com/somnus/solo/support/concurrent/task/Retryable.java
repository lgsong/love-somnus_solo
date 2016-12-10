package com.somnus.solo.support.concurrent.task;

/**
 * @description: 可重试任务接口
 * @author:  Somnus
 * @version: 1.0
 * @createDate: 2016年4月19日 下午3:16:25 
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年4月19日       Somnus                             1.0            
 */
public interface Retryable {

	/**
	 * 获取可重试次数
	 * @return 可重试次数
	 */
	public int getTotal();
	
	/**
	 * 获取重试时间间隔(毫秒)
	 * @return 重试时间间隔(毫秒)
	 */
	public long getInterval();
	
}
