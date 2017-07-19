package com.triplived.dao.trip.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.NotificationBarDTO;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.DeviceDb;
import com.triplived.entity.ExploreTagsDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripCityDb;
import com.triplived.entity.TripCommentsDb;
import com.triplived.entity.TripCommentsLikesDb;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripEntityMediaDb;
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

@Repository
public class TripDAOImpl extends GenericHibernateDAO<TripEntityMediaDb, Serializable> implements TripDAO {
	

	@Override
	public void updateTrip(TripDb trip){
		this.getCurrentSession().saveOrUpdate(trip);
	}

	@Override
	public TripDb getTrip(Long id){
		return (TripDb)this.getCurrentSession().get(TripDb.class, id);
	}
	
	@Override
	public TripUserDb getTripIdByEmail(Long email) {
		Boolean value = false;
		return (TripUserDb)this.getCurrentSession().createQuery("Select user_trip from com.triplived.entity.TripUserDb user_trip where userId = :userId and complete = :complete").setLong("userId", email).setBoolean("complete", value).uniqueResult();
	}
	
	@Override
	public TripUserDb getUserIdByTripId(Long tripId) {
		return (TripUserDb)this.getCurrentSession().createQuery("Select user_trip from com.triplived.entity.TripUserDb user_trip where tripId = :tripId").setLong("tripId", tripId).uniqueResult();
	}
	
	
	@Override
	public void updateUserTrip(TripUserDb tripUserDb){
		this.getCurrentSession().saveOrUpdate(tripUserDb);
	}
	
	@Override
	public void updateTripStatus(TripStatusDb tripStatusDb){
		this.getCurrentSession().saveOrUpdate(tripStatusDb);
	}
	
	@Override
	public TripStatusDb getTripStatusObj(Long id){
		return (TripStatusDb)this.getCurrentSession().createQuery("Select trip_status from com.triplived.entity.TripStatusDb trip_status where id = :tripId").setLong("tripId", id).uniqueResult();
	}
	
	@Override
	public void updateTripHistory(TripHistoryDb tripHistory){
		this.getCurrentSession().saveOrUpdate(tripHistory);
	}
	
	@Override
	public void updateTripCityWise(TripCityDb tripCityDb){
		this.getCurrentSession().saveOrUpdate(tripCityDb);
	}
	
	@Override
	@Transactional("txManager")
	public List<TripCityDb> getTripCities(Long tripId) {
		List<TripCityDb> trips = (List<TripCityDb>)this.getCurrentSession().createQuery("Select trip_city FROM com.triplived.entity.TripCityDb trip_city "
				+ "where TRIP_ID = :tripId and status = :status").setLong("tripId", tripId).setString("status","A").list();
		
		return trips;
	}
	
	@Override
	@Transactional("txManager")
	public List<TripDb> getTrendingTrips(){
		List<TripDb> trips = (List<TripDb>)this.getCurrentSession().createQuery("Select trip FROM com.triplived.entity.TripDb trip where TRIP_TYPE IS NULL Order by ID desc limit 4").list();
		return trips;
	}
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getTrendingTimelines(String timelineStr){
		
		
		
		String queryStr = "Select t.TRIP_NAME as tripName, t.TRIP_COVER_URI as tripCoverUrl, "
				+ "t.TRIP_DATA as tripData, t.UPDATED_DATE as updatedDate, u.name as userName, t.ID as tripId, u.USER_ID as userId, u.ID "
				+ "as userFbId, t.TRIP_CATEGORY as tripCategories, t.PUBLIC_ID as tripPublicId from trip t , user u, user_trip ut where u.USER_ID = ut.USER_ID and t.ID = ut.TRIP_ID and "
				+ "t.TRIP_TYPE = 'TIMELINE' and t.ID IN ("+timelineStr+") ORDER BY t.UPDATED_DATE DESC";
				
		Query query = this.getCurrentSession().createSQLQuery(queryStr);		
		
		List<Object[]> list = query.list();
	    return list;
	}
	
	@Override
	@Transactional("txManager")
	public TripDb getTripById(Long tripId){
		TripDb trip = (TripDb)this.getCurrentSession().createQuery("Select trip FROM com.triplived.entity.TripDb trip where ID = :tripId").setLong("tripId", tripId).uniqueResult();
		return trip;
	}
	
	@Override
	@Transactional("txManager")
	public TripDb getTripByPublicId(Long publicTripId){
		TripDb trip = (TripDb)this.getCurrentSession().createQuery("Select trip FROM com.triplived.entity.TripDb trip where PUBLIC_ID = :tripId").setLong("tripId", publicTripId).uniqueResult();
		return trip;
	}
	
	
	@Override
	@Transactional("txManager")
	public TripLikesDb getLikeOfUser(Long tripId, Long userId){
		TripLikesDb tripLike = (TripLikesDb)this.getCurrentSession().createQuery("Select trip_likes FROM com.triplived.entity.TripLikesDb trip_likes where TRIP_ID = :tripId and USER_ID = :userId").setLong("tripId", tripId).setLong("userId", userId).uniqueResult();
		return tripLike;
	}
	
	
	@Override
	@Transactional("txManager")
	public Long getTripLikes(Long tripId){
		Long likes = (Long) this.getCurrentSession().createQuery("Select count(*) FROM com.triplived.entity.TripLikesDb trip_likes where STATUS = 'A' and TRIP_ID = :tripId").setLong("tripId", tripId).uniqueResult();
		return likes;
	}
	
	@Override
	@Transactional("txManager")
	public void updateTripLike(TripLikesDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional("txManager")
	public void updateTripComment(TripCommentsDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional("txManager")
	public void updateTripShare(TripShareDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getCommentsOnTrip(Long tripId){
		/*List<TripCommentsDb> trips = (List<TripCommentsDb>)this.getCurrentSession().
				createQuery("Select trip_comments FROM com.triplived.entity.TripCommentsDb trip_comments where TRIP_ID = :tripId Order by UPDATED_DATE desc").setLong("tripId",  tripId).list();
		*/
		
		String queryString = "Select tc.USER_ID as userId, tc.UPDATED_DATE as dateTime,tc.COMMENT as comment, "
				+ "u.NAME as userName,u.ID as userFbId, tc.TIMESTAMP as timestamp, tc.ID as commentId  FROM trip_comments tc, user u where tc.TRIP_ID = "+ tripId 
				+ " and tc.USER_ID = u.USER_ID and tc.STATUS='A' Order by tc.UPDATED_DATE desc";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		
		return list;
		
	}
	
	
	
	@Override
	@Transactional("txManager")
	public List<BigInteger> getAllTripLikes(String tripIds) {
		String queryString = "Select TRIP_ID from trip_likes where STATUS = 'A' and TRIP_ID IN ( " + tripIds + " )";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<BigInteger> list = query.list();
		return list;
	}
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getTripsByDestCity(String toCityId){
		String queryString = "Select tc.TRIP_ID as tripId, t.TRIP_NAME as tripName, t.TRIP_COVER_URI as tripUri, "
				+ "u.ID as fbId, u.USER_ID as userId, u.NAME as userName, t.TRIP_DATA as tripData, t.PUBLIC_ID as tripPublicId from trip_city tc , trip t, user_trip tu, user u where "
				+ "tc.STATUS = 'A' and t.TRIP_TYPE = 'TIMELINE' and t.ID = tc.TRIP_ID and tu.TRIP_ID = t.ID and tu.USER_ID = u.USER_ID and tc.TO_CITY_ID = '" 
				+ toCityId + "'";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		
		return list;
	}
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getTripsOfUser(Long personId){
		String queryString = "Select t.ID as tripId, t.TRIP_NAME as tripName, t.TRIP_COVER_URI as tripUri, "
				+ "t.TRIP_DATA as tripData, t.PUBLIC_ID as tripPublicId, t.TRIP_STATE as tripState, t.TRIP_TYPE as tripType, t.TRIP_SOURCE as tripSource from trip t, user_trip tu where t.TRIP_TYPE IN ('TIMELINE','TIMELINE_BK') and "
				+ "tu.TRIP_ID = t.ID and tu.USER_ID = " 
				+ personId;
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		
		return list;
	}
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getTripsOfUserForWeb(Long personId){
		String queryString = "Select t.ID as tripId, t.TRIP_NAME as tripName, t.TRIP_COVER_URI as tripUri, "
				+ "t.TRIP_DATA as tripData, t.PUBLIC_ID as tripPublicId, t.TRIP_STATE as tripState, t.TRIP_TYPE as tripType, t.TRIP_SOURCE as tripSource from trip t, user_trip tu where t.TRIP_TYPE IN ('TIMELINE','TIMELINE_WEB') and "
				+ "tu.TRIP_ID = t.ID and tu.USER_ID = " 
				+ personId;
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		
		return list;
	}
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getLikedTripsOfUser(Long personId){
		String queryString = "Select t.ID as tripId, t.TRIP_NAME as tripName, t.TRIP_COVER_URI as tripUri, "
				+ "t.TRIP_DATA as tripData, u.ID as fbId, u.USER_ID as userId, u.NAME as userName, t.TRIP_CATEGORY as tripCategories, t.PUBLIC_ID as tripPublicId from trip t, user u, user_trip ut, "
				+ "trip_likes tl where t.TRIP_TYPE = 'TIMELINE' and "
				+ "tl.TRIP_ID = t.ID and t.ID = ut.TRIP_ID and u.USER_ID = ut.USER_ID and tl.USER_ID = " 
				+ personId
				+ " ORDER BY tl.UPDATED_DATE DESC";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		
		return list;
	}
	
	
	@Override
	@Transactional("txManager")
	public void updateTripAttractionDetails(TripAttractionDetailsDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	public TripAttractionDetailsDb getTripAttractionEntityBySid(Long sid){
		TripAttractionDetailsDb obj = (TripAttractionDetailsDb)this.getCurrentSession()
		.createQuery(
				"Select trip_attraction_details FROM com.triplived.entity.TripAttractionDetailsDb trip_attraction_details where TRIP_ENTITY_ID =:sid").setLong("sid",sid).uniqueResult();
	    return obj;
	}
	
	@Override
	@Transactional("txManager")
	public void updateTripHotelDetails(TripHotelDetailsDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	public TripHotelDetailsDb getTripHotelEntityBySid(Long sid){
		TripHotelDetailsDb obj = (TripHotelDetailsDb)this.getCurrentSession()
		.createQuery(
				"Select trip_hotel_details FROM com.triplived.entity.TripHotelDetailsDb trip_hotel_details where TRIP_ENTITY_ID =:sid").setLong("sid",sid).uniqueResult();
	    return obj;
	}
	
	@Override
	@Transactional("txManager")
	public void updateTripExperience(TripExperiencesDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	public TripExperiencesDb getTripExperienceBySid(Long sid){
		TripExperiencesDb obj = (TripExperiencesDb)this.getCurrentSession()
		.createQuery(
				"Select trip_experiences FROM com.triplived.entity.TripExperiencesDb trip_experiences where TRIP_ENTITY_ID =:sid").setLong("sid",sid).uniqueResult();
	    return obj;
	}
	
	
	
	@Override
	@Transactional("txManager")
	public List<ExploreTagsDb> getExploreTags() {
		List<ExploreTagsDb> tags = (List<ExploreTagsDb>)this.getCurrentSession().createQuery("Select explore_tags FROM "
				+ "com.triplived.entity.ExploreTagsDb explore_tags "
				+ "where STATUS = 'A'").list();
		
		return tags;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<PeopleLiked> getAllUsersWhoLikedTrip(Long id) {
		 
		String query = "Select  user.name, user.lastName, user.userFrom, user.name, user.id, user.personId  "
				+ " FROM PersonDb user, TripLikesDb likes where user.personId = likes.userId and likes.status = 'A' and user.status = 'A' and "
				+ " likes.tripId = ? order by likes.updateDate" ;
		 
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  id);
		   
		   if(CollectionUtils.isNotEmpty(results)){
			   List<PeopleLiked> allPeople = new ArrayList<PeopleLiked>(results.size());
			   for (Object object : results) {
				   Object[] peopleObject = ((Object[]) object ) ;
				   if(peopleObject != null){
					   PeopleLiked peopleLiked = new PeopleLiked();
					   peopleLiked.setFirstName((String) peopleObject[0]);
					   peopleLiked.setLastName((String) peopleObject[1]);
					   peopleLiked.setUserFrom((UserFrom) peopleObject[2]);
					   peopleLiked.setProfilePhoto((String) peopleObject[3]);
					   peopleLiked.setId((Long)peopleObject[4]);
					   peopleLiked.setPersonId((Long)peopleObject[5]);
					   
					   allPeople.add(peopleLiked);
				   }
			   }
			   
			   return allPeople ;
		   }
			
	  return Collections.EMPTY_LIST;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<NotificationBarDTO> getAllUsersDetailsWhoLikedTripsByTripOwnerId(Long id) {
		 
		String query = "Select  user.name, user.lastName, user.personId, likes.tripId, trp.tripName, trp.tripPublicId, trp.tripCoverUri, likes.timeStamp  "
				+ " FROM PersonDb user, TripLikesDb likes, TripDb trp where user.personId = likes.userId and likes.tripId = trp.id and likes.status = 'A' and user.status = 'A' and "
				+ " likes.tripUserId = ? order by likes.updateDate DESC" ;
		 
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  id);
		   
		   if(CollectionUtils.isNotEmpty(results)){
			   List<NotificationBarDTO> allPeople = new ArrayList<NotificationBarDTO>(results.size());
			   for (Object object : results) {
				   Object[] peopleObject = ((Object[]) object ) ;
				   if(peopleObject != null){
					   NotificationBarDTO peopleLiked = new NotificationBarDTO();
					   peopleLiked.setUserName((String) peopleObject[0]);
					   peopleLiked.setUserId( ((Long)peopleObject[2]).toString());
					   peopleLiked.setTripId(((Long)peopleObject[3]).toString());
					   peopleLiked.setTripIdNew(((Long)peopleObject[5]).toString());
					   peopleLiked.setTripName(((String)peopleObject[4]).toString());
					   if(null != peopleObject[6]) {
						   peopleLiked.setTripCoverUri(((String)peopleObject[6]).toString());
					   }
					   if(null != peopleObject[7]) {
						   peopleLiked.setTimeStamp(Long.parseLong(peopleObject[7].toString()));
					   }
					   peopleLiked.setType("TRIP_LIKE");
					   
					   allPeople.add(peopleLiked);
				   }
			   }
			   
			   return allPeople ;
		   }
			
	  return Collections.EMPTY_LIST;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<NotificationBarDTO> getAllUsersDetailsWhoCommentedOnTripsByTripOwnerId(Long id) {
		 
		String query = "Select  user.name, user.lastName, user.personId, likes.tripId, trp.tripName, trp.tripPublicId, trp.tripCoverUri, likes.timeStamp "
				+ " FROM PersonDb user, TripCommentsDb likes, TripDb trp where user.personId = likes.userId and likes.tripId = trp.id and likes.status = 'A' and user.status = 'A' and "
				+ " likes.tripUserId = ? order by likes.updateDate DESC" ;
		 
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  id);
		   
		   if(CollectionUtils.isNotEmpty(results)){
			   List<NotificationBarDTO> allPeople = new ArrayList<NotificationBarDTO>(results.size());
			   for (Object object : results) {
				   Object[] peopleObject = ((Object[]) object ) ;
				   if(peopleObject != null){
					   NotificationBarDTO peopleLiked = new NotificationBarDTO();
					   peopleLiked.setUserName((String) peopleObject[0]);
					   peopleLiked.setUserId( ((Long)peopleObject[2]).toString());
					   peopleLiked.setTripIdNew(((Long)peopleObject[5]).toString());
					   peopleLiked.setTripId(((Long)peopleObject[3]).toString());
					   peopleLiked.setTripName(((String)peopleObject[4]).toString());
					   if(null != peopleObject[6]) {
						   peopleLiked.setTripCoverUri(((String)peopleObject[6]).toString());
					   }
					   if(null != peopleObject[7]) {
						   peopleLiked.setTimeStamp(Long.parseLong(peopleObject[7].toString()));
					   }
					   peopleLiked.setType("TRIP_COMMENT");
					   
					   allPeople.add(peopleLiked);
				   }
			   }
			   
			   return allPeople ;
		   }
			
	  return Collections.EMPTY_LIST;
	}
	
	
	@Override
	@Transactional(value="txManager" , readOnly = true )
	public Long getTripCommentsCount(Long tripId){
		Long likes = (Long) this.getCurrentSession().createQuery(
				" Select count(*) "
				+ " FROM com.triplived.entity.TripCommentsDb trip_comments"
				+ " WHERE STATUS = 'A' and TRIP_ID = :tripId").setLong("tripId", tripId).uniqueResult();
		return likes;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<PeopleLiked> getAllUsersWhoLikedTripComments(Long id) {
		 
		String query = "Select  user.name, user.lastName, user.userFrom, user.name, user.id, user.personId  "
				+ " FROM PersonDb user, TripCommentsLikesDb likes where user.personId = likes.userID and likes.status = 'A' and user.status = 'A' and "
				+ " likes.commentId = ? order by likes.createdDate" ;
		 
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  id);
		   
		   if(CollectionUtils.isNotEmpty(results)){
			   List<PeopleLiked> allPeople = new ArrayList<PeopleLiked>(results.size());
			   for (Object object : results) {
				   Object[] peopleObject = ((Object[]) object ) ;
				   if(peopleObject != null){
					   PeopleLiked peopleLiked = new PeopleLiked();
					   peopleLiked.setFirstName((String) peopleObject[0]);
					   peopleLiked.setLastName((String) peopleObject[1]);
					   peopleLiked.setUserFrom((UserFrom) peopleObject[2]);
					   peopleLiked.setProfilePhoto((String) peopleObject[3]);
					   peopleLiked.setId((Long)peopleObject[4]);
					   peopleLiked.setPersonId((Long)peopleObject[5]);
					   
					   allPeople.add(peopleLiked);
				   }
			   }
			   
			   return allPeople ;
		   }
			
	  return Collections.EMPTY_LIST;
	}

	/**
	 * Trip Comment likes DB
	 */
	@Override
	public boolean likeUserTripComments(TripCommentsLikesDb tripCommentsLikesDb) {
		TripCommentsLikesDb tripCommentsLikes = (TripCommentsLikesDb) getSession()
				.createQuery("SELECT tripCommentsLikes FROM  com.triplived.entity.TripCommentsLikesDb tripCommentsLikes"
						+ " where COMMENT_ID = :commentId and TRIP_ID = :tripId and USER_ID = :userId  ")
				.setLong("commentId", tripCommentsLikesDb.getCommentId())
				.setLong("tripId", tripCommentsLikesDb.getTripId())
				.setLong("userId", tripCommentsLikesDb.getUserID())
				.uniqueResult();
		
		if(tripCommentsLikes == null){
			saveObject(tripCommentsLikesDb);
		}else{
			tripCommentsLikes.setUpdateDate(new Date());
			tripCommentsLikes.setStatus(tripCommentsLikesDb.getStatus());
			
			updateObject(tripCommentsLikes);
		}
		return true;
	}
	
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getAllTripsAndCommentsCount(){
		
		String queryString = "Select TRIP_ID as tripId, count(*) as comments from trip_comments where STATUS = 'A'"
				+ " GROUP BY TRIP_ID ORDER BY comments DESC";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		
		return list;
		
	}
	
	
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getAllTripsAndLikes() {
		String queryString = "Select TRIP_ID, count(*) as likes from trip_likes where STATUS = 'A'"
				+ " GROUP BY TRIP_ID ORDER BY likes DESC";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		List<Object[]> list = query.list();
		return list;
	}
	
	@Override
	@Transactional("txManager")
	public void updateTrendingTrips(TripTrendingDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional("txManager")
	public void updateTrendingExceptionTrips(TripTrendingExceptionDb obj){
		this.getCurrentSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional("txManager")
	public TripTrendingDb getTrendingTripObj(Long id){
		TripTrendingDb trip = (TripTrendingDb)this.getCurrentSession().createQuery("Select trip_trending FROM com.triplived.entity.TripTrendingDb trip_trending where TRIP_ID = :tripId").setLong("tripId", id).uniqueResult();
		return trip;		
	}
	
	@Override
	@Transactional("txManager")
	public TripTrendingExceptionDb getTrendingTripExceptionObj(Long id){
		TripTrendingExceptionDb trip = (TripTrendingExceptionDb)this.getCurrentSession().createQuery("Select trip_trending_exception FROM com.triplived.entity.TripTrendingExceptionDb trip_trending_exception where TRIP_ID = :tripId").setLong("tripId", id).uniqueResult();
		return trip;		
	}
	
	@Override
	@Transactional("txManager")
	public void deleteTrendingTrips(){
		Query q = this.getCurrentSession().createQuery("delete from com.triplived.entity.TripTrendingDb trip_trending");
		q.executeUpdate();
	}
	
	@Override
	@Transactional("txManager")
	public List<TripTrendingExceptionDb> getAllTrendingExceptionTrips(){
		List<TripTrendingExceptionDb> trips = (List<TripTrendingExceptionDb>)this.getCurrentSession().createQuery("Select trip_trending_exception FROM com.triplived.entity.TripTrendingExceptionDb	 trip_trending_exception where STATUS = 'A'").list();
		return trips;
	}
	
	@Override
	@Transactional("txManager")
	public List<TripDb> getAllValidTrips(){
		List<TripDb> trips = (List<TripDb>)this.getCurrentSession().createQuery("Select trip FROM com.triplived.entity.TripDb trip where TRIP_TYPE = 'TIMELINE' ").list();
		return trips;
	}
	
	@Override
	@Transactional("txManager")
	public List<TripDb> getAllValidTrips(Long min , Long max){
		List<TripDb> trips = (List<TripDb>)this.getCurrentSession().createQuery("Select trip FROM com.triplived.entity.TripDb  "
				+ " trip where TRIP_TYPE = 'TIMELINE' and ID between " +min+" and "+ max).list();
		return trips;
	}
	/*@Override
	@Transactional("txManager")
	public List<Object> getAllTripsFromUser(Long userId) {
		return  (List<Object>) this.getCurrentSession().createQuery("Select user_trip from com.triplived.entity.TripUserDb "
				+ " user_trip where userId = :userId  ").setLong("userId", userId).list();
	}
	*/
	
	 
	@Override
	@Transactional("txManager")
	public List<Object> getAllTripsFromUser(Long userId) {
		Query query = getSession().createSQLQuery("select ut.TRIP_ID   "
				+ " from user_trip ut, trip t where ut.USER_ID = :userId and t.TRIP_TYPE = 'TIMELINE' and t.ID = ut.TRIP_ID  ").setLong("userId", userId);
		
				
		@SuppressWarnings("unchecked")
		List<Object> obj = query.list();
		 
		
		return obj;
	}
	
	/**
	 * Will user userID and tripID in future 
	 * 
	 * @param userId
	 * @param tripId
	 * @param commentId
	 * @return
	 */

	@Override
	@Transactional("txManager")
	public Status updateCommentStatus(Long userId, Long tripId, Long commentId, String status) {
		
		TripCommentsDb commentsDb = this.getHibernateTemplate().load(TripCommentsDb.class, commentId);
	    commentsDb.setStatus(status);
	    commentsDb.setUpdateDate(new Date());
	    this.getHibernateTemplate().update(commentsDb);
	     
		return Status.DELETED;
	}
	
	
	@Override
	@Transactional("txManager")
	public List<TripFaqDb> getTripFaqList(Long tripId) {
		List<TripFaqDb> list = (List<TripFaqDb>)this.getCurrentSession().createQuery("Select trip_faq FROM "
				+ "com.triplived.entity.TripFaqDb trip_faq "
				+ "where TRIP_ID = :tripId").setLong("tripId", tripId).list();
		
		return list;
	}
	
	@Override
	@Transactional("txManager")
	public List<TripKitListDb> getTripKitList(Long tripId) {
		List<TripKitListDb> list = (List<TripKitListDb>)this.getCurrentSession().createQuery("Select trip_kit_list FROM "
				+ "com.triplived.entity.TripKitListDb trip_kit_list "
				+ "where TRIP_ID = :tripId").setLong("tripId", tripId).list();
		
		return list;
	}
	
	
	
	
}
