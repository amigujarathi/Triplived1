package com.connectme.domain.triplived.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class ContactUsDTO {
	     
	
	private String name;
	private String email;
	private String message;
	private String subject;
	
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
	
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@JsonProperty("subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return String.format("{\"name\":\"%s\", email\":\"%s\", message\":\"%s\", subject\":\"%s}", name, email, message, subject);
	}
	
	
	 
	
}
