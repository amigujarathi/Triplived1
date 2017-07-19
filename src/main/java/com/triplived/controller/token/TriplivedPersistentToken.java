package com.triplived.controller.token;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * based on Spring Remember me Token
 * 
 * @author santosh
 *
 */
public class TriplivedPersistentToken {

	@JsonProperty("username")
	private String username;
	
	@JsonProperty("series")
	private String series;
	
	@JsonProperty("tokenValue")
	private String tokenValue;
	
	@JsonProperty("date")
	private long date;

	public TriplivedPersistentToken() {
		// TODO Auto-generated constructor stub
	}
	
	public TriplivedPersistentToken(String username, String series, String tokenValue, long date) {
		this.username = username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date;
	}
	
	public TriplivedPersistentToken(String username, String series, String tokenValue, Date date) {
		this.username = username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date.getTime();
	}

	public String getUsername() {
		return username;
	}

	public String getSeries() {
		return series;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public long getDate() {
		return date;
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

	public void setDate(long date) {
		this.date = date;
	}
	
	
}
