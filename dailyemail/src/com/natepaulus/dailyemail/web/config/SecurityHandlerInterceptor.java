package com.natepaulus.dailyemail.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.natepaulus.dailyemail.repository.User;
import com.natepaulus.dailyemail.web.controller.LoginController;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;

public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) WebUtils.getSessionAttribute(request, "user");
		if(user == null){
			String url = request.getRequestURL().toString();
			WebUtils.setSessionAttribute(request, LoginController.REQUESTED_URL, url);
			throw new AuthenticationException("Authentication required.");
		}
		
		return true;
	}
}
