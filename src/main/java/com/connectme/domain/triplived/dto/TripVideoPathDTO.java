package com.connectme.domain.triplived.dto;

import com.connectme.domain.triplived.VideoStatus;


public class TripVideoPathDTO {
	
	private Long tripID;
	private String serverPath ;
	private String youTubePath ;
	private String youTubeStatus ;
	public Long getTripID() {
		return tripID;
	}
	public void setTripID(Long tripID) {
		this.tripID = tripID;
	}
	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public String getYouTubePath() {
		return youTubePath;
	}
	public void setYouTubePath(String youTubePath) {
		this.youTubePath = youTubePath;
	}
	public String getYouTubeStatus() {
		return youTubeStatus;
	}
	public void setYouTubeStatus(String youTubeStatus) {
		this.youTubeStatus = youTubeStatus;
	}
		
}
