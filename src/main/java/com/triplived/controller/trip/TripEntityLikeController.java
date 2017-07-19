package com.triplived.controller.trip;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse.Analysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.Analytics;
import com.connectme.domain.triplived.AppPage;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TripElement;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.domain.triplived.trip.dto.TripLivedResponseCode;
import com.domain.triplived.trip.dto.TripResponse;
import com.domain.triplived.trip.dto.UpVoteDownVoteStatus;
import com.google.gson.Gson;
import com.triplived.service.trip.TripLikeService;
import com.triplived.service.trip.TripService;
import com.triplived.util.TripLivedUtil;

/**
 * Handles all trip Entity like functioning
 * 
 * @author santosh 
 *
 */
@Controller
@RequestMapping("/tripEntity")
public class TripEntityLikeController {

	private static final Logger logger = LoggerFactory.getLogger(TripEntityLikeController.class );
	
	
	@Autowired
	private TripLikeService tripLikeService;
	
	@Autowired
	private TripService tripService;
	
	
	/**
	 * Upvote and downvote setting of an Entity
	 * 
	 * @param tripId
	 * @param entitySid
	 * @param userId
	 * @param tripLikeEntity
	 * @param userDevice
	 * @param applicationVersion
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/like/{tripId}/{entitySid}/{userId}", method = RequestMethod.POST)
	public @ResponseBody TripResponse likeTripEntity(@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("entitySid") @Valid String entitySid, @PathVariable("userId") @Valid Long userId, @RequestBody TripLikeInfo tripLikeEntity,
			 @RequestHeader("UserDeviceId") String userDevice, @RequestHeader("ApplicationVersion") String applicationVersion,
			 HttpServletRequest request
			 ) throws Exception {
	 
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalId = tripService.getTripIdByPublicId(tripId);
			tripId = internalId;
		}
		logger.warn("user :{}, action:liked, trip_entity:{}, trip :{}, action{} ", userId, entitySid, tripId, tripLikeEntity.getUserAction()  );
		
		tripLikeEntity.setId(entitySid);
		tripLikeEntity.setUserId(userId);
		tripLikeEntity.setTripId(tripId);
		
		logger.warn("TRIP entity Like :  {}", new Gson().toJson(tripLikeEntity));
		
		try{
			fireAnalytics(tripLikeEntity, request);
			return  tripLikeService.likeTripEntity(tripLikeEntity);
			
		}catch(Exception ex) {
			
			logger.error("Unable to like Entity by user {} and media {}" , userId, entitySid); 
			TripResponse response = new TripResponse();
			response.setCode(TripLivedResponseCode.RESPONSE_ERROR);
			response.setMessage("Unable to Like entity, Please try again!");
			
			return response;
		}
	}
	
	/**
	 * TODO change this and merge with the above similar function
	 * @param tripId
	 * @param entitySid
	 * @param userId
	 * @param userDevice
	 * @param applicationVersion
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/likeFromWeb/{tripId}/{entitySid}/{userId}/{userAction}", method = RequestMethod.POST)
	public @ResponseBody TripResponse likeTripEntityFromWeb(@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("entitySid") @Valid String entitySid, @PathVariable("userId") @Valid Long userId,
			@PathVariable("userAction") @Valid String userAction, 
			 @RequestHeader("UserDeviceId") String userDevice, @RequestHeader("ApplicationVersion") String applicationVersion,
			 HttpServletRequest request
			 ) throws Exception {
	 
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalId = tripService.getTripIdByPublicId(tripId);
			tripId = internalId;
		}
		logger.warn("user :{}, action:liked, trip_entity:{}, trip :{}, action{} ", userId, entitySid, tripId, userAction  );
		
		TripLikeInfo tripLikeEntity = new TripLikeInfo();
		tripLikeEntity.setId(entitySid);
		tripLikeEntity.setUserId(userId);
		tripLikeEntity.setTripId(tripId);
		tripLikeEntity.setUserAction(userAction);
		
		logger.warn("TRIP entity Like :  {}", new Gson().toJson(tripLikeEntity));
		
		try{
			fireAnalytics(tripLikeEntity, request);
			return  tripLikeService.likeTripEntity(tripLikeEntity);
			
		}catch(Exception ex) {
			
			logger.error("Unable to like Entity by user {} and media {}" , userId, entitySid); 
			TripResponse response = new TripResponse();
			response.setCode(TripLivedResponseCode.RESPONSE_ERROR);
			response.setMessage("Unable to Like entity, Please try again!");
			
			return response;
		}
	}
	
	
	private void fireAnalytics(TripLikeInfo tripLikeEntity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Analytics analytics = new Analytics();
		analytics.setHeaderParameters(request);
		analytics.CreateLog(tripLikeEntity.getTripId().toString(), AppPage.TIMELINE_PAGE, null, "openDialog", tripLikeEntity.getTripId()+"", tripLikeEntity.getId(), tripLikeEntity.getUserAction()  );
	}

	/**
	 * Get All people who liked a photo
	 */
	
	@RequestMapping(value = "/likedBy/{userid}/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PeopleLiked> whoAllLiked(@PathVariable("userid") @Valid String userid,
			@PathVariable("id") @Valid String id, @RequestHeader("UserDeviceId") String userDevice, HttpServletRequest request){
		
		List<PeopleLiked> whoAllLiked = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(id)){
			try{
				logger.warn("user :{}, action:want_entity_likes_of, entity:{} ", userid, id  );
				
				if(TripLivedUtil.isPublicIdEnabled(request)) {
					Long internalId = tripService.getTripIdByPublicId(Long.parseLong(id));
					id = internalId.toString();
				}
				whoAllLiked = tripLikeService.peopleWhoLiked(Long.parseLong(id), id, TripElement.TRIP_ENTITY);
				
			}catch(Exception ex) {
				logger.error("exception "+ ex);
				logger.error("Unable to get liked by user for entity {} " , id); 
			}
		}
		
		return whoAllLiked ;
	}
	
	@RequestMapping(value = "/status/{tripId}/{entitySid}/{userId}", method = RequestMethod.GET)
	public @ResponseBody UpVoteDownVoteStatus getUserStatusOnEntity(@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("entitySid") @Valid Long entitySid, @PathVariable("userId") @Valid Long userId, HttpServletRequest request ){
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalId = tripService.getTripIdByPublicId(tripId);
			tripId = internalId;
		}
		
		UpVoteDownVoteStatus status = null;
		try{
			logger.warn("user :{}, get user satus of entity:{} for trip:{}  ", userId, entitySid, tripId );
			status =  tripLikeService.getUserStatusOnEntity(userId, tripId, entitySid);
			return status;
		}catch(Exception ex) {
			logger.error("exception "+ ex);
			logger.error("Unable to get user "+ userId+" status on trip " +  tripId +" entity " +entitySid , ex); 
		}
		
		if(status == null){
			status = new UpVoteDownVoteStatus();
			status.setUserVerdict(Status.UNKNOWN.getStatus());
		}
		
		return status;
	}
}
