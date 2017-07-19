package com.gcm;

public enum ClientMessageType {

	TIMELINE("timeline"),
	TRIP("recorded_trip"),
	ENTITY("entity"),
	WNATSUP("whats_in_your_mind"),
	TIMELINE_VIDEO("timeline_video"),
	TRIP_VIEW("trip_view"),
	LIKE("like"),
	PHOTO_LIKE("photo_like"),
	FOLLOW("follow"),
	BADGE_EARNED("badge_earned"),
	COMMENT("comment"),
	PHOTO_COMMENT("photo_comment"),
	SHARE("share"),
	WEB_VIEW ("web_view"),
	OTHERS("others");
	
	
	String type;
	
	public String getType() {
		return type;
	}
	
	ClientMessageType(String type){
		
		this.type = type;
	}
	
}
