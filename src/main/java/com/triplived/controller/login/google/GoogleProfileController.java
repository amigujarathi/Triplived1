package com.triplived.controller.login.google;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.triplived.controller.login.AbstractSignupController;
import com.triplived.controller.login.google.pojo.GoogleProfile;
import com.triplived.controller.profile.Person;
import com.triplived.service.profile.MapProfileFromProviderService;
import com.triplived.util.Constants;

/**
 * 
 * @author santosh joshi
 *
 * Creating a person's profile from google's data
 *   
 */
@Controller
@RequestMapping(value = "/google")
public class GoogleProfileController extends AbstractSignupController {

    private static final Logger logger = LoggerFactory.getLogger(GoogleProfileController.class );
    
/*	@Autowired
    private SearchService service;*/
    	
	@Autowired
	@Qualifier("googleprofilesercice")
	private MapProfileFromProviderService<GoogleProfile> profileMapperService; 
	

	@RequestMapping(method = RequestMethod.POST, value = "/createProfile")
	public @ResponseBody String createProfileFromFaceBookData(@RequestBody GoogleProfile googleUserProfileData, HttpSession session , HttpServletRequest request, Model model ) {
		
		Gson gson = new Gson();
		
		String deviceId = request.getHeader("UserDeviceId");
		if(StringUtils.isEmpty(googleUserProfileData.getDeviceId()) && StringUtils.isNotEmpty(deviceId)){
			googleUserProfileData.setDeviceId(deviceId);
		}
		
	   	try{
		logger.warn("Person Logged in from google:  name is  {} and mail:{}", googleUserProfileData.getName(), googleUserProfileData.getEmail());
		logger.warn("Person details while logging in from Google: " + gson.toJson(googleUserProfileData));

	   	Person person  = profileMapperService.mapProfileInformation(googleUserProfileData);
		session.setAttribute(Constants.LOGIN_USER, person);

		if(isProfileComplete(person)){
			fireProfileEvent(person.getPersonId());
		}
		
		return new Gson().toJson(person);
	   	}catch (Exception e) {
	   		logger.error("Error encountered while logging in from google : {}",e);
	   		return "Error while logging in from google";
	   	}
	}
}
