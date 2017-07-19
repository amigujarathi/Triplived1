package com.domain.triplived.transport.dto;

import java.util.List;

import com.connectme.domain.triplived.EntityResponse;

public class ValidRouteRestDTO implements Comparable<ValidRouteRestDTO>{
	
	private Double totalDistance;
	private Integer totalDuration;
	private String routeName;
	private List<ValidTransportRestDTO> validTransports;
	
	public Double getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}
	public Integer getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public List<ValidTransportRestDTO> getValidTransports() {
		return validTransports;
	}
	public void setValidTransports(List<ValidTransportRestDTO> validTransports) {
		this.validTransports = validTransports;
	}

	@Override
	public int compareTo(ValidRouteRestDTO obj) {
		if(this.totalDuration >= obj.totalDuration) {
			return 1;
		} else {
			return -1;
		}
	}
}
