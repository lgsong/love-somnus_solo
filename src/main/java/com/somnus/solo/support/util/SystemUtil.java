package com.somnus.solo.support.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtil {
	private static String ip = null;
	private static String machine = null;

	private static void init(){
		InetAddress addr = null;
	    try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		 
		ip = addr.getHostAddress().toString();//获得本机IP 
		machine = addr.getHostName().toString();//获得本机名称 
	}
	/**
	 * 
	 * @return 机器IP
	 */
	public static String getIp(){
		if(ip == null){
			init();
		}
		return ip;
	}
	/**
	 * 
	 * @return 机器编号
	 */
	public static String getMachine(){
		if(machine == null){
			init();
		}
		return machine;
	}
	
}
