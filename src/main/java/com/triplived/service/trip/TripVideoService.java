package com.triplived.service.trip;


import java.util.List;

import com.connectme.domain.triplived.dto.TripVideoDTO;
import com.connectme.domain.triplived.dto.TripVideoPathDTO;


public interface TripVideoService {

		

	List<TripVideoDTO> getTripVideoStatus(Long tripID);
	
	TripVideoPathDTO getTripVideoPath(Long tripID);
	
	void updateTripVideoPath(String serverPath, String youTubePath,String status,String tripId);
	
	void updateTripVideoStatus(Long tripId,String TripPub, String VidGen, String VidReq,String YouUp,String YouLive,
			String mailSent, String NotReq,String NotSent);

		
}
