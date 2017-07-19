package com.dataLogger.triplived;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller
@RequestMapping("/analysis_data_logging")
public class DataLogging {


    private static final Logger logger = LoggerFactory.getLogger(DataLogging.class );

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public String index(HttpServletRequest request) {
    	
    	
    	StringBuilder sb = new StringBuilder("Request from DEVICE is ");
    	for (Object object : request.getParameterMap().entrySet()) {
			
    		Map.Entry entry  = (Map.Entry) object;
    		sb.append(entry.getKey()).append("="+ ( (String []) entry.getValue() )[0]).append("  # ");
		}
    	//System.out.println(sb.toString());
    	logger.warn(sb.toString());
    	 
    	return "OK";
	}
    
}
