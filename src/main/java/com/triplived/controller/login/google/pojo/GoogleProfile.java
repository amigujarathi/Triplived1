package com.triplived.controller.login.google.pojo;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

import com.triplived.controller.profile.GenericProfile;
import com.triplived.controller.profile.UserFrom;

public class GoogleProfile extends GenericProfile {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("personImage")
	private String personImage;
	
	@JsonProperty("first_name")
	private String first_name;
	
	@JsonProperty("last_name")
	private String last_name;
	
	@JsonProperty("link")
	private String link;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("birthday")
	private String birthday;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("timezone")
	private Double timezone;
	
	@JsonProperty("locale")
	private String locale;
	
	@JsonProperty("verified")
	private Boolean verified;
	
	@JsonProperty("fb_access_token")
	private String fb_access_token;
	
	@JsonProperty("updated_time")
	private String updated_time;
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public String getId() {
	return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
	this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
	return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
	this.name = name;
	}

	@JsonProperty("first_name")
	public String getFirst_name() {
	return first_name;
	}

	@JsonProperty("first_name")
	public void setFirst_name(String first_name) {
	this.first_name = first_name;
	}

	@JsonProperty("last_name")
	public String getLast_name() {
	return last_name;
	}

	@JsonProperty("last_name")
	public void setLast_name(String last_name) {
	this.last_name = last_name;
	}

	@JsonProperty("link")
	public String getLink() {
	return link;
	}

	@JsonProperty("link")
	public void setLink(String link) {
	this.link = link;
	}

	@JsonProperty("username")
	public String getUsername() {
	return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
	this.username = username;
	}

	@JsonProperty("birthday")
	public String getBirthday() {
	return birthday;
	}

	@JsonProperty("birthday")
	public void setBirthday(String birthday) {
	this.birthday = birthday;
	}

	@JsonProperty("gender")
	public String getGender() {
	return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
	this.gender = gender;
	}

	@JsonProperty("email")
	public String getEmail() {
	return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
	this.email = email;
	}

	@JsonProperty("timezone")
	public Double getTimezone() {
	return timezone;
	}

	@JsonProperty("timezone")
	public void setTimezone(Double timezone) {
	this.timezone = timezone;
	}

	@JsonProperty("locale")
	public String getLocale() {
	return locale;
	}

	@JsonProperty("locale")
	public void setLocale(String locale) {
	this.locale = locale;
	}

	@JsonProperty("verified")
	public Boolean getVerified() {
	return verified;
	}

	@JsonProperty("verified")
	public void setVerified(Boolean verified) {
	this.verified = verified;
	}

	@JsonProperty("fb_access_token")
	public String getFb_access_token() {
		return fb_access_token;
	}

	@JsonProperty("fb_access_token")
	public void setFb_access_token(String fb_access_token) {
		this.fb_access_token = fb_access_token;
	}

	@JsonProperty("updated_time")
	public String getUpdated_time() {
	return updated_time;
	}

	@JsonProperty("updated_time")
	public void setUpdated_time(String updated_time) {
	this.updated_time = updated_time;
	}
	
	
	@JsonProperty("personImage")
	public String getPersonImage() {
		return personImage;
	}
	
	@JsonProperty("personImage")
	public void setPersonImage(String personImage) {
		this.personImage = personImage;
	}
	

	@Override
	public int hashCode() {
	return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
	return EqualsBuilder.reflectionEquals(this, other);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public UserFrom getUserFrom() {
		return UserFrom.GOOGLE;
	}
}
