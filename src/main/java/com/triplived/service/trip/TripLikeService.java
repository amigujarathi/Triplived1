package com.triplived.service.trip;

import java.util.List;

import com.domain.triplived.trip.dto.LikeStatus;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.TripElement;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.domain.triplived.trip.dto.TripResponse;
import com.domain.triplived.trip.dto.UpVoteDownVoteStatus;


/**
 * Like Handling of trips Media and Entities
 * 
 * @author Santosh 
 *
 */
public interface TripLikeService {

	/**
	 * Adds likes of a user on  trip media.
	 * 
	 * @return TripResponse
	 */
	
	public LikeStatus likeTripMedia(TripLikeInfo tripMediaInfo) throws Exception;
	
	
	/**
	 * Adds likes of a user on  trip entity.
	 * 
	 * @return TripResponse
	 */
	
	public TripResponse likeTripEntity(TripLikeInfo tripEntityInfo) throws Exception;
	
	/**
	 * Adds likes of a user on  trip comment.
	 * 
	 * @return TripResponse
	 */
	
	public TripResponse likeTripComment(TripLikeInfo tripEntityInfo) throws Exception;
	
	
	/**
	 * Returns list of all People who liked a Media/Entity/Trip
	 * 
	 */
	
	public List<PeopleLiked> peopleWhoLiked(Long tripId, String id, TripElement element) throws Exception;
	
	
	/**
	 * Checks whether user has upvoted or downvoted on entity or not also find overall status on entity 
	 * i.e total number of up and downvotes
	 * 
	 * @param userId
	 * @param tripId
	 * @param entityId
	 * @return
	 */
	public UpVoteDownVoteStatus getUserStatusOnEntity(Long userId, Long tripId, Long entityId);


	/**
	 * 
	 * @param userId
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public LikeStatus getUserStatusOnMediaLike(Long userId, Long tripId, String mediaId);


	UpVoteDownVoteStatus getOnlyStatusOnEntity(Long tripId,	Long entityId);
}
