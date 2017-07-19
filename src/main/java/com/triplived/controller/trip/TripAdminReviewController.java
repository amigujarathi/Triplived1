package com.triplived.controller.trip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.connectme.domain.triplived.dto.TripAdminReviewDTO;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.entity.TripReviewData;
import com.triplived.entity.UpdatedAttractionReview;
import com.triplived.service.trip.TripAdminReviewService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/tripReviewComments")
@SessionAttributes(Constants.LOGIN_USER)
public class TripAdminReviewController {

	private static final Logger logger = LoggerFactory
			.getLogger(TripAdminReviewController.class);

	@Autowired
	private TripAdminReviewService tripAdminReviewService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return "TripAdminReviews";
	}

	/**
	 * This API is invoked, when the admin tries to see the reviews
	 * 
	 * @return
	 */

	@RequestMapping(value = "/tripReviews/{tripId}", method = RequestMethod.GET)
	public @ResponseBody String getTripReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@PathVariable Long tripId) {
		if (user == null) {
			logger.error(
					"User trying to fetch admin reviews without logging in for the trip ID -{}",
					tripId);
			return null;
		} else {
			try {
				List<TripAdminReviewDTO> er = tripAdminReviewService
						.getTripReviews(tripId);
				logger.warn(
						"Trip Reviews successfully fetched for the trip ID {} ",
						tripId);
				Gson gson = new Gson();
				return gson.toJson(er);
			} catch (Exception e) {
				logger.error("Error while getting admin reviews for the trip ID -{}",
						tripId);
				return null;
			}
		}

	}

	/**
	 * This API is invoked, when the admin tries to add the reviews
	 * 
	 * @return
	 */

	@RequestMapping(value = "/saveTripReviews", method = RequestMethod.POST)
	public @ResponseBody void saveTripReviews(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestBody TripReviewData tripReviewData) {
		if (null == user) {
			logger.error(
					"User trying to change the admin reviews without logging in for the trip ID -{}",
					tripReviewData.getTripId());
			// return "You need to be logged in to make changes";
			return;
		}
		try {
			tripAdminReviewService
					.saveTripReviews(tripReviewData, user.getPersonId());
			logger.warn(
					"Trip Admin Reviews successfully saved for the trip ID {} ",
					tripReviewData.getTripId());
		} catch (Exception e) {
			logger.error("Error while saving admin reviews for the trip ID -{}",
					tripReviewData.getTripId());
		}
	}
}