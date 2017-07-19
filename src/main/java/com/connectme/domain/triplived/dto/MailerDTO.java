package com.connectme.domain.triplived.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.triplived.service.profile.PersonProfile;

public class MailerDTO {
	
	private String name;
	private String email;
	private String password;
	private String templateName;
	private String subject;
	private String users;
	private String designation;
	
	private List<PersonProfile> listUser;
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonProperty("templateName")
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	@JsonProperty("subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@JsonProperty("users")
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	
	public List<PersonProfile> getListUser() {
		return listUser;
	}
	
	public void setListUser(List<PersonProfile> listUser) {
		this.listUser = listUser;
	}
	
	@JsonProperty("designation")
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	@Override
	public String toString() {
		return "MailerDTO [name=" + name + ", email=" + email + ", designation=" + designation + ", templateName="
				+ templateName + ", subject=" + subject + ", users=" + users + "]";
	}
	
 
	
	
	 
	
}
