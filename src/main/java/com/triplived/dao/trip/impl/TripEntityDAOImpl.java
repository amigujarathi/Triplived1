package com.triplived.dao.trip.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.UpVoteDownVoteStatus;
import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.trip.TripEntityDAO;
import com.triplived.entity.TripEntityDb;
import com.triplived.entity.TripEntityLikesDb;
import com.triplived.entity.TripEntityMediaDb;

@Repository
public class TripEntityDAOImpl extends GenericHibernateDAO<TripEntityDb, Serializable> implements TripEntityDAO {
	
	@Override
	public TripEntityDb getTripEntity(Long id){
		return (TripEntityDb)this.getCurrentSession().get(TripEntityDb.class, id);
	}
	
	@Override
	public void updateTripMedia(TripEntityMediaDb tripMediaDb){
		this.getCurrentSession().saveOrUpdate(tripMediaDb);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txManager")
	public List<TripEntityDb> getTripEntities(Long tripId) {
		List<TripEntityDb> trips = ((List<TripEntityDb>)getSession().createQuery("Select trip_entity FROM com.triplived.entity.TripEntityDb trip_entity "
				+ "where TRIP_ID = :tripId and status = :status").setLong("tripId", tripId).setString("status","A").list());
		
		return trips;
	}
	
	/**
	 * Return a active Trip Entity
	 * @param tripId
	 * @param entityId
	 * @return
	 */
	@Transactional(value="txManager", readOnly = true)
	public TripEntityDb getTripEntity(Long tripId, String entityId) {
		TripEntityDb tripEntity = (TripEntityDb) getSession().createQuery("Select trip_entity FROM com.triplived.entity.TripEntityDb trip_entity "
				+ "where TRIP_ID = :tripId and ENTITY_ID = :entityId and  status = 'A' ")
				.setLong("tripId", tripId).setString("entityId", entityId).uniqueResult();
		
		return tripEntity;
	}

	@Override
	@Transactional("txManager")
	public boolean likeUserTripEntity(TripEntityLikesDb tripEntityLikesDb) {
		
		TripEntityLikesDb tripLikes = (TripEntityLikesDb) getSession()
				.createQuery("SELECT tripEntityLikes FROM  com.triplived.entity.TripEntityLikesDb tripEntityLikes"
						+ " where TRIP_ENTITY_ID = :tripEntityID and TRIP_ID = :tripId and USER_ID = :userId  ")
				.setLong("tripEntityID", tripEntityLikesDb.getTripEntityId())
				.setLong("tripId", tripEntityLikesDb.getTripId())
				.setLong("userId", tripEntityLikesDb.getUserID())
				.uniqueResult();
		
		if(tripLikes == null){
			saveObject(tripEntityLikesDb);
		}else{
			tripLikes.setUpdateDate(new Date());
			tripLikes.setStatus(tripEntityLikesDb.getStatus());
			
			updateObject(tripLikes);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value="txManager", readOnly = true)
	@Override
	public List<PeopleLiked> getAllUsersWhoLiked(Long id) {
	
		/**
		 * 
		 *  select user.name, user.LAST_NAME, user.USER_FROM, likes.USER_ID
			from trip_media_likes likes, user user   
			where MEDIA_ID = '1448195756711-1061' and user.USER_ID = likes.USER_ID and user.STATUS = 'A' ;
		 */
		
		String query = "Select user.name, user.lastName,  user.userFrom , user.lastName,  user.id, user.personId "
				+ " FROM PersonDb user, TripEntityLikesDb likes where user.personId = likes.userID  and user.status = 'A' and "
				+ " likes.tripEntityId = ? order by likes.createdDate " ;
		 
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

	@Override
	public Status getUserStatusOnEntity(Long userId, Long tripId, Long entityId){
		
		String query = "Select likes.status "
				+ " FROM PersonDb user, TripEntityLikesDb likes "
				+ " WHERE user.personId = likes.userID  and user.status = 'A' and "
				+ " likes.tripEntityId = ? and likes.userID = ? and likes.tripId = ?  " ;
		 
	    @SuppressWarnings("unchecked")
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  entityId, userId, tripId);
	  
	    if(CollectionUtils.isNotEmpty(results)){
			   for (Object object : results) {
				   Status status  = (Status ) object  ;
				   return status;
			   }
	    }
		return Status.UNKNOWN;
	}
	
	@Override
	public UpVoteDownVoteStatus getOverallUpvoteDownvoteStatus(Long tripId, Long entityId){
		UpVoteDownVoteStatus  upvoteDownvoteStatus = new UpVoteDownVoteStatus();
		
		String query = " select likes.status, count(*)  "
				+ " from TripEntityLikesDb likes "
				+ " where likes.tripEntityId = ? and likes.tripId = ?  "
				+ " group by TRIP_ENTITY_ID, STATUS " ;
		 
	    @SuppressWarnings("unchecked")
		List<Object> results = this.getHibernateTemplate().find(query.toString(),  entityId, tripId);
	  
	    if(CollectionUtils.isNotEmpty(results)){
			   for (Object object : results) {
				   Object[] result = ((Object[]) object ) ;
				   if(result != null) {
					   
					   Status stat = (Status) result[0];
					   long votesCount = (Long) result[1];
					   if(stat == Status.UPVOTE){
						   upvoteDownvoteStatus.setTotalUpvotes(votesCount);
					   }else if(stat == Status.DOWNVOTE){
						   upvoteDownvoteStatus.setTotalDownvotes(votesCount);
					   }else{
						   //No op
					   }
				   }
			   }
	    }
	    
	    return upvoteDownvoteStatus;
	}

}
