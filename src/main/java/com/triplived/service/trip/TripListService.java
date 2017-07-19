package com.triplived.service.trip;

import java.util.List;

import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.TripBetweenCityDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripAttractionDTO;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripEventDTO;
import com.triplived.entity.TripDb;

public interface TripListService {

		

		List<TripBetweenCityDTO> getTripBetweenCities(String source, String destination);

		
}
