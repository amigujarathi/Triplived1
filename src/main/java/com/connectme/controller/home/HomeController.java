package com.connectme.controller.home;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





@Controller
@RequestMapping("/home")
public class HomeController {


    private static final Logger logger = LoggerFactory.getLogger(HomeController.class );

    /*@RequestMapping(method= RequestMethod.GET)
    public String index(Principal principal, HttpSession session, Model model, HttpServletRequest request) {
    	Person user = (Person) session.getAttribute(Constants.LOGIN_USER);
    	if(user == null ) {
    		StringBuffer userLog = new StringBuffer();
    		userLog.append("User entered  site, ");
    	
    		   String ipAddress = request.getHeader("X-FORWARDED-FOR");  
    		   if (ipAddress != null) {  
    			   userLog.append("Ip address : " + ipAddress + " , ");  
    		   }
    		
    		   userLog.append("Referrer of this request : " + request.getHeader("referer"));
    		   
    		   logger.warn(userLog.toString());
    		   
    	    return "index";
    	}
    	logger.warn("User entered  name :{}  and id {}" , user.getName(), user.getId());
    	return "attraction";*/
	//}
    
    @RequestMapping(method= RequestMethod.GET, value="/logout")
    public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:";
    }
}
