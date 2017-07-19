package com.connectme.domain.triplived;


public enum RequiredStatus {

	Y("Y"),
	N("N"),
	U("U");
	
	private String reqStatus;
	
	RequiredStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	
	public String getReqStatus() {
		return reqStatus;
	}
	
}
