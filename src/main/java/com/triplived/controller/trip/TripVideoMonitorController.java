package com.triplived.controller.trip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.TripVideoDTO;
import com.connectme.domain.triplived.dto.TripVideoPathDTO;
import com.google.gson.Gson;
import com.triplived.service.trip.TripVideoService;

@Controller
@RequestMapping("/tripVideoStatus")
public class TripVideoMonitorController {

	@Autowired
	private TripVideoService tripVideoService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return "TripVideoMonitor";
	}

	/**
	 * This API is invoked, when the user tries to get the status of trip video
	 * and video upload paths.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTripVideoStages/{tripID}", method = RequestMethod.GET)
	public @ResponseBody String getTripVideoStatus(@PathVariable Long tripID) {
		List<TripVideoDTO> videoStatus = tripVideoService
				.getTripVideoStatus(tripID);
		Gson gson = new Gson();
		return gson.toJson(videoStatus);
	}

	/**
	 * This API is invoked, when the user tries to get the status of trip video
	 * and video upload paths.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTripVideoPath/{tripID}", method = RequestMethod.GET)
	public @ResponseBody String getTripVideoPath(@PathVariable Long tripID) {
		TripVideoPathDTO tripPath = tripVideoService.getTripVideoPath(tripID);
		Gson gson = new Gson();
		return gson.toJson(tripPath);
	}

	/**
	 * This API is invoked, when the user tries to update the trip video upload
	 * paths.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateTripVideoPath", method = RequestMethod.POST)
	public @ResponseBody void updateTripVideoPath(
			@RequestParam("serverPath") @Valid String serverPath,
			@RequestParam("youTubePath") @Valid String youTubePath,
			@RequestParam("videoStatus") @Valid String videoStatus,
			@RequestParam("tripId") @Valid String tripId) {
		tripVideoService.updateTripVideoPath(serverPath, youTubePath,
				videoStatus, tripId);
	}

	/**
	 * This API is invoked, when the user tries to update the stages of trip
	 * video.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateTripVideoStages", method = RequestMethod.POST)
	public @ResponseBody void updateTripVideoStages(
			@RequestParam("tripId") @Valid Long tripId,
			@RequestParam("TripPub") @Valid String TripPub,
			@RequestParam("VidGen") @Valid String VidGen,
			@RequestParam("VidReq") @Valid String VidReq,
			@RequestParam("YouUp") @Valid String YouUp,
			@RequestParam("YouLive") @Valid String YouLive,
			@RequestParam("mailSent") @Valid String mailSent,
			@RequestParam("NotReq") @Valid String NotReq,
			@RequestParam("NotSent") @Valid String NotSent) {
		tripVideoService.updateTripVideoStatus(tripId,TripPub, VidGen, VidReq, YouUp,
				YouLive, mailSent, NotReq, NotSent);
	}
}