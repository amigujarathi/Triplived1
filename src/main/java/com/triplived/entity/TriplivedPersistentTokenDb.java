package com.triplived.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * based on Spring Rememberme Token
 * 
 * @author santosh
 *
 */
@Entity
@Table(name = "persistent_logins")
public class TriplivedPersistentTokenDb implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String series;
	private String tokenValue;
	private Date date;
	private String status;

	@Column(name = "username",  nullable = false, length = 128)
	public String getUsername() {
		return username;
	}

	@Id
	@Column(name = "series", unique = true, nullable = false, length = 128)
	public String getSeries() {
		return series;
	}

	@Column(name = "token",   nullable = false, length = 128)
	public String getTokenValue() {
		return tokenValue;
	}

	@Column(name = "last_used",  nullable = false, length = 20)
	public Date getDate() {
		return date;
	}
	
	 @Column(name = "status")
	public String getStatus() {
		return status;
	}
	 
	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
