package com.triplived.controller.log;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.Analytics;
import com.connectme.domain.triplived.UserResponse;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.triplived.rest.logProcessing.ActiveUsersDetailLogClient;
import com.triplived.rest.logProcessing.LogClient;
import com.triplived.service.logProcessing.LogProcessingService;
import com.triplived.service.user.UserService;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping(value = "/log")
public class LogController {
	
	private static final Logger logger = LoggerFactory.getLogger(LogController.class);
	
	@Autowired
	private LogClient logClient;
	
	@Autowired
	private LogProcessingService logService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/getLogs", method= RequestMethod.GET)
	public String getLogsFromServer(@RequestParam("fromTime") String fromTime,
									@RequestParam("toTime") String toTime,
									@RequestParam("deviceId") String deviceId, Model model) throws Exception{
		 
		
		 List<String> logList = logClient.execCommandAndGetLogs(fromTime, toTime, deviceId);
		 model.addAttribute("logList", logList);
		 return "log-view";
	}
	
	
	
	@RequestMapping(value = "/getActiveUsers", method= RequestMethod.GET)
	public @ResponseBody Boolean getActiveUsers(Model model) throws Exception{
		 
		
		 logService.getActiveUsers();
		 return true;
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/analytics")
    public @ResponseBody Boolean logAnalytics(Principal principal, final HttpServletResponse response, 
    		@RequestBody Analytics analytics,
			HttpSession session, HttpServletRequest request) {
		
		if(null != analytics.getUserId()) {
			
			try {
				UserResponse user = userService.getUserDetailsForAnalytics(analytics.getUserId());
				if(null != user) {
					analytics.setUserName(user.getName());
				}
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		}
		logger.warn("Analytics logged from device - {} . Analytics : {}", analytics.getDeviceId(), analytics.toString());
		return true;
		
	}
	
	public static void log(Analytics analytics){
		
		logger.warn("Analytics logged from device - {} . Analytics : {}", analytics.getDeviceId(), analytics.toString());
	}
	
	//@Scheduled(cron = "0 1 1 * * ?")
    public void sendActiveUsersReport() {
		try {
			logService.getActiveUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
