package com.triplived.controller.profile;


public class Person {

    Long personId;
	
	Long id;
	
	private String name;

	private String middleName;
	
	private String lastName;
	
	private String gender;
	
	private String age;

	private UserFrom userFrom;
	private String dateOfBirth;
	private String aboutMe;
	private String email;
	private String password;
	private String mobile;
	private Long lastMsgReadTime;
	private String showTelephoneCheck;
	private String showEmailCheck;
	private String isProfessionFilled;
	private String nickName;
	private String userType;
	private String userImage;
	
	private String address;
	private String link;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public UserFrom getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(UserFrom userFrom) {
		this.userFrom = userFrom;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getLastMsgReadTime() {
		return lastMsgReadTime;
	}
	public void setLastMsgReadTime(Long lastMsgReadTime) {
		this.lastMsgReadTime = lastMsgReadTime;
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
	public String getIsProfessionFilled() {
		return isProfessionFilled;
	}
	public void setIsProfessionFilled(String isProfessionFilled) {
		this.isProfessionFilled = isProfessionFilled;
	}
	 
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserImage() {
		return userImage;
	}
	
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	
}