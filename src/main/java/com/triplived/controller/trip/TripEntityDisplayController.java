package com.triplived.controller.trip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.EntityResponse;
import com.google.gson.Gson;
import com.triplived.service.trip.TripEntityDisplayService;


@Controller
@RequestMapping("/tripEntityDisplay")
public class TripEntityDisplayController {

	@Autowired
	private TripEntityDisplayService tripEntityDisplayService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return "TripEntityDisplay";
	}

	/**
	 * This API is invoked, when the user tries to get the entity details of the
	 * trip
	 * 
	 * @return
	 */
	@RequestMapping(value = "/entityDetails/{tripId}", method = RequestMethod.GET)
	public @ResponseBody String getEntityDetailsFromId(@PathVariable Long tripId) {
		List<EntityResponse> er = tripEntityDisplayService
				.getEntityDetailsFromId(tripId);
		Gson gson = new Gson();
		return gson.toJson(er);
	}
}