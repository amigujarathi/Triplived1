package com.triplived.service.trip;


import com.domain.triplived.trip.dto.TimelineTrip;
import com.triplived.entity.TripDb;

public interface TripJsonEditService {

		
		TripDb displayTripJson(Long tripId);
		
		void submitTripJson(TimelineTrip tripJson,Long tripId);

}
