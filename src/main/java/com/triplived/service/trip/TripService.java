package com.triplived.service.trip;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.connectme.domain.triplived.VideoStatus;
import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.connectme.domain.triplived.dto.TripSummaryDTO;
import com.connectme.domain.triplived.dto.WebTripDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripEventDTO;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripFaqDb;
import com.triplived.entity.TripKitListDb;
import com.triplived.entity.TripMetaData;

public interface TripService {

		

		String getTrip(Long tripId);

		void addAttractionToTrip(Long tripId, AttractionDataUploadDTO attrObj);

		//Long createTrip(Trip trip);

		

		void addAttractionToCity(String tripId, TripEventDTO attrDTO);

		void addHotelToCity(String tripId, TripEventDTO attrDTO);

		SubTrip getActiveSubTrip(Long tripId, Trip trip, String status);

		void updateTripCity(Long tripId, Long subTripId, TripCityDTO cityDTO,
				String transportType);

		Long getTripIdByEmail(Long email);

		void createUserTrip(Long personId, Long tripId);

		void endTrip(Long tripId, Long personId);

		


		String getTripNameById(Long tripId);

		Long getUserIdByTripId(Long tripId);

		List<TripSearchDTO> getTripsByDestCity(String toCityId);

		String getLikeOfUser(Long tripId, Long userId, String like);

		Boolean saveTimelineTrip(TimelineTrip timelineTrip, String deviceId);
		
		Boolean saveEventsFromUserTrip(Long tripId, String deviceId, Boolean flag);
		
		Boolean saveCitiesFromUserTrip(Long tripId, String deviceId, Boolean flag);

		void publishVideoStatus(Long tripId, VideoStatus status);

		Boolean updateVideoYoutubePath(String tripId, String youtubePath);

		Boolean updateVideoServerPath(String tripId, String serverPath);

		Boolean sendTripsForVideoGeneration(TimelineTrip trip);

		Long createTrip(TimelineTrip trip, String endDateTimestamp, String source);

		String addCommentsOnTrip(Long tripId, Long userId, String comment);

		List<TripSearchDTO> getLikedTripsOfUser(String personId);

		List<TripCommentDTO> getCommentsOnTrip(Long tripId);

		Boolean saveTimelineTripForReview(TimelineTrip timelineTrip,
				String deviceId);

		TimelineTrip getTripById(Long tripId, String requestorId);

		String addReviewsOfEvents(String tripId) throws Exception;

		TimelineTrip getTripForShare(Long tripId, String requestorId);

		String updateTripPublicId(Long tripId);

		Long getTripIdByPublicId(Long publicTripId);

		Long getPublicTripIdById(Long tripId);

		String addSharesOfTrip(Long tripId, Long userId);

		void populateTimelineTripData(TimelineTrip trip, boolean breakCityWise,
				boolean breakEntityWise);

		Future<Boolean> pushOtherDetails(TimelineTrip trip);

		List<TripDb> getAllTripsOfTypeTimeline();

		void fireTripSubmitEvent(TimelineTrip trip, Map<TripMetaData, Integer> tripStats);

		String updateTripWithDeleteStatus(Long tripId);

		String updateTripWithEditStatus(Long tripId);

		String updateTripWithRecordingStatus(Long tripId);

		Future<Boolean> updateTripWithFinalizedStatus(Long tripId);

		List<TripFaqDb> getTripFaqList(Long tripId);

		List<TripKitListDb> getTripKitList(Long tripId);

		String createTripFromWebExperiences(Long tripId,
				TripSummaryDTO tripSummary);

		Boolean saveTimelineTripCreatedFromWeb(TimelineTrip timelineTrip,
				String deviceId);

		String updateTripStateFromWeb(String tripId, String status,
				String userId);

		WebTripDTO getTripForWebEdit(String newTripId, Long internalTripId);


		


}
