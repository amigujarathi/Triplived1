package com.triplived.controller.trip;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.solr.client.solrj.SolrServerException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.dto.AttractionCuratedSuggestionDTO;
import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.ExploreTagsDTO;
import com.connectme.domain.triplived.dto.HotelCuratedSuggestionDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.connectme.domain.triplived.dto.TripBetweenCityDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.entity.CuratedAttractionReview;
import com.triplived.entity.CuratedHotelReview;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;
import com.triplived.service.trip.TripEntityCurationEditService;
import com.triplived.service.trip.TripEntityCuratedService;
import com.triplived.service.trip.TripListService;
import com.triplived.service.trip.TripSearchService;
import com.triplived.service.trip.TripService;
import com.triplived.service.user.UserService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/tripEntityCuration")
@SessionAttributes(Constants.LOGIN_USER)
public class TripEntityCurationEditController {

	private static final Logger logger = LoggerFactory
			.getLogger(TripEntityCurationEditController.class);

	@Autowired
	private TripEntityCurationEditService tripEntityCurationEditService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return "tripEntityCurationPage";
	}

	/**
	 * This API is invoked, when the review of attraction are supposed to be
	 * fetched
	 * 
	 * @param principal
	 * @param response
	 * @param tripId
	 * @param session
	 * @return
	 */

	@RequestMapping(value = "/attractionReviews/{attractionId}", method = RequestMethod.GET)
	public @ResponseBody String getAttractionReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@PathVariable String attractionId) {
		if (user == null) {
			logger.error(
					"Admin trying to fetch attraction reviews without logging in for the attraction ID -{}",
					attractionId);
			return null;
		} else {
			try {
				List<AttractionCuratedSuggestionDTO> er = tripEntityCurationEditService
						.getAttractionReviews(attractionId);
				logger.warn(
						"Curated attraction Reviews successfully fetched for the attraction ID {} ",
						attractionId);
				Gson gson = new Gson();
				return gson.toJson(er);
			} catch (Exception e) {
				logger.error(
						"Error while getting curated attraction reviews for the attraction ID -{}",
						attractionId);
				return null;
			}
		}
	}

	/**
	 * This API is invoked, when the review of hotel are supposed to be fetched
	 * 
	 * @param principal
	 * @param response
	 * @param tripId
	 * @param session
	 * @return
	 */

	@RequestMapping(value = "/hotelReviews/{hotelId}", method = RequestMethod.GET)
	public @ResponseBody String gethotelReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@PathVariable String hotelId) {
		if (user == null) {
			logger.error(
					"Admin trying to fetch attraction reviews without logging in for the attraction ID -{}",
					hotelId);
			return null;
		} else {
			try {
				List<HotelCuratedSuggestionDTO> er = tripEntityCurationEditService
						.getHotelReviews(hotelId);
				logger.warn(
						"Curated hotel Reviews successfully fetched for the hotel ID {} ",
						hotelId);
				Gson gson = new Gson();
				return gson.toJson(er);
			} catch (Exception e) {
				logger.error(
						"Error while getting curated hotel reviews for the hotel ID -{}",
						hotelId);
				return null;
			}
		}
	}

	/**
	 * This API is invoked, when the user tries to save the edited reviews and
	 * suggestions of the attraction
	 * 
	 * @return
	 */

	@RequestMapping(value = "/saveAttractionReviews", method = RequestMethod.POST)
	public @ResponseBody void saveAttractionReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestBody CuratedAttractionReview curatedAttractionReview) {
		if (null == user) {
			logger.error(
					"Admin trying to change the attraction curated reviews without logging in for the trip ID -{}",
					curatedAttractionReview.getTripId());
			// return "You need to be logged in to make changes";
			return;
		}
		try {
			tripEntityCurationEditService.saveCuratedAttractionReviews(
					curatedAttractionReview, user.getName());
			logger.warn(
					"Trip Admin Reviews successfully saved for the trip ID {} ",
					curatedAttractionReview.getTripId());
		} catch (Exception e) {
			logger.error(
					"Error while saving curated admin reviews for the trip ID -{}",
					curatedAttractionReview.getTripId());
		}
	}

	/**
	 * This API is invoked, when the user tries to save the edited reviews and
	 * suggestions of the hotels
	 * 
	 * @return
	 */

	@RequestMapping(value = "/saveHotelReviews", method = RequestMethod.POST)
	public @ResponseBody void saveHotelReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestBody CuratedHotelReview curatedHotelReview) {
		if (null == user) {
			/*
			 * logger.error(
			 * "Admin trying to change the attraction curated reviews without logging in for the trip ID -{}"
			 * , CuratedHotelReview.getTripId());
			 */
			// return "You need to be logged in to make changes";
			return;
		}
		// updatedHotelReview.setUserName(user.getName());
		try{
		tripEntityCurationEditService.saveCuratedHotelReviews(
				curatedHotelReview, user.getName());
		/*logger.warn(
				"Trip Admin Reviews successfully saved for the trip ID {} ",
				curatedAttractionReview.getTripId());*/
		}catch (Exception e) {
			/*logger.error(
					"Error while saving curated admin reviews for the trip ID -{}",
					CuratedHotelReview.getTripId());*/
		}
		

	}
}