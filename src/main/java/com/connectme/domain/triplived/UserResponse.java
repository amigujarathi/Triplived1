package com.connectme.domain.triplived;

import java.util.List;

public class UserResponse {
	
	private String name;
	private String id;
	private String role;
	private String fbId;
	private String accountId;
	private List<String> tripIds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<String> getTripIds() {
		return tripIds;
	}
	public void setTripIds(List<String> tripIds) {
		this.tripIds = tripIds;
	}
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
