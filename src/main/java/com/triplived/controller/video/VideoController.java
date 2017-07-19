package com.triplived.controller.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.VideoStatus;
import com.triplived.service.trip.TripService;

@Controller
@RequestMapping("/video")
public class VideoController {

	private static final Logger logger = LoggerFactory.getLogger(VideoController.class );
	
	@Autowired
	private TripService tripService;
	
	/**
	 * This code also gets called from the video server
	 * @param tripId
	 * @param path
	 * @return
	 */
	@RequestMapping(value = "/publishStatus", method = RequestMethod.GET)
    public @ResponseBody Boolean publishVideoStatus(@RequestParam("tripId") String tripId,
    		@RequestParam("status") String status) {
    	
		
		logger.warn("VIDEO PUBLISH: Video status for trip -{} with status - {}", tripId, status);
		try {
		tripService.publishVideoStatus(Long.parseLong(tripId), VideoStatus.valueOf(status));
		
		return true;
		}catch(Exception e) {
			logger.error("Error while publishing video state for trip - {} and exception - {}", tripId, e);
			return false;
		}
    }
	
	
}
