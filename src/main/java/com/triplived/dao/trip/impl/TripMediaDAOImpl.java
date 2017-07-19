package com.triplived.dao.trip.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.NotificationBarDTO;
import com.domain.triplived.trip.dto.LikeStatus;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.trip.TripMediaDAO;
import com.triplived.entity.TripEntityMediaDb;
import com.triplived.entity.TripEntityMediaLikesDb;
import com.triplived.entity.TripMediaCommentsDb;

@Repository
public class TripMediaDAOImpl extends GenericHibernateDAO<TripEntityMediaDb, Serializable> implements TripMediaDAO {
	
	@Override
	@Transactional("txManager")
	public TripEntityMediaDb getTripMediaEntity(String id){
		return (TripEntityMediaDb)this.getCurrentSession().get(TripEntityMediaDb.class, id);
	}
	
	@Override
	@Transactional("txManager")
	public boolean likeUserTripMedia(TripEntityMediaLikesDb tripEntityMediaLike) {
		
		
		TripEntityMediaLikesDb tripEntityMediaLikes = (TripEntityMediaLikesDb) getSession()
				.createQuery("SELECT tripMediaLikes FROM  com.triplived.entity.TripEntityMediaLikesDb tripMediaLikes"
						+ " where MEDIA_ID = :mediaId and TRIP_ID = :tripId and USER_ID = :userId  ")
				.setString("mediaId", tripEntityMediaLike.getMediaId())
				.setLong("tripId", tripEntityMediaLike.getTripId())
				.setLong("userId", tripEntityMediaLike.getUserID())
				.uniqueResult();
		
		if(tripEntityMediaLikes == null){
			saveObject(tripEntityMediaLike);
		}else{
			tripEntityMediaLikes.setUpdateDate(new Date());
			tripEntityMediaLikes.setStatus(tripEntityMediaLike.getStatus());
			tripEntityMediaLikes.setTimeStamp(System.currentTimeMillis() / 1000L);
			
			updateObject(tripEntityMediaLikes);
		}
		return true;
	}

	
	@Override
	@Transactional(value="txManager", readOnly=true)
	public TripEntityMediaDb getTripMedia(TripLikeInfo mediaInfo) {
		
		TripEntityMediaDb tripMedia = (TripEntityMediaDb) getSession()
				.createQuery("SELECT tripMedia FROM  com.triplived.entity.TripEntityMediaDb tripMedia where MEDIA_ID = :mediaId and TRIP_ID = :tripId")
				.setString("mediaId", mediaInfo.getId())
				.setLong("tripId", mediaInfo.getTripId())
				.uniqueResult();

		return tripMedia;
	}


	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<PeopleLiked> getAllUsersWhoLiked(Long tripId, String id) {
		/**
		 * 
		 *  select user.name, user.LAST_NAME, user.USER_FROM, likes.USER_ID
			from trip_media_likes likes, user user   
			where MEDIA_ID = '1448195756711-1061' and user.USER_ID = likes.USER_ID  and likes.status = 'LIKE' and user.STATUS = 'A' ;
		 */
		
		String query = "Select  user.name, user.lastName, user.userFrom, user.name, user.id, user.personId  "
				+ " FROM PersonDb user, TripEntityMediaLikesDb likes where user.personId = likes.userID and likes.status = 'LIKE' and user.status = 'A' and "
				+ " likes.mediaId = ? and likes.tripId = ? order by likes.createdDate" ;
		 
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  id, tripId);
		   
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

	@Override
	public LikeStatus getOverallMediaStatus(Long tripId, String mediaId) {
		LikeStatus  likeStatus = new LikeStatus();
		
		String query = " select likes.status  "
				+ " from TripEntityMediaLikesDb likes "
				+ " where likes.mediaId = ? and likes.tripId = ?  and likes.status = 'LIKE' ";
		 
	    @SuppressWarnings("unchecked")
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  mediaId, tripId);
	  
	    if(CollectionUtils.isNotEmpty(results)){
	    	 likeStatus.setTotalLikes(results.size());
	    }
	    
	    return likeStatus;
	}

	@Override
	public Status getUserStatusOnMedia(Long userId, Long tripId, String mediaId) {
		
		String query = "Select likes.status "
				+ " FROM TripEntityMediaLikesDb likes "
				+ " WHERE likes.mediaId = ? and likes.userID = ? and likes.tripId = ?  " ;
		 
	    @SuppressWarnings("unchecked")
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  mediaId, userId, tripId);
	  
	    if(CollectionUtils.isNotEmpty(results)){
			   for (Object object : results) {
				   Status status  = (Status ) object  ;
				   return status;
			   }
	    }
		return Status.UNKNOWN;
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
		
	    TripMediaCommentsDb commentsDb = this.getHibernateTemplate().load(TripMediaCommentsDb.class, commentId);
	    commentsDb.setStatus(status);
	    commentsDb.setUpdateDate(new Date());
	    this.getHibernateTemplate().update(commentsDb);
	     
		return Status.DELETED;
	}
	
	
	@Override
	@Transactional("txManager")
	public List<Object[]> getCommentsOnTripMedia(Long tripId, String mediaId){
		 
		String queryString = "Select tc.USER_ID as userId, tc.UPDATED_DATE as dateTime,tc.COMMENT as comment, "
				+ "u.NAME as userName,u.ID as userFbId, tc.TIMESTAMP as timestamp , tc.ID as commentId FROM trip_media_comments tc, user u where tc.TRIP_ID = "+ tripId 
				+ " and tc.MEDIA_ID='"+ mediaId+"' and tc.USER_ID = u.USER_ID and tc.STATUS='A' Order by tc.UPDATED_DATE desc";
		Query query = this.getCurrentSession().createSQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		
		return list;
		
	}

	@Override
	@Transactional("txManager")
	public void updateTripComment(TripMediaCommentsDb obj) {
		saveObject(obj);
	}
	
	
	@Override
	@Transactional(value="txManager" , readOnly = true )
	public Long getCommentsCountonMedia(Long tripId, String mediaId){
		Long likes = (Long) this.getCurrentSession().createQuery(
				" Select count(*) "
				+ " FROM com.triplived.entity.TripMediaCommentsDb trip_comments"
				+ " WHERE STATUS = 'A' and TRIP_ID = :tripId and MEDIA_ID = :mediaId ")
				 .setLong("tripId", tripId)
				.setString("mediaId", mediaId).uniqueResult();
		return likes;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<NotificationBarDTO> getAllUsersDetailsWhoLikedMediaOfTripsOfParticularUser(Long id) {
		 
		String query = "Select  user.name, user.lastName, user.personId, likes.tripId, trp.tripName, trp.tripPublicId, trp.tripCoverUri, "
				+ "likes.mediaId, likes.timeStamp FROM PersonDb user, TripEntityMediaLikesDb likes, TripDb trp where user.personId = likes.userID and "
				+ "likes.tripId = trp.id and likes.status = 'LIKE' and likes.tripId IN (Select trp.id from PersonDb user, TripDb trp, "
				+ "TripUserDb tu where trp.id = tu.tripId and tu.userId = user.personId and user.personId = ?)";
		 
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
					   peopleLiked.setMediaId((String) peopleObject[7]);
					   if(null != peopleObject[8]) {
						   peopleLiked.setTimeStamp(Long.parseLong(peopleObject[8].toString()));
					   }
					   
					   peopleLiked.setType("TRIP_MEDIA_LIKE");
					   
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
	public List<NotificationBarDTO> getAllUsersDetailsWhoCommentedOnMediaOfTripsOfParticularUser(Long id) {
		 
		String query = "Select  user.name, user.lastName, user.personId, comments.tripId, trp.tripName, trp.tripPublicId, trp.tripCoverUri, "
				+ "comments.mediaId, comments.timeStamp FROM PersonDb user, TripMediaCommentsDb comments, TripDb trp where user.personId = comments.userId and "
				+ "comments.tripId = trp.id and comments.status = 'A' and comments.tripUserId = ?";
		 
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
					   peopleLiked.setMediaId((String) peopleObject[7]);
					   if(null != peopleObject[8]) {
						   peopleLiked.setTimeStamp(Long.parseLong(peopleObject[8].toString()));
					   }
					   
					   peopleLiked.setType("TRIP_MEDIA_COMMENT");
					   
					   allPeople.add(peopleLiked);
				   }
			   }
			   
			   return allPeople ;
		   }
			
	  return Collections.EMPTY_LIST;
	}

}
