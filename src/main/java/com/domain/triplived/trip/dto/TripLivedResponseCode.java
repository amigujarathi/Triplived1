package com.domain.triplived.trip.dto;

/**
 * 
 * TripLived Error or Success Messages
 * 
 * @author Santosh 
 *
 */
public enum TripLivedResponseCode {

	/**
	 * Everything is fine 
	 */
	RESPONSE_OK(200), 
	
	/**
	 * Some exception
	 */
	RESPONSE_EXCEPTION(500),
	
	/**
	 * 
	 */
	RESPONSE_NOT_FOUND(60),
	/**
	 * 
	 */
	RESPONSE_ERROR(500),
	
	/**
	 * Tempared
	 */
	RESPONSE_TEMPERED(501);
	
	private int code ;
	
	public int getCode() {
		return this.code;
	}
	
	TripLivedResponseCode(int code) {
		this.code = code ;
	}
}
