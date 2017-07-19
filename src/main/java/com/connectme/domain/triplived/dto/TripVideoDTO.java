package com.connectme.domain.triplived.dto;

import com.connectme.domain.triplived.VideoStatus;


public class TripVideoDTO {
	
	private Long tripID;
	private VideoStatus tripVideoStatus;
	public Long getTripID() {
		return tripID;
	}
	public void setTripID(Long tripID) {
		this.tripID = tripID;
	}
	public VideoStatus getTripVideoStatus() {
		return tripVideoStatus;
	}
	public void setTripVideoStatus(VideoStatus videoStatus) {
		this.tripVideoStatus = videoStatus;
	}
	
	
}
