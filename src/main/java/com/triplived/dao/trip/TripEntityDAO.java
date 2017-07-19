package com.triplived.dao.trip;

import java.io.Serializable;
import java.util.List;

import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.UpVoteDownVoteStatus;
import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.TripEntityDb;
import com.triplived.entity.TripEntityLikesDb;
import com.triplived.entity.TripEntityMediaDb;


public interface TripEntityDAO extends GenericDAO<TripEntityDb, Serializable> {

	List<TripEntityDb> getTripEntities(Long tripId);

	/**
	 * 
	 * @param tripId
	 * @param entityId
	 * @return
	 */
	public	TripEntityDb getTripEntity(Long tripId, String entityId);
	
	/**
	 * Likes a trip's Entity
	 * 
	 * @param tripEntityMediaLikesDb
	 * @return
	 */
	public boolean likeUserTripEntity(TripEntityLikesDb tripEntityLikesDb);

	/**
	 * get List of People who liked an Entity
	 * @param id
	 * @return
	 */
	public List<PeopleLiked> getAllUsersWhoLiked(Long id);

	TripEntityDb getTripEntity(Long id);

	void updateTripMedia(TripEntityMediaDb tripMediaDb);
	
	/**
	 * Checks whether user has upvoted or downvoted on entity or not
	 * @param userId
	 * @param tripId
	 * @param entityId
	 * @return
	 */
	public Status getUserStatusOnEntity(Long userId, Long tripId, Long entityId);

	/**
	 * getOVerall Upvote Downvote status of trip entity
	 * @param tripId
	 * @param entityId
	 * @return
	 */
	public UpVoteDownVoteStatus getOverallUpvoteDownvoteStatus(Long tripId, Long entityId);
	

}