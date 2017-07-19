package com.triplived.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.triplived.rest.geoCoder.GeoCoderRestClient;

@Controller
@RequestMapping("/api")
public class AutoCompleteAPI {

	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteAPI.class );
	
	@Autowired
	private GeoCoderRestClient geoCoder;
	
	@RequestMapping(method= RequestMethod.GET)
	public String places(){
		
		logger.warn("Google Maps Page request ");
		return "api/api";
	}
}
