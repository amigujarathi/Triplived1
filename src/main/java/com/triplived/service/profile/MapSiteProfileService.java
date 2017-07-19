package com.triplived.service.profile;

import org.springframework.stereotype.Service;

import com.triplived.controller.profile.Person;

/**
 * 
 * @author Santosh Joshi
 *
 */
@Service(value="personprofileservice")
public class MapSiteProfileService implements MapProfileFromProviderService<PersonProfile> {

	/*@Autowired
	private PersonRepository personRepository;*/
	
	@Override
	public Person mapProfileInformation(PersonProfile profile) {
		
		return null;
	}

}
