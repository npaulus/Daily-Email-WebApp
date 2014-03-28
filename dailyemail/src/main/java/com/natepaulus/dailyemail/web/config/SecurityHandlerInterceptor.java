package com.natepaulus.dailyemail.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.natepaulus.dailyemail.web.controller.LoginController;
import com.natepaulus.dailyemail.web.exceptions.AuthenticationException;

/**
 * The Class SecurityHandlerInterceptor is checking to ensure certain pages are only accessible if the user has logged into the
 * application.
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final Long user = (long) WebUtils.getSessionAttribute(request, "user");
		if (user == null) {
			final String url = request.getRequestURL().toString();
			WebUtils.setSessionAttribute(request, LoginController.REQUESTED_URL, url);
			throw new AuthenticationException("Authentication required.");
		}

		return true;
	}
}
