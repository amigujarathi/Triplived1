package com.domain.triplived.trip.dto;

public class TripResponse {

	private String message;
	private String code;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(TripLivedResponseCode code) {
		this.code = code.getCode()+"";
	}
	
	
}
