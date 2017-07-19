package com.domain.triplived.trip.dto;

import com.triplived.controller.profile.UserFrom;

/**
 * 
 * Who all people liked a Trip / Photo / Video / Ettity
 * 
 * @author Santosh Joshi
 *
 */
public class PeopleLiked {

	private String firstName;
	private String lastName;
	private String profilePhoto;
	private Long id;
	private Long personId;
	private UserFrom userFrom;
	
	public PeopleLiked() {
		// TODO Auto-generated constructor stub
	}
	
 
	public PeopleLiked(String firstName, String lastName, String userFrom, String profilePhoto) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePhoto = profilePhoto;
		//this.id = id;
		this.userFrom = UserFrom.getUser(userFrom);
		//this.personId = personId;
	}
	
	public PeopleLiked(String firstName, String lastName, String userFrom, String profilePhoto, Long id, Long personId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePhoto = profilePhoto;
		this.id = id;
		this.userFrom = UserFrom.getUser(userFrom);
		this.personId = personId;
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserFrom getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(UserFrom userFrom) {
		this.userFrom = userFrom;
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
}
