package com.triplived.controller.trip;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.triplived.service.trip.TripEntityCuratedService;

@Controller
@RequestMapping("/tripEntityReview")
public class TripEntityReviewController {

	private static final Logger logger = LoggerFactory.getLogger(TripEntityReviewController.class );
	
	@Autowired
	private TripEntityCuratedService tripEntityReviewService;
	
	
	/**
	 * This API is invoked, when the review of attraction for a trip are supposed to be fetched
	 * @param principal
	 * @param response
	 * @param tripId
	 * @param session
	 * @return
	 */
	
	
	@RequestMapping(method= RequestMethod.GET, value="/attraction/{tripId}")
    public @ResponseBody String attractionReview(Principal principal, final HttpServletResponse response,
    		@PathVariable Long tripId,HttpSession session) {
	
		/*List<AttractionReviewDTO> entities = new ArrayList<AttractionReviewDTO>();
		try {
			entities = tripEntityReviewService.getAttractionCuratedSuggestionsFromTripId(tripId);
			Gson gson = new Gson();
			return gson.toJson(entities);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			logger.error("Error while getting reviews for attraction corresponding to the trip Id- {}. Exception - {}",tripId, e.getStackTrace());
			return null;
		}*/
		return null;
		
   }
	
	/**
	 * This API is invoked, when the review of hotel for a trip are supposed to be fetched
	 * @param principal
	 * @param response
	 * @param tripId
	 * @param session
	 * @return
	 */
	
	
	@RequestMapping(method= RequestMethod.GET, value="/hotel/{tripId}")
    public @ResponseBody String hotelReview(Principal principal, final HttpServletResponse response,
    		@PathVariable Long tripId,HttpSession session) {
	
		/*List<HotelReviewDTO> entities = new ArrayList<HotelReviewDTO>();
		try {
			entities = tripEntityReviewService.getHotelCuratedSuggestionsFromTripId(tripId);
			Gson gson = new Gson();
			return gson.toJson(entities);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			logger.error("Error while getting reviews for hotels corresponding to the trip Id- {}. Exception - {}",tripId, e.getStackTrace());
			return null;
		}*/
		return null;
		
   }
	
}