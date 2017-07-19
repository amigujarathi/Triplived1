package com.triplived.rest.video;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.domain.triplived.trip.dto.TimelineTrip;
import com.domain.triplived.trip.dto.User;
import com.google.gson.Gson;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripVideoDao;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripVideoDb;
import com.triplived.mail.client.VideoStatusMail;
import com.triplived.service.trip.TripService;
import com.triplived.service.user.UserService;

@Component
public class TripLivedVideoClient {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TripLivedVideoClient.class);
	
	@Autowired
	private TripVideoDao tripVideoDao;
	
	@Autowired
	private TripDAO tripDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TripService tripService; 
	
	@Autowired
	private VideoStatusMail videoStatusMail;
	
	@Value("${videoGenerateUrl}")
	private String videoGenerateUrl;
	
	private static Boolean IS_VIDEO_PROCESSING_ON = true;
	
	
	//@Scheduled(fixedRate = 120000)
	public void run() {
		// TODO Auto-generated method stub
		
		if(IS_VIDEO_PROCESSING_ON) {
			
			List<Object[]> pendingTripList = tripVideoDao.getPendingTripsForVideoGeneration();
			if(!CollectionUtils.isEmpty(pendingTripList)) {
				Object[] objArr = pendingTripList.get(0);
				String tripVideo = objArr[0].toString();
				
				TripDb tripDb = tripDao.getTripById(Long.parseLong(tripVideo));
				logger.warn("In scheduler for Generating video for trip - " + Long.parseLong(tripVideo));
				Gson gson = new Gson();
				TimelineTrip trip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
				IS_VIDEO_PROCESSING_ON = false; 
				sendTripsForVideoGeneration(trip);
			}
			
		}
	}
	
	@Transactional
	public Boolean sendTripsForVideoGeneration(TimelineTrip trip) {

		
		String tripId = trip.getTripId();
		Long userId = tripService.getUserIdByTripId(Long.parseLong(tripId));
		PersonDb person = userService.loadUserDetails(userId.toString());
		User user = new User();
		user.setName(person.getName());
		user.setUserId(Integer.parseInt(userId.toString()));
		trip.setUser(user);
		
		try{
	    //PRODvideoGenerateUrl
		String url = videoGenerateUrl;
		//DEV
		//String url = "http://104.238.103.233:8080/triplived-video/video/generate";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		//add reuqest header
		con.setRequestMethod("POST");
		Gson gson = new Gson();
		String urlParameters = gson.toJson(trip);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line;
		while ((line = in.readLine()) != null) {
			if(line.equalsIgnoreCase("true")) {
				IS_VIDEO_PROCESSING_ON = true;
				generateMailDetails(trip, person.getName(), true);
			}
			if(line.equalsIgnoreCase("false")) {
				IS_VIDEO_PROCESSING_ON = true;
				logger.error("RETURNED FALSE STATE from video generation server. Please check, The responseCode is - {}, status is {}", responseCode, line);
				generateMailDetails(trip, person.getName(), false);
			}
			
	    }
		in.close();
		IS_VIDEO_PROCESSING_ON = true;
		return true;
		
		}catch(Exception e) {
			IS_VIDEO_PROCESSING_ON = true;
			logger.error("Trip Video Client: Returning false as exception occurred for submitting trip - {}, error - {}", trip.getTripId(), e.getStackTrace());
			return false;
		}
	}

    @Transactional
	public void generateMailDetails(TimelineTrip trip, String userName, Boolean videoGenerationStatus) {
    	List<TripVideoDb> list = tripVideoDao.getTripVideoDetails(Long.parseLong(trip.getTripId()));
    	List<String> tripVideoGenerationPhases = new ArrayList<String>(8);
    	for(TripVideoDb obj : list) {
    		tripVideoGenerationPhases.add(obj.getVideoStatus().getVideoStatus());
    	}
    	
    	videoStatusMail.sendMail(trip.getTripId(), trip.getTripName(), userName, tripVideoGenerationPhases, videoGenerationStatus);
    }

	

}
