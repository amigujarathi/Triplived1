package com.triplived.controller.trip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.dto.AttractionReviewDTO;
import com.connectme.domain.triplived.dto.HotelReviewDTO;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.entity.UpdatedHotelReview;
import com.triplived.service.trip.TripEntityDisplayService;
import com.triplived.service.trip.TripReviewEditService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/tripReviewEdit")
@SessionAttributes(Constants.LOGIN_USER)
public class TripReviewsEditController {

	@Autowired
	private TripReviewEditService tripReviewEditService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return "TripReviewsEdit";
	}

	/**
	 * This API is invoked, when the user tries to edit the reviews and
	 * suggestions of the city
	 * 
	 * @return
	 */


	/**
	 * This API is invoked, when the user tries to edit the reviews and
	 * suggestions of the attraction
	 * 
	 * @return
	 */

	@RequestMapping(value = "/attractionReviews/{tripId}", method = RequestMethod.GET)
	public @ResponseBody String getAttractionReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@PathVariable Long tripId) {
		if (user == null) {
			return null;
		} else {
			List<AttractionReviewDTO> er = tripReviewEditService
					.getAttractionReviews(tripId);
			Gson gson = new Gson();
			return gson.toJson(er);
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
			@RequestBody UpdatedAttractionReview updatedAttractionReview) {
		if (null == user) {
			// return "You need to be logged in to make changes";
			return;
		}
		//updatedAttractionReview.setUserName(user.getName());
		tripReviewEditService
				.saveCuratedAttractionReviews(updatedAttractionReview,user.getName());
	}

	/**
	 * This API is invoked, when the user tries to edit the reviews and
	 * suggestions of the Hotel
	 * 
	 * @return
	 */

	@RequestMapping(value = "/hotelReviews/{tripId}", method = RequestMethod.GET)
	public @ResponseBody String getHotelReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@PathVariable Long tripId) {
		if (null == user) {
			// return "You need to be logged in to make changes";
			return null;
		}
		List<HotelReviewDTO> er = tripReviewEditService.getHotelReviews(tripId);
		Gson gson = new Gson();
		return gson.toJson(er);
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
			@RequestBody UpdatedHotelReview updatedHotelReview) {
		if (null == user) {
			// return "You need to be logged in to make changes";
			return;
		}
		//updatedHotelReview.setUserName(user.getName());
		tripReviewEditService.saveCuratedHotelReviews(updatedHotelReview,user.getName());

	}
}