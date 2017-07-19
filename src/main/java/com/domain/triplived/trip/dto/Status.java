package com.domain.triplived.trip.dto;

/**
 * 
 * @author santosh
 *
 */
public enum Status {

	UPVOTE("UPVOTE"),
	DOWNVOTE("DOWNVOTE"), 
	INACTIVE("INACTIVE"), 
	DELETED("DELETED"),
	LIKE("LIKE"),
	UNLIKE("UNLIKE"),
	UNKNOWN("UNKNOWN");
	
	private String status;
	
	Status(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public static Status getStatus(String statusString){
		
		for(Status s: values()){
			if(s.getStatus().equalsIgnoreCase(statusString)){
				return s;
			}
		}
		return UNKNOWN;
	}
	
	public static Status getEnum(String value) {
	    for(Status v : values())
	        if(v.getStatus().equalsIgnoreCase(value)) return v;
	    throw new IllegalArgumentException();
	}
}
