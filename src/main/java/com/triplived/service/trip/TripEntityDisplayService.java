package com.triplived.service.trip;


import java.util.List;

import com.connectme.domain.triplived.EntityResponse;


public interface TripEntityDisplayService {
	
	List<EntityResponse> getEntityDetailsFromId(Long tripId);

		
}
