package com.triplived.service.trip.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.triplived.trip.dto.TimelineTrip;
import com.google.gson.Gson;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.TripDb;
import com.triplived.service.trip.TripJsonEditService;


@Service
public class TripJsonEditServiceImpl implements TripJsonEditService {

	private static final Logger logger = LoggerFactory.getLogger(TripJsonEditServiceImpl.class);
	
	@Autowired
	private TripDAO tripDAO;
	
	
	@Override
	@Transactional
	public TripDb displayTripJson(Long tripId){
		try {
			TripDb tripJson = tripDAO.getTripById(tripId);
			logger.warn("Trip Json data displayed in editor for trip ID : ",tripId);
			return tripJson;
			
		} catch (Exception e) {
			logger.error("Error occurred while displaying data in editor for trip ID : ",tripId);
			e.printStackTrace();
		}
		 
		return null;
		
	}
	
	@Override
	@Transactional
	public void submitTripJson(TimelineTrip tripJson, Long tripId){
		try {
			Gson gson = new Gson();
			TripDb oldTrip = tripDAO.getTripById(tripId);
			oldTrip.setTripDataEdited(gson.toJson(tripJson));
			logger.warn("Edited Trip Json data updated for trip ID : ",tripId);
			tripDAO.updateTrip(oldTrip);
			
		} catch (Exception e) {
			logger.error("Error occurred while updateing data in database for trip ID : ",tripId);
			e.printStackTrace();
		}
	}
	
	
}
