package com.triplived.service.trip.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripElement;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripMediaDAO;
import com.triplived.entity.TripMediaCommentsDb;
import com.triplived.entity.TripUserDb;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.service.trip.TripCommentService;

/**
 * 
 * @author santosh
 *
 * for documentation @see TripLikeService
 */
@Service
public class TripCommentServiceImpl implements TripCommentService {

	private static final Logger logger = LoggerFactory.getLogger(TripCommentServiceImpl.class );

	@Autowired
	private TripMediaDAO tripMediaDao;
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private GCMNotificationService gcmService;
	
	@Transactional(readOnly=false)
	@Override
	public String addCommentsOnTripMedia(Long tripId, Long userId, String mediaId, String comment) {
		
		logger.warn("add comments to trip Media {} and userId {} ", mediaId, userId);
		TripMediaCommentsDb newObj = new TripMediaCommentsDb();
		newObj.setTripId(tripId);
		newObj.setUserId(userId);
		newObj.setMediaId(mediaId);
		newObj.setStatus("A");
		newObj.setUpdateDate(new Date());
		newObj.setComment(comment);
		newObj.setTimeStamp(System.currentTimeMillis() / 1000L);
		
		TripUserDb tripUser = tripDao.getUserIdByTripId(tripId);
		newObj.setTripUserId(tripUser.getUserId());
		
		tripMediaDao.updateTripComment(newObj);
		
		gcmService.sendTripMediaCommentNotification(tripId, userId, mediaId);
		return "Updated-"+newObj.getId();
	}

	@Transactional(readOnly=true)
	@Override
	public List<TripCommentDTO> getCommentsOnTrip(Long tripId, String mediaId) {
		List<Object[]> tripCommentsList = tripMediaDao.getCommentsOnTripMedia(tripId, mediaId);
		List<TripCommentDTO> tripComments = new ArrayList<TripCommentDTO>(tripCommentsList.size());
		if(!CollectionUtils.isEmpty(tripCommentsList)) {
			
			for(Object[] oArr : tripCommentsList) {
				
				TripCommentDTO obj = new TripCommentDTO();
				
				if(null != oArr[0]) {
					obj.setUserId(oArr[0].toString());
				}
				if(null != oArr[1]) {
					//obj.setDateTime(oArr[1].toString());
				}
				if(null != oArr[2]) {
					obj.setComment(oArr[2].toString());
				}
				if(null != oArr[3]) {
					obj.setUserName(oArr[3].toString());
				}
				if(null != oArr[4]) {
					obj.setUserFbId(oArr[4].toString());
				}
				if(null != oArr[5]) {
					obj.setTimeStamp(oArr[5].toString());
				}
				if(null != oArr[6]) {
					obj.setCommentId(oArr[6].toString());
				}
				tripComments.add(obj);
			}
			
			return tripComments;
		}else {
			return null;
		}
	}
	

	@Override
	@Transactional("txManager")
	public Status updateCommentStatus(Long tripId, Long userId, Long mediaId, String status, TripElement element) {
		
		Status stat = Status.DELETED;
		if("I".equalsIgnoreCase(status) || "A".equalsIgnoreCase(status)){
			if(element == TripElement.TRIP_PHOTO){
				stat =  tripMediaDao.updateCommentStatus(userId, tripId, mediaId, status);
			}else if(element == TripElement.TRIP){
				stat = tripDao.updateCommentStatus(userId, tripId, mediaId, status);
			}
		}
		return stat;
		
	}
	 
}
