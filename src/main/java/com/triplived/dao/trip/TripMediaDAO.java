package com.triplived.dao.trip;

import java.io.Serializable;
import java.util.List;

import com.connectme.domain.triplived.dto.NotificationBarDTO;
import com.domain.triplived.trip.dto.LikeStatus;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.TripEntityMediaDb;
import com.triplived.entity.TripEntityMediaLikesDb;
import com.triplived.entity.TripMediaCommentsDb;

/**
 * Responsibility: All operation related to Trip Media
 * 
 * @author santosh 
 *
 */
public interface TripMediaDAO extends GenericDAO<TripEntityMediaDb, Serializable> {

	
	/**
	 * Returns the Trip Media associated with a trip and an entity
	 * 
	 * @param mediaInfo
	 * @return
	 */
	
	public TripEntityMediaDb getTripMedia(TripLikeInfo mediaInfo); 
	
	/**
	 * Likes a user trip Media(photo/Video)
	 * 
	 * @param info
	 */
	public boolean likeUserTripMedia(TripEntityMediaLikesDb tripEntityMediaLikesDb);

	/**
	 * Fetches list of people who like a media(photo/video)
	 * @param id
	 */
	public List<PeopleLiked>  getAllUsersWhoLiked(Long tripId, String id);

	/**
	 * @param id
	 * @return
	 */
	public TripEntityMediaDb getTripMediaEntity(String id);

	/**
	 * get Number of users who liked this media
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public LikeStatus getOverallMediaStatus(Long tripId, String mediaId);

	/**
	 * Checks whether user has liked this media or not
	 * @param userId
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public Status getUserStatusOnMedia(Long userId, Long tripId, String mediaId);

	/**
	 * 
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public List<Object[]> getCommentsOnTripMedia(Long tripId, String mediaId);
	
	/**
	 * 
	 * @param obj
	 */
	public void updateTripComment(TripMediaCommentsDb obj);

	/**
	 * 
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public Long getCommentsCountonMedia(Long tripId, String mediaId);

	/**
	 * 
	 * @param userId
	 * @param tripId
	 * @param commentId
	 * @return
	 */
	public Status updateCommentStatus(Long userId, Long tripId, Long commentId, String status);

	List<NotificationBarDTO> getAllUsersDetailsWhoLikedMediaOfTripsOfParticularUser(
			Long id);

	List<NotificationBarDTO> getAllUsersDetailsWhoCommentedOnMediaOfTripsOfParticularUser(
			Long id);

}