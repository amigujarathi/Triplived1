package com.gcm;

public enum GCMNotificationStatus {
	
	NOT_DELIVERED("N"),
	DELIVERED("D"),
	ACCEPTED("A"),
	REJECTED("R"),
	OTHERS("others");
	
	String type;
	
	public String getType() {
		return type;
	}
	
	GCMNotificationStatus(String type){
		
		this.type = type;
	}

}
