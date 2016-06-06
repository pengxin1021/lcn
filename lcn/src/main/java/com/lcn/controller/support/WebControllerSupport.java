package com.lcn.controller.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.lcn.entity.BaseResult;

public abstract class WebControllerSupport {
	
	protected HttpServletRequest request;
	
	public BaseResult successResult(Object message){
		BaseResult result = new BaseResult();
		result.setSuccess(true);
		result.setMessage(message);
		return result;
	}
	
	public BaseResult erroResult(Object message){
		BaseResult result = new BaseResult();
		result.setSuccess(false);
		result.setMessage(message);
		return result;
	}
	
	public void execute(HttpServletRequest request, Model model){};
}
