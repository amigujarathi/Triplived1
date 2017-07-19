package com.triplived.dao.trip;

import java.util.List;

import com.triplived.entity.TripDb;
import com.triplived.entity.TripVideoDb;

public interface TripVideoDao {

	void updateTripVideo(TripVideoDb tripVideo);

	List<Object[]> getPendingTripsForVideoGeneration();

	List<TripVideoDb> getTripVideoDetails(Long tripId);
	
	List<TripVideoDb> getTripVideoStatus(Long tripID);

	TripDb getTripVideoPath(Long tripID);
	
	void updateTripVideoPath(TripDb tripDb);
	
	void updateTripVideoStatus(TripVideoDb tripVideoDb);
	
	void deleteTripVideoStatus(TripVideoDb tripVideoDb);
}
