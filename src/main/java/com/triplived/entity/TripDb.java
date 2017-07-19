package com.triplived.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="trip")
public class TripDb {

	@Id
    @Column(name="ID")
    @GeneratedValue
	private Long id;
	
	@Column(name="TRIP_DATA")
	private String tripData;
	
	@Column(name="TRIP_DATA_EDITED")
	private String tripDataEdited;
	
	@Column(name="UPDATED_DATE")
	private Date updateDate;
	
	
	@Column(name="TRIP_COVER_URI")
	private String tripCoverUri;
	
	@Column(name="TRIP_SOURCE")
	private String tripSource;
	
	@Column(name="TRIP_NAME")
	private String tripName;
	
	@Column(name="TRIP_TYPE")
	private String tripType;//either timeline or static Iteneary
	
	@Column(name="TRIP_VIDEO_SERVER_PATH")
	private String videoServerPath;
	
	@Column(name="TRIP_VIDEO_YOUTUBE_PATH")
	private String videoYoutubePath;
	
	@Column(name="TRIP_VIDEO_YOUTUBE_STATUS")
	private String videoYoutubeStatus;
	
	
    @Column(name = "CREATED_DATE")
	private Date createDate;
	
    @Column(name = "TRIP_CATEGORY")
	private String tripCategory;
    
    @Column(name = "PUBLIC_ID")
    private Long tripPublicId;
    
    @Column(name = "END_DATE")
    private Long endDate;

    @Column(name = "TRIP_STATE")
	private String tripState;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTripData() {
		return tripData;
	}

	public void setTripData(String tripData) {
		this.tripData = tripData;
	}

	public String getTripDataEdited() {
		return tripDataEdited;
	}

	public void setTripDataEdited(String tripDataEdited) {
		this.tripDataEdited = tripDataEdited;
	}

	public String getTripCoverUri() {
		return tripCoverUri;
	}

	public void setTripCoverUri(String tripCoverUri) {
		this.tripCoverUri = tripCoverUri;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}



	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	public String getVideoServerPath() {
		return videoServerPath;
	}



	public void setVideoServerPath(String videoServerPath) {
		this.videoServerPath = videoServerPath;
	}



	public String getVideoYoutubePath() {
		return videoYoutubePath;
	}



	public void setVideoYoutubePath(String videoYoutubePath) {
		this.videoYoutubePath = videoYoutubePath;
	}



	public String getVideoYoutubeStatus() {
		return videoYoutubeStatus;
	}



	public void setVideoYoutubeStatus(String videoYoutubeStatus) {
		this.videoYoutubeStatus = videoYoutubeStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTripCategory() {
		return tripCategory;
	}

	public void setTripCategory(String tripCategory) {
		this.tripCategory = tripCategory;
	}

	public Long getTripPublicId() {
		return tripPublicId;
	}

	public void setTripPublicId(Long tripPublicId) {
		this.tripPublicId = tripPublicId;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public String getTripSource() {
		return tripSource;
	}

	public void setTripSource(String tripSource) {
		this.tripSource = tripSource;
	}

	public String getTripState() {
		return tripState;
	}

	public void setTripState(String tripState) {
		this.tripState = tripState;
	}




	/*public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}*/
}
