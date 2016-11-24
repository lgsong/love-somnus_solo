package com.somnus.solo.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.somnus.solo.message.Message;
import com.somnus.solo.support.common.MsgCodeList;
import com.somnus.solo.support.exceptions.BizException;
import com.somnus.solo.support.util.MD5Util;

/**
 * @ClassName:     BaseController.java
 * @Description:   TODO
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年9月23日 下午2:38:56
 */
public abstract class BaseController {
	
	private transient Logger       log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${host.sign.key}")
	private String key;
	
	@Autowired
	private MessageSourceAccessor  msa;

	protected void fieldChecked(BindingResult result){
		if(result.hasErrors()){
			FieldError error = result.getFieldErrors().get(0);
			throw new BizException(msa.getMessage(MsgCodeList.ERROR_100001, 
					new Object[]{error.getField(), error.getDefaultMessage()}));
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void signChecked(Message message) throws Exception{
		Map<String,Object> map = BeanUtils.describe(message);
		map.remove("class");
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.putAll(map);
		
		StringBuilder signBuilder = new StringBuilder();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			String k = entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)  && !"sign".equals(k) && !"key".equals(k)) {
				signBuilder.append(k + "=" + v + "&");
			}
		}
		log.info("传递的参数为-->{}",signBuilder.toString());
		//拼接key进行MD5签名
		signBuilder.append("key=" + key);
		String sign = MD5Util.md5(signBuilder.toString(), "UTF-8").toUpperCase();
		log.info("系统sign签名为-->{}",sign);
		//验签
		if(!message.getSign().equals(sign)){
			throw new BizException(msa.getMessage(MsgCodeList.ERROR_100002, 
					new Object[]{"请求端sign签名->".concat(message.getSign()),}));
		}
	}
}
