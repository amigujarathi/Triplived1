package com.triplived.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reportuser")
public class ReportUserDb {

	@Id
    @Column(name="userid")
    @GeneratedValue
	private Long userid;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	
	public Long getUserId() {
		return userid;
	}

	public void setUserId(Long userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return username;
	}

	public void setName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
