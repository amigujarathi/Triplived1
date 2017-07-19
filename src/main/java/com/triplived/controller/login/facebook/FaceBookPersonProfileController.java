package com.triplived.controller.login.facebook;

import javax.servlet.http.HttpSession;

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
import com.triplived.controller.login.facebook.pojo.FaceBookProfile;
import com.triplived.controller.profile.Person;
import com.triplived.service.profile.MapProfileFromProviderService;
import com.triplived.util.Constants;

/**
 * 
 * @author santosh joshi
 *
 *  What ever person's profile data we will receive from facebook, we will try to create his/ her profile here 
 *  at our end. this controller merely helps in creating in users profile in our database by using facebook's data.
 *   
 */
@Controller
@RequestMapping(value = "/facebook")
public class FaceBookPersonProfileController extends AbstractSignupController {

    private static final Logger logger = LoggerFactory.getLogger(FaceBookPersonProfileController.class );
    
  	@Autowired
	@Qualifier("facebokprofilesercice")
	private MapProfileFromProviderService<FaceBookProfile> profileMapperService; 
	
	@RequestMapping(method = RequestMethod.POST, value = "/createProfile")
	public @ResponseBody String createProfileFromFaceBookData(@RequestBody FaceBookProfile facebookUserProfileData, HttpSession session , Model model ) {
		
		Gson gson = new Gson();
	   	try{
		logger.warn("Person Logged in from face book :  name is  {}  - {} and mail:{}", facebookUserProfileData.getFirst_name(), facebookUserProfileData.getLast_name(),
	    			facebookUserProfileData.getEmail());
		logger.warn("Person details while logging in from FB: " + gson.toJson(facebookUserProfileData));

	   	Person person  = profileMapperService.mapProfileInformation(facebookUserProfileData);
		session.setAttribute(Constants.LOGIN_USER, person);

		if(isProfileComplete(person)){
			fireProfileEvent(person.getPersonId());
		}
		
		return new Gson().toJson(person);
	   	}catch (Exception e) {
	   		logger.error("Error encountered while logging in from FB : {} ", e);
	   		return "Error while logging in from FB";
	   	}
	}
	
}
