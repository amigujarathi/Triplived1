package com.connectme.controller.home;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("*")

public class WelcomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class );

	@RequestMapping(method= RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		
			   
			return "index";
		
	}
}
