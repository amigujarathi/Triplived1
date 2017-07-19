package com.triplived.service.profile;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.triplived.controller.profile.GenericProfile;
import com.triplived.controller.profile.UserFrom;
import com.triplived.util.JsonDateDeserializer;

 

@JsonIgnoreProperties( value = {"_spring_security_remember_me"}, ignoreUnknown = true)
public class PersonProfile extends GenericProfile {

    @JsonProperty("_spring_security_remember_me")
	private String rembmberMe;
    
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("rePassword")
	private String rePassword;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("fname")
	private String fname;
	
	@JsonProperty("lname")
	private String lname;
	
	@JsonProperty("telephone")
	private String telephone;
	
	@JsonProperty("showTelephoneCheck")
	private String showTelephoneCheck;
	
	@JsonProperty("showEmailCheck")
	private String showEmailCheck;
	
	
	/**
         * @return the rembmberMe
         */
	@JsonProperty("_spring_security_remember_me")
        public String getRembmberMe() {
	    return this.rembmberMe;
        }
        
        /**
         * @param rembmberMe the rembmberMe to set
         */
	@JsonProperty("_spring_security_remember_me")
        public void setRembmberMe(String rembmberMe) {
	    this.rembmberMe = rembmberMe;
        }
        
	@JsonProperty("dateofbirth" )
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date dateofbirth;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonProperty("fname")
	public String getFname() {
		return fname;
	}
	@JsonProperty("fname")
	public void setFname(String fname) {
		this.fname = fname;
	}
	@JsonProperty("lname")
	public String getLname() {
		return lname;
	}
	@JsonProperty("lname")
	public void setLname(String lname) {
		this.lname = lname;
	}
	@JsonProperty("telephone")
	public String getTelephone() {
		return telephone;
	}
	@JsonProperty("telephone")
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@JsonProperty("dateofbirth")
	@JsonDeserialize(using = JsonDateDeserializer.class)
	public Date getDateofbirth() {
		return dateofbirth;
	}
	@JsonProperty("dateofbirth")
	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@JsonProperty("userName")
	public String getUserName() {
		return userName;
	}
	
	@JsonProperty("userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("rePassword")
	public String getRePassword() {
		return rePassword;
	}

	@JsonProperty("rePassword")
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	@Override
	public UserFrom getUserFrom() {
		return UserFrom.SITE;
	}
	 
	public String getShowTelephoneCheck() {
		return showTelephoneCheck;
	}

	public void setShowTelephoneCheck(String showTelephoneCheck) {
		this.showTelephoneCheck = showTelephoneCheck;
	}

	public String getShowEmailCheck() {
		return showEmailCheck;
	}

	public void setShowEmailCheck(String showEmailCheck) {
		this.showEmailCheck = showEmailCheck;
	}
	 
	
}
