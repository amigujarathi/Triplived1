package com.domain.triplived.transport.dto;

public enum FinalTransportType {

	FLIGHT_SURE("Flight_Sure"),
	FLIGHT_MAYBE("Flight_Maybe"),
	TRAIN_SURE("Train_Sure"),
	SURFACE_MAYBE("SURFACE_MAYBE"),
	FERRY_SURE("FERRY_SURE"),
	SURFACE_SURE("SURFACE_SURE"),
	NOT_SURE("Not_Sure");
	
    String type;
	
	public String getType() {
		return type;
	}
	
	FinalTransportType(String type){
		
		this.type = type;
	}

}
