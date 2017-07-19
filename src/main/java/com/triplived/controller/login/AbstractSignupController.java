package com.triplived.controller.login;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.connectme.domain.triplived.event.TlEvent;
import com.triplived.controller.profile.Person;
import com.triplived.entity.TripMetaData;
import com.triplived.service.AppUtils;

/**
 * 
 * @author santoshjo
 *
 */
public abstract class AbstractSignupController {

	
	@Autowired
	private AppUtils apputils;
	
	public void fireProfileEvent(Long userId){
		TlEvent event = new TlEvent();
		event.setUser(userId);
		event.setVerb(TripMetaData.PROFILE.toString());
		event.setActionDetails(userId+"");
		event.setBody(new Object());
		event.addToHeader(TlEvent.TYPE, TripMetaData.PROFILE);
		apputils.getHandler("userProfileHandler").setState(event);
	}
	
	protected boolean isProfileComplete(Person person) {
		
		if(StringUtils.isEmpty(person.getEmail())){
			return false;
		}
		
		if(StringUtils.isEmpty(person.getAboutMe())){
			return false;
		}
		if(StringUtils.isEmpty(person.getAddress())){
			return false;
		}
		if(StringUtils.isEmpty(person.getMobile())){
			return false;
		}
		if(StringUtils.isEmpty(person.getGender())){
			return false;
		}
	/*	if(StringUtils.isEmpty(person.getDateOfBirth())){
			return false;
		}*/
		 
		return true;
	}
    
}
