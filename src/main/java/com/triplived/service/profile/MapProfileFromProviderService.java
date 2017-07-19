package com.triplived.service.profile;


import com.triplived.controller.profile.GenericProfile;
import com.triplived.controller.profile.Person;

/**
 * 
 * @author santosh joshi
 *
 */
public interface MapProfileFromProviderService< T extends GenericProfile> {

	public Person mapProfileInformation(T profile) throws Exception;

	//public void updateProfile(Person person, PersonProfile profile); 
}
