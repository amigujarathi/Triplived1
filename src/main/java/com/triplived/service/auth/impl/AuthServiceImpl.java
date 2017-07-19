package com.triplived.service.auth.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.service.auth.IAuthService;
import com.triplived.service.auth.user.TriplivedUser;

 
public class AuthServiceImpl implements IAuthService, UserDetailsService {

	//private SearchService searchService;
	
	/*public AuthServiceImpl(SearchService searchService) {
	    this.searchService =  searchService;
	}*/
	

	@Transactional
	@Override
	public TriplivedUser loadUserByUsername(String email)	throws UsernameNotFoundException {
/*
		Person details = this.searchService.findPeopleByEmail(email);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		TriplivedUser user = new TriplivedUser(details.getEmail(), details.getPassword(), true, true, true, true, authorities, details);
		return user;*/
		
		return null;
	}
}
