package com.triplived.controller.staticitenery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/itenary")
public class StaticItenaryController {

	
	@RequestMapping(method= RequestMethod.GET)
    public String getAttractionsByCity() {
	
    	return "static-itenary" ;
    }
}
