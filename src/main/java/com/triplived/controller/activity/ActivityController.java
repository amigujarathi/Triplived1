package com.triplived.controller.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.ActivityDTO;
import com.google.gson.Gson;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	
	@Value("${activities}")
	private String activities;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)	
	public @ResponseBody String getActivities(@RequestParam("param") String param) {
		
		String[] activityArray = activities.split(",");
		List<String> activityList = Arrays.asList(activityArray);
		
		List<ActivityDTO> desiredActivities = new ArrayList<ActivityDTO>();
		int activityCounter = 0;
		
		for(String s : activityList) {
	        		
			activityCounter++;
			String lowerParm = param.toLowerCase();
			String lowerStr = s.toLowerCase();
			if(lowerStr.startsWith(lowerParm)) {
				ActivityDTO activity = new ActivityDTO();
				activity.setId(activityCounter);
				activity.setName(s);
				desiredActivities.add(activity);
			}
		}
		Gson gson = new Gson();
		return gson.toJson(desiredActivities);
	}
	

}
