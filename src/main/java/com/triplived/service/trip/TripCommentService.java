package com.triplived.service.trip;

import java.util.List;

import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripElement;


/**
 * Like Handling of trip media comments
 * 
 * @author Santosh 
 *
 */
public interface TripCommentService {

	/**
	 * 
	 * @param tripId
	 * @param userId
	 * @param mediaId
	 * @param comment
	 * @return
	 */
	public String addCommentsOnTripMedia(Long tripId, Long userId, String mediaId, String comment);

	/**
	 * 
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public List<TripCommentDTO> getCommentsOnTrip(Long tripId, String mediaId);
	
	/**
	 * Update comments status
	 * @param tripId
	 * @param userId
	 * @param mediaId
	 * @param comment
	 * @return
	 */
	public Status updateCommentStatus(Long tripId, Long userId, Long mediaId, String comment, TripElement tripElement);

	
}
