package com.domain.triplived.transport.dto;

public enum ValidTransportType {

	Flight("Flight"),
	Train("Train"),
	Bus("Bus"),
	Ferry("Ferry"),
	Car("Car"),
	Drive("Car"),
	Taxi("Car"),
	Uber("Car"),
	Metro("Train");
	
    String type;
	
	public String getType() {
		return type;
	}
	
	ValidTransportType(String type){
		
		this.type = type;
	}
}
