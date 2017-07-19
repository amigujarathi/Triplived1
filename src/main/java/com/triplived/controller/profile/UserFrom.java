package com.triplived.controller.profile;

/**
 * 
 * @author santosh joshi
 *
 * From where the user was mapped initially
 * we need this to get social profile, interests, pics 	
 */
public enum UserFrom {

	FACEBOOK("facebook"),
	GOOGLE("google"),
	YAHOO("yahoo"),
	SITE("site");
	
	private String userFrom;
	
	UserFrom(String userFrom) {
		this.userFrom = userFrom;
	}
	
	public String getUserFrom() {
		return userFrom;
	}

	public static UserFrom getUser(String userFrom) {
		
		for (UserFrom fromWhere : values()) {
			
			if(fromWhere.getUserFrom().equals(userFrom)) {
				return fromWhere ;
			}
		}
		
		throw new IllegalArgumentException("invalid user From");
	}
}
