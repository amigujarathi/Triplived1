package com.connectme.domain.triplived.dto;

import java.util.List;

public class PersonDTO {

	private String name;
	private String aboutMe;
	private String email;
	private String phoneNo;
	private String personFbId;
	private String id;
	private String location;
	private String blog;
	private List<String> badges;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public List<String> getBadges() {
		return badges;
	}
	public void setBadges(List<String> badges) {
		this.badges = badges;
	}
	public String getPersonFbId() {
		return personFbId;
	}
	public void setPersonFbId(String personFbId) {
		this.personFbId = personFbId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
