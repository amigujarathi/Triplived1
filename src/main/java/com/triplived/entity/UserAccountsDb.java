package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.triplived.controller.profile.UserFrom;

@Entity
@Table(name = "user_accounts", catalog = "trip")
public class UserAccountsDb {

    private PersonDb person;
    
	private Long id;
	
	private UserFrom userFrom;
	
	private String email;
	
	private Date createdDate;

	private String accountId;
	
	private String userImage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	public PersonDb getPerson() {
		return person;
	}

	public void setPerson(PersonDb person) {
		this.person = person;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false, length = 30)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_FROM", length = 20)
	@Enumerated(EnumType.STRING)
	public UserFrom getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(UserFrom userFrom) {
		this.userFrom = userFrom;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "ACCOUNT_ID", length = 100, nullable=false, unique=true)
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Column(name = "USER_IMAGE", length = 500)
	public String getUserImage() {
		return userImage;
	}
	
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
}