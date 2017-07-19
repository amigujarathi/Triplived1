package com.triplived.dao.trip;

import java.math.BigInteger;
import java.util.List;

import com.connectme.domain.triplived.dto.NotificationBarDTO;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.triplived.entity.ExploreTagsDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripCityDb;
import com.triplived.entity.TripCommentsDb;
import com.triplived.entity.TripCommentsLikesDb;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripExperiencesDb;
import com.triplived.entity.TripFaqDb;
import com.triplived.entity.TripHistoryDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.TripKitListDb;
import com.triplived.entity.TripLikesDb;
import com.triplived.entity.TripShareDb;
import com.triplived.entity.TripStatusDb;
import com.triplived.entity.TripTrendingDb;
import com.triplived.entity.TripTrendingExceptionDb;
import com.triplived.entity.TripUserDb;
import com.triplived.entity.WebExperiencesDb;

public interface TripDAO {

	void updateTrip(TripDb trip);

	TripDb getTrip(Long id);

	void updateUserTrip(TripUserDb tripUserDb);

	TripUserDb getTripIdByEmail(Long email);

	void updateTripCityWise(TripCityDb tripCityDb);

	List<TripDb> getTrendingTrips();

	

	TripUserDb getUserIdByTripId(Long tripId);

	

	TripDb getTripById(Long tripId);

	TripLikesDb getLikeOfUser(Long tripId, Long userId);

	Long getTripLikes(Long tripId);

	void updateTripLike(TripLikesDb obj);

	List<BigInteger> getAllTripLikes(String tripIds);

	List<Object[]> getTripsByDestCity(String toCityId);

	List<Object[]> getTripsOfUser(Long personId);

	List<Object[]> getTrendingTimelines(String timelineStr);

	void updateTripComment(TripCommentsDb obj);

	List<Object[]> getLikedTripsOfUser(Long personId);

	List<Object[]> getCommentsOnTrip(Long tripId);

	List<TripCityDb> getTripCities(Long tripId);

	void updateTripAttractionDetails(TripAttractionDetailsDb obj);

	List<ExploreTagsDb> getExploreTags();

	void updateTripHotelDetails(TripHotelDetailsDb obj);

	TripDb getTripByPublicId(Long publicTripId);

	List<Object[]> getAllTripsAndCommentsCount();

	List<Object[]> getAllTripsAndLikes();

	void updateTrendingTrips(TripTrendingDb obj);

	void deleteTrendingTrips();
	
	
	/**
	 * get count of trip comments 
	 */
	public Long getTripCommentsCount(Long tripId);
	
	/**
	 * get List of People who liked trip Comments
	 * @param id
	 * @return
	 */
	public List<PeopleLiked> getAllUsersWhoLikedTripComments(Long tripCommentsId);
	
	/**
	 * get List of People who liked an Entity
	 * @param id
	 * @return
	 */
	public List<PeopleLiked> getAllUsersWhoLikedTrip(Long tripId);
	
	
	/**
	 * Likes a user trip Comments
	 * 
	 * @param tripCommentsLikesDb
	 */
	public boolean likeUserTripComments(TripCommentsLikesDb tripCommentsLikesDb);

	void updateTripShare(TripShareDb obj);

	void updateTripHistory(TripHistoryDb tripHistory);

	TripAttractionDetailsDb getTripAttractionEntityBySid(Long sid);

	TripHotelDetailsDb getTripHotelEntityBySid(Long sid);

	List<TripDb> getAllValidTrips();

	void updateTripExperience(TripExperiencesDb obj);

	TripExperiencesDb getTripExperienceBySid(Long sid);
	
	/**
	 * 
	 * @param userId
	 * @param tripId
	 * @param commentId
	 * @return
	 */
	public Status updateCommentStatus(Long userId, Long tripId, Long commentId, String status);

	TripTrendingDb getTrendingTripObj(Long id);

	List<TripTrendingExceptionDb> getAllTrendingExceptionTrips();

	void updateTrendingExceptionTrips(TripTrendingExceptionDb obj);

	TripTrendingExceptionDb getTrendingTripExceptionObj(Long id);

	List<NotificationBarDTO> getAllUsersDetailsWhoLikedTripsByTripOwnerId(Long id);

	List<NotificationBarDTO> getAllUsersDetailsWhoCommentedOnTripsByTripOwnerId(Long id);

	List<Object> getAllTripsFromUser(Long userId);

	List<TripDb> getAllValidTrips(Long min, Long max);

	void updateTripStatus(TripStatusDb tripStatusDb);

	TripStatusDb getTripStatusObj(Long id);

	List<TripFaqDb> getTripFaqList(Long tripId);

	List<TripKitListDb> getTripKitList(Long tripId);

	List<Object[]> getTripsOfUserForWeb(Long personId);

}
