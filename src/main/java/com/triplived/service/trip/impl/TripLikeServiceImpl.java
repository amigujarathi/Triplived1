package com.triplived.service.trip.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.triplived.trip.dto.LikeStatus;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripElement;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.domain.triplived.trip.dto.TripLivedResponseCode;
import com.domain.triplived.trip.dto.TripResponse;
import com.domain.triplived.trip.dto.UpVoteDownVoteStatus;
import com.gcm.ClientMessageType;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripEntityDAO;
import com.triplived.dao.trip.TripMediaDAO;
import com.triplived.entity.TripCommentsLikesDb;
import com.triplived.entity.TripEntityLikesDb;
import com.triplived.entity.TripEntityMediaDb;
import com.triplived.entity.TripEntityMediaLikesDb;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.service.trip.TripLikeService;

/**
 * 
 * @author santosh
 *
 * for documentation @see TripLikeService
 */
@Service
public class TripLikeServiceImpl implements TripLikeService {

	private static final Logger logger = LoggerFactory.getLogger(TripLikeServiceImpl.class );
	
	@Autowired
	TripMediaDAO tripMediaDAO;
	
	@Autowired
	TripEntityDAO tripEntityDao;
	
	@Autowired
	private GCMNotificationService gcmService;

	@Autowired
	TripDAO tripDao;
	
	@Transactional(readOnly=false)
	@Override
	public TripResponse likeTripEntity(TripLikeInfo tripEntityInfo) throws Exception {
		
		TripResponse response = new TripResponse();

		try {
			Status status = Status.getStatus(tripEntityInfo.getUserAction()) ; 
			if(status == Status.UNKNOWN){
				response.setCode(TripLivedResponseCode.RESPONSE_TEMPERED);
				response.setMessage("Failed");
				return response;
			}
			TripEntityLikesDb tripEntityLikesDb = new TripEntityLikesDb();
			tripEntityLikesDb.setCreatedDate(new Date());
			tripEntityLikesDb.setTripEntityId(Long.parseLong(tripEntityInfo.getId()));
			tripEntityLikesDb.setStatus(status);
			tripEntityLikesDb.setTripId(tripEntityInfo.getTripId());
			tripEntityLikesDb.setUserID(tripEntityInfo.getUserId());
			
			if (tripEntityDao.likeUserTripEntity(tripEntityLikesDb)) {
				response.setCode(TripLivedResponseCode.RESPONSE_OK);
				response.setMessage("Success");
			} else {
				response.setCode(TripLivedResponseCode.RESPONSE_ERROR);
				response.setMessage("Failed");
			}
		} catch (Exception ex) {
			logger.error("Exception on liking user {} for  entity {}", tripEntityInfo.getUserId(), tripEntityInfo.getId());
			logger.error("Error", ex);
			response.setMessage(ex.getMessage());
			response.setCode(TripLivedResponseCode.RESPONSE_EXCEPTION);
		}

		return response;
	}
	
	/**
	 * @see TripLikeService#likeTripMedia(TripLikeInfo)
	 */
	@Transactional(readOnly=false)
	@Override
	public LikeStatus likeTripMedia(TripLikeInfo tripMediaInfo) throws Exception {

		LikeStatus response = new LikeStatus();

		try {
			
			TripEntityMediaDb tripEntityMediaDb = tripMediaDAO.getTripMedia(tripMediaInfo);
			
			/*if(tripEntityMediaDb == null ){
				
				 * a)  Media is null means the trip was old and media information was not persisted into database
				 * b)  if Null Store media into database
				 
				 tripEntityMediaDb = getTripMedia(tripMediaInfo);
			}*/
			if(tripEntityMediaDb == null){
				response.setTotalLikes(-1);
				response.setUserLikeStatus(Status.UNKNOWN.getStatus());
				logger.warn("media not present in database  {}", tripMediaInfo.getId()); 
				return response;
			}
			Status status = Status.getStatus(tripMediaInfo.getUserAction()) ; 
			if(status == Status.UNKNOWN){
				response.setTotalLikes(-1);
				response.setUserLikeStatus(Status.UNKNOWN.getStatus());
				return response;
			}
			TripEntityMediaLikesDb tripEntityMediaLikesDb = new TripEntityMediaLikesDb();
			tripEntityMediaLikesDb.setStatus(status);
			tripEntityMediaLikesDb.setCreatedDate(new Date());
			tripEntityMediaLikesDb.setMediaId(tripEntityMediaDb.getMediaId());
			tripEntityMediaLikesDb.setTripId(tripEntityMediaDb.getTripId());
			tripEntityMediaLikesDb.setUserID(tripMediaInfo.getUserId());
			tripEntityMediaLikesDb.setTimeStamp(System.currentTimeMillis() / 1000L);
			
			if (tripMediaDAO.likeUserTripMedia(tripEntityMediaLikesDb)) {
				
				LikeStatus stat = tripMediaDAO.getOverallMediaStatus(tripMediaInfo.getTripId(), tripMediaInfo.getId());
				response.setUserLikeStatus(Status.getStatus(tripMediaInfo.getUserAction()).getStatus());
				response.setTotalLikes(stat.getTotalLikes());
				
				Long totalComments = tripMediaDAO.getCommentsCountonMedia(tripMediaInfo.getTripId(), tripMediaInfo.getId());
				if(totalComments == null){
					response.setTotalComments(0);
				}else{
					response.setTotalComments(totalComments);
				}
				
			} else {
				response.setUserLikeStatus(Status.UNKNOWN.getStatus());
			}
			
			if(status == Status.LIKE){
				gcmService.sendTripMediaLikeNotification(tripMediaInfo.getTripId(), tripMediaInfo.getUserId(), 
						tripEntityMediaDb.getMediaId(), tripEntityMediaDb.getMediaPath(), ClientMessageType.PHOTO_LIKE);
			}
		} catch (Exception ex) {
			logger.error("Exception on liking user: {} for  media: {}", tripMediaInfo.getUserId(), tripMediaInfo.getId());
			logger.error("Error", ex);
			response.setTotalLikes(-1);
			response.setUserLikeStatus(Status.UNKNOWN.getStatus());
		}

		return response;
	}

	/**
	 * @see TripLikeService#likeTripComment(TripLikeInfo)
	 */
	@Transactional(readOnly=false)
	@Override
	public TripResponse likeTripComment(TripLikeInfo tripEntityInfo) throws Exception {
		
		TripResponse response = new TripResponse();

		try {

			TripCommentsLikesDb tripCommentsLikesDb = new TripCommentsLikesDb();
			tripCommentsLikesDb.setCreatedDate(new Date());
			tripCommentsLikesDb.setCommentId(Long.parseLong(tripEntityInfo.getId()));
			tripCommentsLikesDb.setStatus(tripEntityInfo.getUserAction());
			tripCommentsLikesDb.setTripId(tripEntityInfo.getTripId());
			tripCommentsLikesDb.setUserID(tripEntityInfo.getUserId());
			
			if (tripDao.likeUserTripComments(tripCommentsLikesDb)) {
				response.setCode(TripLivedResponseCode.RESPONSE_OK);
				response.setMessage("Success");
			} else {
				response.setCode(TripLivedResponseCode.RESPONSE_ERROR);
				response.setMessage("Failed");
			}
		} catch (Exception ex) {
			logger.error("Exception on liking user {} for  entity {}", tripEntityInfo.getUserId(), tripEntityInfo.getId());
			logger.error("Error", ex);
			response.setMessage(ex.getMessage());
			response.setCode(TripLivedResponseCode.RESPONSE_EXCEPTION);
		}

		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PeopleLiked> peopleWhoLiked(Long tripIdentifier, String id, TripElement element) throws Exception {
	
		try {
			
			switch (element) {
				case TRIP_PHOTO:
						return tripMediaDAO.getAllUsersWhoLiked(tripIdentifier, id);
						
				case TRIP_ENTITY:
						Long identifier = Long.parseLong(id);
						return tripEntityDao.getAllUsersWhoLiked(identifier);

				case TRIP:
						Long tripId = Long.parseLong(id);
						return tripDao.getAllUsersWhoLikedTrip(tripId);
					
				case TRIP_COMMENT:
						Long commentId = Long.parseLong(id);
						return tripDao.getAllUsersWhoLikedTripComments(commentId);
			default:
				break;
			}
			
			
		} catch (Exception ex) {
			logger.error("Exception fetching likes for Trip photo/media/entity : {} for  media Type : {} ", id, element.toString());
			logger.error("Error", ex);
			return Collections.EMPTY_LIST;
		}	
		
		return Collections.EMPTY_LIST;
	}

	@Override
	public UpVoteDownVoteStatus getUserStatusOnEntity(Long userId, Long tripId, Long entityId) {
		UpVoteDownVoteStatus overallStatus = tripEntityDao.getOverallUpvoteDownvoteStatus(tripId, entityId);
		Status status = tripEntityDao.getUserStatusOnEntity(userId, tripId, entityId);
		
		if(status != null){
			overallStatus.setUserVerdict(status.getStatus());
		}else{
			overallStatus.setUserVerdict(Status.UNKNOWN.getStatus());
		}
		
		return overallStatus ;
	}
	
	@Override
	public UpVoteDownVoteStatus getOnlyStatusOnEntity(Long tripId, Long entityId) {
		UpVoteDownVoteStatus overallStatus = tripEntityDao.getOverallUpvoteDownvoteStatus(tripId, entityId);
		
		
		return overallStatus ;
	}
	
	
	/**
	 * Checks whether user has Liked a photo or not also find overall status on entity 
	 * i.e total number of likes
	 * 
	 * @param userId
	 * @param tripId
	 * @param mediaId
	 * @return
	 */
	public LikeStatus getUserStatusOnMediaLike(Long userId, Long tripId, String mediaId){
		LikeStatus overallStatus = tripMediaDAO.getOverallMediaStatus(tripId, mediaId);
		Status status = tripMediaDAO.getUserStatusOnMedia(userId, tripId, mediaId);
		Long totalComments = tripMediaDAO.getCommentsCountonMedia(tripId, mediaId);
		if(totalComments == null){
			overallStatus.setTotalComments(0);
		}else{
			overallStatus.setTotalComments(totalComments);
		}
		
		if(status != null){
			overallStatus.setUserLikeStatus(status.getStatus());
		}else{
			overallStatus.setUserLikeStatus(Status.UNKNOWN.getStatus());
		}
		
		return overallStatus ;
		
	}
}
