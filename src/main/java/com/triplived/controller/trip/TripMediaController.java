package com.triplived.controller.trip;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.domain.triplived.trip.dto.LikeStatus;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripElement;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.google.gson.Gson;
import com.triplived.service.trip.TripCommentService;
import com.triplived.service.trip.TripLikeService;
import com.triplived.service.trip.TripService;
import com.triplived.util.TripLivedUtil;

/**
 * 
 * @author santosh
 *
 */
@Controller
@RequestMapping("/tripMedia")
public class TripMediaController {

	private static final Logger logger = LoggerFactory.getLogger(TripMediaController.class );
	
	@Autowired
	private TripLikeService tripLikeService;
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private TripCommentService tripCommentService;
	
	/**
	 * Like/Unlike a trip
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/like/{tripId}/{mediaId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody LikeStatus likeTrip(@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("mediaId") @Valid String mediaId, @PathVariable("userId") @Valid Long userId, @RequestBody TripLikeInfo tripMedia,
			 @RequestHeader("UserDeviceId") String userDevice, 
			 @RequestHeader("ApplicationVersion") String applicationVersion, HttpServletRequest request
			 ) throws Exception {
	 
	
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalId = tripService.getTripIdByPublicId(tripId);
			tripId = internalId;
		}
		
		logger.warn("user :{}, action:liked, trip_media:{}, trip :{}  action {}", userId, mediaId, tripId,  tripMedia.getUserAction()  );
		
		tripMedia.setId(mediaId);
		tripMedia.setUserId(userId);
		tripMedia.setTripId(tripId);
		if(StringUtils.isEmpty(tripMedia.getContentType())){
			tripMedia.setContentType("image/jpeg");
		}
		
		logger.warn("TRIP Like :  {}", new Gson().toJson(tripMedia));
		
		try{
			LikeStatus status =   tripLikeService.likeTripMedia(tripMedia);
			
			return status;
			
		}catch(Exception ex) {
			
			logger.error("Unable to like by user {} and media {}" , userId, mediaId); 
			LikeStatus response = new LikeStatus();
			response.setUserLikeStatus(Status.UNKNOWN.getStatus());
			response.setTotalLikes(0);
			
			return response;
		}
	}

	/**
	 * Get All people who liked a photo
	 */
	
	@RequestMapping(value = "/likedBy/{userid}/{tripId}/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PeopleLiked> whoAllLiked(@PathVariable("tripId") @Valid Long tripId, @PathVariable("userid") @Valid String userid,
			@PathVariable("id") @Valid String id, @RequestHeader("UserDeviceId") String userDevice, HttpServletRequest request){
		List<PeopleLiked> whoAllLiked = new ArrayList<>();
		
		logger.warn("user :{}, action:want_photo_likes_of, entity:{} ", userid, id  );
		
		if(StringUtils.isNotEmpty(id)){
			try{
				if(TripLivedUtil.isPublicIdEnabled(request)) {
					Long internalId = tripService.getTripIdByPublicId(tripId);
					tripId = internalId;
				}
				//Long identifier = Long.parseLong(id);
				whoAllLiked = tripLikeService.peopleWhoLiked(tripId, id, TripElement.TRIP_PHOTO);
				
			}catch(Exception ex) {
				logger.error("exception "+ ex);
				logger.error("Unable to get liked by user for media {} " , id); 
			}
		}
		
		return whoAllLiked ;
	}
	
	@RequestMapping(value = "/status/{tripId}/{entitySid}/{userId}", method = RequestMethod.GET)
	public @ResponseBody LikeStatus getUserStatusOnEntity(@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("entitySid") @Valid String entitySid, 
			@PathVariable("userId") @Valid Long userId, HttpServletRequest request ){
		

		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalId = tripService.getTripIdByPublicId(tripId);
			tripId = internalId;
		}
		
		LikeStatus status = null;
		try{
			logger.warn("user :{}, get user satus of entity:{} for trip:{}  ", userId, entitySid, tripId );
			status =  tripLikeService.getUserStatusOnMediaLike(userId, tripId, entitySid);
			return status;
		}catch(Exception ex) {
			logger.error("exception "+ ex);
			logger.error("Unable to get user "+ userId+" status on trip " +  tripId +" entity " +entitySid , ex); 
		}
		
		if(status == null){
			status = new LikeStatus();
			status.setUserLikeStatus(Status.UNKNOWN.getStatus());
		}
		return status;
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/comment/{tripId}/{mediaId}/{userId}")
	public @ResponseBody String tripComments(
			HttpServletRequest request,
			@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("mediaId") @Valid String mediaId, 
			@PathVariable("userId") @Valid Long userId,
			@RequestParam(value = "value") String comment) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(tripId);
			tripId = internalTripId;
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip comment request for trip - {} from user - {} and deviceId - {}", tripId, userId, deviceId);
		
		if(null != deviceId && null != userId && null != tripId) {
			String returnValue =  tripCommentService.addCommentsOnTripMedia(tripId, userId, mediaId, comment);
			return returnValue;
		}else {
			return null;
		}
	}

	
	@RequestMapping(method= RequestMethod.POST, value="/updatecomment/{tripId}/{commendId}/{userId}")
	public @ResponseBody Status updateComment(
			HttpServletRequest request,
			@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("commendId") @Valid Long commendId,
			@PathVariable("userId") @Valid Long userId,
			@RequestParam(value = "value") String comment) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(tripId);
			tripId = internalTripId;
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip Media comment Update request for trip - {} from user - {} and deviceId - {} and commentId {} ", tripId, userId, deviceId, commendId);
		
		if(null != deviceId && null != userId && null != tripId) {
			return tripCommentService.updateCommentStatus(tripId, userId, commendId, comment, TripElement.TRIP_PHOTO);
		}else {
			return null;
		}
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/getcomments/{tripId}/{mediaId}/{userId}")
	public @ResponseBody String getCommentsOnTrip(HttpServletRequest request,
			@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("mediaId") @Valid String mediaId, 
			@PathVariable("userId") @Valid Long userId) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(tripId);
			tripId = internalTripId;
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip Get Media comments request for trip {} and media {} and user - {}", tripId, mediaId, userId);
		
		if(null != deviceId && null != tripId) {
			List<TripCommentDTO> tripComments = tripCommentService.getCommentsOnTrip(tripId, mediaId);
			Gson gson = new Gson();
			return gson.toJson(tripComments);
			
		}else {
			return null;
		}
	}
		
}
