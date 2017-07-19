package com.triplived.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.triplived.controller.profile.UserFrom;

@Entity
@Table(name = "user", catalog = "trip")
public class PersonDb {

    private Long personId;
	private Long id;
	private String name;
	private String middleName;
	private String lastName;
	private String gender;
	private UserFrom userFrom;
	private Date dateOfBirth;
	private String aboutMe;
	private String email;
	private String password;
	private String mobile;
	private String nickName;
	private String userType;
	private String deviceId;
	private String location;
	private String website;
	
	private String badges;
	
	private String fbAccessToken;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade= CascadeType.ALL)
	private Set<UserAccountsDb> userAccounts = new HashSet<UserAccountsDb>(0);
	
	
	//@Column(name = "CREATED_DATE", nullable = false, length = 19)
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE", length = 19)
	private Date updatedDate;
	private String status;
	
	@Column(name = "STATUS", length = 2) 
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID", unique = true, nullable = false, length = 30)
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	@Column(name = "ID", unique = true, nullable = false, length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "NAME", length = 20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "MIDDLE_NAME", length = 20)
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Column(name = "LAST_NAME", length = 20)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "GENDER", length = 2)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column(name = "USER_FROM", length = 20)
	@Enumerated(EnumType.STRING)
	public UserFrom getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(UserFrom userFrom) {
		this.userFrom = userFrom;
	}
	
	@Column(name = "DATE_OF_BIRTH")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Column(name = "ABOUT_ME", length = 500)
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PASSWORD", length = 20)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "NICKNAME", length = 20)
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Column(name = "USER_TYPE", length = 3)
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
		
	@Column(name = "DEVICE_ID", length = 3)
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	@Column(name = "UPDATED_DATE", length = 19)
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Column(name = "FB_ACCESS_TOKEN")
	public String getFbAccessToken() {
		return fbAccessToken;
	}

	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}

	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "WEB_SITE")
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Column(name = "BADGES")
	public String getBadges() {
		return badges;
	}
	
	public void setBadges(String badges) {
		this.badges = badges;
	}
	
	@PrePersist
	public void prePersist() {
		 this.createdDate = new Date();
	}

	@PostUpdate
	public void postUpdate() {
		this.updatedDate = new Date();
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade= CascadeType.ALL)
	public Set<UserAccountsDb> getUserAccounts() {
		return userAccounts;
	}
	
	public void setUserAccounts(Set<UserAccountsDb> userAccounts) {
		this.userAccounts = userAccounts;
	}
 
	
}