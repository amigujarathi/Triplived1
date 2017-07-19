package com.connectme.domain.triplived;


public enum VideoStatus {

	TRIP_PUBLISH("trip_publish"),
	MAIL_SENT("mail_sent"),
	VIDEO_REQUEST_RECEIVED("video_request_received"),
	VIDEO_GENERATED("video_generated"),
	YOUTUBE_UPLOADED("youtube_uploaded"),
	YOUTUBE_LIVE("youtube_live"),
	NOTIFICATION_REQUEST_RECEIVED("notification_request_received"),
	NOTIFICATION_SENT("notification_sent");
	
	private String videoStatus;
	
	VideoStatus(String videoStatus) {
		this.videoStatus = videoStatus;
	}
	
	public String getVideoStatus() {
		return videoStatus;
	}
	
}
