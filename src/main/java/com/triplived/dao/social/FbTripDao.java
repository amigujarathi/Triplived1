package com.triplived.dao.social;

import com.triplived.entity.FbTripDb;

public interface FbTripDao {

	void updateFbTrip(FbTripDb fbTrip);

	FbTripDb checkIfFbDetailsExist(Long tripId);

}
