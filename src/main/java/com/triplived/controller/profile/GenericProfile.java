package com.triplived.controller.profile;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author santosh joshi
 *
 */
public abstract class GenericProfile {
	
	@JsonProperty("aboutMe")
	private String aboutMe;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("mobile")
	private String mobile;
	
	@JsonProperty("personId")
	private Long personId;
	
	@JsonProperty("webSite")
	private String webSite;
	
	@JsonProperty("profileImagePath")
	private String profileImagePath;
	
 
	protected UserFrom userFrom;
	protected String deviceId;
	
	public String getProfileImagePath() {
		return profileImagePath;
	}
	
	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}
	
	public String getWebSite() {
		return webSite;
	}
	
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public abstract UserFrom getUserFrom();
}
