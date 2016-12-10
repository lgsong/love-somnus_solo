package com.somnus.solo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.somnus.solo.service.MerAccountService;
import com.somnus.solo.message.Message;
import com.somnus.solo.message.sample.MerAccountQueryRequest;
import com.somnus.solo.message.sample.MerAccountQueryResponse;
import com.somnus.solo.message.sample.MeracctRequest;
import com.somnus.solo.support.common.Constants;
import com.somnus.solo.support.common.MessageUtil;
import com.somnus.solo.support.exceptions.BizException;

@Controller
@RequestMapping(value = "merAccount")
@Validated
public class MerAccountController  extends BaseController{
	private transient Logger	log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MerAccountService	merAccountService;

	@RequestMapping(value="selectByAcctcode", method = {RequestMethod.POST})
	@ResponseBody
	public MerAccountQueryResponse selectByAcctcode(@Valid @RequestBody MerAccountQueryRequest request,BindingResult result) {
		log.info(Constants.REQUEST_MSG, JSON.toJSONString(request));
		MerAccountQueryResponse response = new MerAccountQueryResponse();
		try {
			//MD5签名校验
			signChecked(request);
			response = merAccountService.selectByAcctcode(request);
			// 返回成功报文
			MessageUtil.createCommMsg(response);
		} catch (BizException e) {
			log.error(Constants.BUSINESS_ERROR, e);
			// 组织错误报文
			MessageUtil.errRetrunInAction(response, e);
		} catch (Exception ex) {
			log.error(Constants.EXCEPTION_ERROR, ex);
			// 组织错误报文
			MessageUtil.createErrorMsg(response);
		}
		log.info(Constants.REPONSE_MSG, JSON.toJSONString(response));
		return response;
	}

	@RequestMapping(value="createMeracct", method = {RequestMethod.POST})
	@ResponseBody
	public Message createMeracct(@Valid @RequestBody MeracctRequest request,BindingResult result) {
		log.info(Constants.REQUEST_MSG, JSON.toJSONString(request));
		Message message = new Message();
		try {
			//MD5签名校验
			signChecked(request);
			merAccountService.createMeracct(request);
			// 返回成功报文
			message = MessageUtil.createCommMsg();
		} catch (BizException e) {
			log.error(Constants.BUSINESS_ERROR, e);
			// 组织错误报文
			message = MessageUtil.errRetrunInAction(e);
		} catch (Exception ex) {
			log.error(Constants.EXCEPTION_ERROR, ex);
			// 组织错误报文
			message = MessageUtil.createErrorMsg();
		}
		log.info(Constants.REPONSE_MSG, JSON.toJSONString(message));
		return message;
	}
}
