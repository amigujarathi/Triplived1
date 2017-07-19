package com.connectme.domain.triplived.trip.dto;

import java.util.List;

public class SubTrip {
	
	private Long id;
	private TripCityDTO fromCityDTO;
	private List<TripTransportDTO> tripTransportDTO;
	private TripCityDTO toCityDTO;
	
	private Boolean complete;
	
	public SubTrip() {}
	
	public SubTrip(Long id) {
		this.id = id;
		this.complete = false;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TripCityDTO getFromCityDTO() {
		return fromCityDTO;
	}
	public void setFromCityDTO(TripCityDTO fromCityDTO) {
		this.fromCityDTO = fromCityDTO;
	}
	public List<TripTransportDTO> getTripTransportDTO() {
		return tripTransportDTO;
	}
	public void setTripTransportDTO(List<TripTransportDTO> tripTransportDTO) {
		this.tripTransportDTO = tripTransportDTO;
	}
	public TripCityDTO getToCityDTO() {
		return toCityDTO;
	}
	public void setToCityDTO(TripCityDTO toCityDTO) {
		this.toCityDTO = toCityDTO;
	}
	

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	
}
