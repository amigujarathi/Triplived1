package com.triplived.controller.trip;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.controller.home.HomeController;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.triplived.entity.TripDb;
import com.triplived.service.trip.TripJsonEditService;


@Controller
@RequestMapping("/tripJson")
public class TripJsonEditController {

	private static final Logger logger = LoggerFactory
			.getLogger(TripJsonEditController.class);

	@Autowired
	private TripJsonEditService tripJsonEditService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return null;//"JsonEditor";
	}

	/**
	 * This Method is called, when the user tries to fetch the trip_data for a
	 * specific trip ID in the editor to edit some values.
	 * 
	 * @param tripId
	 * @return
	 */

	@RequestMapping(value = "/ajax-fetch-json/{tripId}", method = RequestMethod.GET)
	@ResponseBody
	public TripDb fetchTripJson(@PathVariable Long tripId)
			throws JsonGenerationException, JsonMappingException, IOException {
		 
		TripDb tripJson = tripJsonEditService.displayTripJson(tripId);
		if(tripJson == null){
			logger.error("Trip ID entered does not exists");
		}
		else{
			logger.warn("Trip Json data fetched for trip ID : ",tripId);
		}
		return tripJson;

	}

	/**
	 * This Method is called, to beautify the trip data json fetched from
	 * database through "FetchTripJson" service as the json returned from this
	 * service is in compact form. This beautified Json is then displayed in the
	 * editor.
	 */

	@RequestMapping(value = "/ajax-beautify-json", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public String beautifyTripJson(
			@RequestParam("compactjson") @Valid String compactjson) {
		if (compactjson != null && !compactjson.isEmpty()) {
			try {
				JsonParser parser = new JsonParser();
				JsonObject json = parser.parse(compactjson).getAsJsonObject();
				Gson gson = new GsonBuilder().setPrettyPrinting()
						.disableHtmlEscaping().create();
				String prettyJson = gson.toJson(json);
				return prettyJson;
			} catch (Exception e) {
				e.printStackTrace();
				return compactjson;
			}
		} else {
			logger.warn("compactJson is null.");
			return "compactJson is null";
		}

	}

	/**
	 * This Method is called to save the edited trip data json from the editor
	 * into the database.
	 */
	@RequestMapping(value = "/ajax-submit-json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody void submitTripJson(@RequestBody TimelineTrip trip) {
		tripJsonEditService.submitTripJson(trip, Long.parseLong(trip.getTripId()));
	}
}
