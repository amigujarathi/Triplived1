package com.triplived.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.triplived.util.Constants;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

	 @Override
	    public boolean preHandle(HttpServletRequest request,
		    HttpServletResponse response, Object handler) throws ServletException {
		 
		 
		 Integer appVersion = Integer.parseInt(request.getHeader("ApplicationVersion"));
		 if(null == appVersion) {
		    	return false;
		 }
		 if(null != appVersion && (appVersion < Constants.APP_VERSION)) {
		    	return false;
		 }
		 return true;
	 }
}
