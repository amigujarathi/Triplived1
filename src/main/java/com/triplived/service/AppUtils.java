package com.triplived.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.event.listener.Handler;

@Service
public class AppUtils {

	@Autowired
	private ApplicationContext _applicationContex;
	 
	public Handler getHandler(String name) {
		
		return _applicationContex.getBean(name, Handler.class); 
	}
}
