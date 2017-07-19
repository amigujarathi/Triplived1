package com.triplived.controller.trip;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connectme.domain.triplived.dto.PersonDTO;
import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.connectme.domain.triplived.dto.TripSummaryDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.domain.triplived.trip.dto.Event;
import com.domain.triplived.trip.dto.PeopleLiked;
import com.domain.triplived.trip.dto.PeopleLikedList;
import com.domain.triplived.trip.dto.Status;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.domain.triplived.trip.dto.ToCityDTO;
import com.domain.triplived.trip.dto.TripElement;
import com.domain.triplived.trip.dto.TripLikeInfo;
import com.domain.triplived.trip.dto.TripLivedResponseCode;
import com.domain.triplived.trip.dto.TripResponse;
import com.domain.triplived.trip.dto.UpVoteDownVoteStatus;
import com.domain.triplived.trip.dto.Update;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.controller.video.VideoController;
import com.triplived.entity.TripFaqDb;
import com.triplived.entity.TripKitListDb;
import com.triplived.mail.client.RecordingStartMail;
import com.triplived.mail.client.VideoGenerationRequestMail;
import com.triplived.service.trip.TripCommentService;
import com.triplived.service.trip.TripLikeService;
import com.triplived.service.trip.TripService;
import com.triplived.service.user.UserService;
import com.triplived.util.Constants;
import com.triplived.util.RetriableTask;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/trip")
public class TripController {

	private static final Logger logger = LoggerFactory.getLogger(TripController.class );
	
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private TripLikeService tripLikeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TripCommentService tripCommentService;
	
	@Autowired
	private MiscDataController miscDataController;
	
    @Value("${images.upload.dir}")
    private String imagesUploadDir;
    
    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
    
    @Value("${phantomjs.command.script}")
    private String phantomCommand;
    
    @Value("${phantomjs.screenshotpath}")
    private String phantomScreenShotCommand;
    
    @Value("${images.user.timeline.upload.dir}")
    private String tripPhotoDir;
    
    
    @Value("${tl.domain}")
    private String tlDomain;
    
    @Value("${moderatorIds}")
    private String moderatorIds;
    
    
    @Autowired
    private RecordingStartMail recordStartMail;
    
    
    @Autowired
    private VideoGenerationRequestMail videoGenerationRequestMail;
    
    @Autowired
    private VideoController videoController;
    
    
    @RequestMapping(method= RequestMethod.GET)
	public String tripStatus(HttpSession session, HttpServletRequest request, Model model) {
    	    Person person = (Person) session.getAttribute(Constants.LOGIN_USER);
    	    if(person != null) {
    	    	model.addAttribute("isLoggedIn", true);
    	    	Long tripId = tripService.getTripIdByEmail(person.getPersonId());
    	    	
    	    	if(tripId == null) {
    	    		String redirectUrl =  "trip/progress";
    				return "redirect:" + redirectUrl;
    	    	}else {
    	    		
    	    		String x = tripService.getTrip(tripId);
    	    		model.addAttribute("presentTrip", x);
    	    		
    	    		Gson gson = new Gson();
    	    		Trip trip = gson.fromJson(x, Trip.class);
    	    		
    	    		SubTrip currentSubTrip = tripService.getActiveSubTrip(tripId, trip, null);
    	    		if(null == currentSubTrip) {
    	    			String redirectUrl = "city/progress?tripId="+tripId;
        				return "redirect:" + redirectUrl;
    	    		} else {
    	    			if(null == currentSubTrip.getToCityDTO()) {
    	    				String redirectUrl = "city/progress?tripId="+tripId;
            				return "redirect:" + redirectUrl;	
    	    			}
    	    			if(!currentSubTrip.getComplete()) {
    	    				//create jsp
    	    				String attractionUrl = "attraction/progress?tripId="+tripId+"&cityId="+currentSubTrip.getToCityDTO().getCityId()+
    	    									   "&subTripId="+currentSubTrip.getId();
    	    				
    	    				String hotelUrl = "hotel/progress?tripId="+tripId+"&cityId="+currentSubTrip.getToCityDTO().getCityId()+
									   "&subTripId="+currentSubTrip.getId();
    	    				
    	    				String newDestinationUrl = "city/progress?tripId="+tripId+"&status=new";
    	    				
    	    				model.addAttribute("attractionUrl", attractionUrl);
    	    				model.addAttribute("hotelUrl", hotelUrl);
    	    				model.addAttribute("newDestinationUrl", newDestinationUrl);
    	    				return "trip-status";
    	    			}
    	    		}
    	    		
    	    	}
    	    	
    	    }
    	    if(person == null) {
    	    	model.addAttribute("isLoggedIn", false);
    	    	return "trip-status";
    	    }
    	    return "trip-status";
	}
    
    @RequestMapping(method= RequestMethod.GET, value="/progress")
	public String index(HttpSession session, HttpServletRequest request, Model model) {
    	    Person person = (Person) session.getAttribute(Constants.LOGIN_USER);
    	    model.addAttribute("trip", new Trip());
			return "trip-parent";
	}
    
    @RequestMapping(method= RequestMethod.POST, value="/trip-start")
    public String startTrip(@ModelAttribute("trip") Trip trip,  ModelMap model) {
	
    	//List<AttractionResponseDTO> attractions = staticAttractionService.getAttractionByCity(city);
    	
		Gson gson = new Gson();
		model.addAttribute("trip", trip);
		return "trip";
		//return gson.toJson(attractions);
    }
    
       
	@RequestMapping(value = "/ajax-post", method = RequestMethod.POST)
	public @ResponseBody String uploadImage(Principal principal, final HttpServletResponse response, 
			@RequestParam("tripName") @Valid String tripName,
			@RequestParam("tripDescription") @Valid String tripDescription,
			@RequestParam("timeStamp")  @Valid String timeStamp,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = null;
		try {
			date = formatter.parse(timeStamp);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Long ts = date.getTime() / 1000;
		
		Trip trip = new Trip();
		trip.setTripName(tripName);
		trip.setTripStartTime(ts);
		//Long tripId = tripService.createTrip(trip);
		
		Person person = (Person) session.getAttribute(Constants.LOGIN_USER);
		//tripService.createUserTrip(person.getPersonId(), tripId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), trip.getTripId());
		return trip.getTripId().toString();
	}
	
	
	@RequestMapping(value = "/createTrip", method = RequestMethod.GET)
	public @ResponseBody String createTrip(Principal principal, final HttpServletResponse response,
			@RequestParam("userId") @Valid String userId,
			@RequestParam("tripName") @Valid String tripName,
			@RequestParam(value = "tripDescription", required = false) @Valid String tripDescription,
			@RequestParam(value =  "timeStamp", required = true)  @Valid String timeStamp,
			@RequestParam(value = "type", required = false) @Valid String type,
			@RequestParam(value = "endDate", required = false) @Valid String endDate,
			@RequestParam(value = "source", required = false) @Valid String source,
			HttpSession session, HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		logger.warn("New trip created by user. Details: tripName - {}, userId - {}, userDevice - {} and type {}  and endDate {}", 
				tripName, userId, request.getHeader("UserDeviceId"), type, endDate );
		
		TimelineTrip trip = new TimelineTrip();
		trip.setTripName(tripName);
		//trip.setTripStartTime(Long.parseLong(timeStamp));
		Long tripId = tripService.createTrip(trip, endDate, source);
		
		if(TripLivedUtil.isTripStatusEnabled(request) && null != userId) {
			tripService.updateTripWithRecordingStatus(tripId);
		}
		//Person person = (Person) session.getAttribute(Constants.LOGIN_USER);
		tripService.createUserTrip(Long.parseLong(userId), tripId);
		String publicTripId = tripService.updateTripPublicId(tripId);
		
		PersonDTO person = userService.getUserDetails(userId);
		
		try {
			if(null != person.getName() && null != person.getEmail()) {
				recordStartMail.sendMail(person.getEmail(), person.getName(), type, endDate);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Recording start Mail: Error while sending recording mailer - {}", e);
		}
		
		Gson gson = new Gson();
		return gson.toJson(Long.parseLong(publicTripId));
	}


	@RequestMapping(method= RequestMethod.GET, value="/trip-end")
    public String startTrip(HttpSession session) {
		Person person = (Person) session.getAttribute(Constants.LOGIN_USER);
		if(person != null) {
			Long tripId = tripService.getTripIdByEmail(person.getPersonId());
	    	tripService.endTrip(tripId, person.getPersonId());
		}
    	return "trip-end";
    }
	
	
	@RequestMapping(method= RequestMethod.POST, value="/submitTimeline")
    public @ResponseBody Boolean submitTrip(Principal principal, final HttpServletResponse response, 
    		@RequestBody TimelineTrip trip,
			HttpSession session, HttpServletRequest request) {
		
		Gson gson = new Gson();
		String timelineTripStr = gson.toJson(trip);
		logger.warn("Trip Json Logged: Request received for submitting tripid - {} and trip -{}", trip.getTripId(), timelineTripStr);
		
		try {
			if(TripLivedUtil.isPublicIdEnabled(request)) {
				String publicId = trip.getTripId();
				Long internalId = tripService.getTripIdByPublicId(Long.parseLong(publicId));
				trip.setTripId(internalId.toString());
			}else {
				//Writing code for trips that have been started with previous versions(before public id). Will remove this after some time
				String tripId = trip.getTripId();
				if(tripId.length() > 6) {
					Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
					trip.setTripId(internalTripId.toString());
				}
			}
			
			String appVersion = request.getHeader("ApplicationVersion");
			logger.warn("Trip Submit: Request received for device -{} for app version - {} for submitting tripid - {} ",request.getHeader("UserDeviceId"),
					appVersion, trip.getTripId());
			
			if(null != appVersion && (Integer.parseInt(appVersion) >= Constants.MIN_SUPPORTED_APP_VERSION))
			{
				logger.warn("Trip Submit: Submitting trip from device - {} ", request.getHeader("UserDeviceId"));
				
				//Check to ensure cover url of the trip does not break
				if(null == trip.getTripCoverUri()) {
					trip.setTripCoverUri("http://www.triplived.com/static/triplived/rdTrPh/trip1.jpg");
				}
				Boolean status = tripService.saveTimelineTrip(trip, request.getHeader("UserDeviceId"));
				logger.warn("Trip Submit Status: Device - {} for submitting tripid - {}",request.getHeader("UserDeviceId"),trip.getTripId());
				if(status) {
					if(TripLivedUtil.isTripStatusEnabled(request)) {
						tripService.updateTripWithFinalizedStatus(Long.parseLong(trip.getTripId()));
					}
					tripService.pushOtherDetails(trip);
					
					try {
						generateImageForShare(trip.getTripId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return status;
				}else{
					logger.warn("Trip Submit: Returning false as status is false for request received for submitting trip - {}", trip.getTripId());
					return false;
				}
				
			}else {
				logger.warn("Trip Submit: Returning false for request received for submitting trip - {}, since issue with appVersion - {}", trip.getTripId(), appVersion);
				return false;//TODO, when code moves to new server
			}
		}catch(Exception e) {
			logger.error("Trip Submit: Error while submitting trip for id - {} - {}",trip.getTripId(),e);
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	@RequestMapping(method= RequestMethod.POST, value="/addEntities/{flag}")
    public @ResponseBody Boolean addEntities( 
    		@RequestBody Long tripId, HttpServletRequest request,@PathVariable Boolean flag) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(tripId);
			tripId = internalTripId;
		}
		Boolean status = tripService.saveEventsFromUserTrip(tripId, request.getHeader("UserDeviceId"),flag);
				return status;
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/addCitiesFromTrip/{flag}")
    public @ResponseBody Boolean addCitiesFromTrip( 
    		@RequestBody Long tripId, HttpServletRequest request,@PathVariable Boolean flag) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(tripId);
			tripId = internalTripId;
		}
		Boolean status = tripService.saveCitiesFromUserTrip(tripId, request.getHeader("UserDeviceId"),flag);
				return status;
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/getTrip")
    public @ResponseBody String getTripById(Principal principal, final HttpServletResponse response, final HttpServletRequest request,
			@RequestParam("tripId") @Valid String tripId, 
			@RequestParam(value = "rId", required = false) @Valid String requestorId,HttpSession session) {
	 
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		logger.warn("Trip View Request: Trip Id - {} and requestorId - {}", tripId, requestorId);
		
		TimelineTrip tripData = null;
		
		if(null == requestorId) {
			tripData = tripService.getTripById(Long.parseLong(tripId), null);
		}else {
			tripData = tripService.getTripById(Long.parseLong(tripId), requestorId);
		}
		
		if(!TripLivedUtil.isPublicIdEnabled(request)) {
			tripData.setTripId(tripId);
		}
		
		//check for application version. if application version is below 13, dont allow edit permission
		Integer appVersion = Integer.parseInt(request.getHeader("ApplicationVersion"));
		Map<String, Object> additionalMap = tripData.getAdditionalProperties();
		if(null != additionalMap) {
			if(null != appVersion && (appVersion < Constants.MIN_SUPPORTED_APP_VERSION_FOR_EDITING_RIGHTS)) {
				if(additionalMap.containsKey("editUrl")) {
					additionalMap.remove("editUrl");	
				}
			}
			/*if(additionalMap.containsKey("trravelAgent")) {
				additionalMap.remove("trravelAgent");	
			}
			if(additionalMap.containsKey("travelAgent")) {
				additionalMap.remove("travelAgent");	
			}
			if(additionalMap.containsKey("travelAgentId")) {
				additionalMap.remove("travelAgentId");	
			}*/
			
		}
		
		
		Gson gson = new Gson();
		return gson.toJson(tripData);
		
		//return tripData;
	}
	
	
	
	@RequestMapping(method= RequestMethod.GET, value="/getTripForWeb")
    public @ResponseBody String getTripForWebById(Principal principal, final HttpServletResponse response, final HttpServletRequest request,
			@RequestParam("tripId") @Valid String tripId, 
			@RequestParam(value = "rId", required = false) @Valid String requestorId,HttpSession session) {
	 
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		logger.warn("Trip View Request: Trip Id - {} and requestorId - {}", tripId, requestorId);
		
		TimelineTrip tripData = null;
		
		if(null == requestorId) {
			tripData = tripService.getTripById(Long.parseLong(tripId), null);
		}else {
			tripData = tripService.getTripById(Long.parseLong(tripId), requestorId);
		}
		
		if(!TripLivedUtil.isPublicIdEnabled(request)) {
			tripData.setTripId(tripId);
		}
		
		//check for application version. if application version is below 13, dont allow edit permission
		Integer appVersion = Integer.parseInt(request.getHeader("ApplicationVersion"));
		Map<String, Object> additionalMap = tripData.getAdditionalProperties();
		if(null != additionalMap) {
			if(null != appVersion && (appVersion < Constants.MIN_SUPPORTED_APP_VERSION_FOR_EDITING_RIGHTS)) {
				if(additionalMap.containsKey("editUrl")) {
					additionalMap.remove("editUrl");	
				}
			}
			
			additionalMap.put("intTripId", tripId);
			/*if(additionalMap.containsKey("trravelAgent")) {
				additionalMap.remove("trravelAgent");	
			}
			if(additionalMap.containsKey("travelAgent")) {
				additionalMap.remove("travelAgent");	
			}
			if(additionalMap.containsKey("travelAgentId")) {
				additionalMap.remove("travelAgentId");	
			}*/
			
		}
		
		//Get all upvotes count for experiences here
		/*for(com.domain.triplived.trip.dto.SubTrip sb : tripData.getSubTrips()) {
			ToCityDTO toCity = sb.getToCityDTO();
			for(Event event : toCity.getEvents()) {
				UpVoteDownVoteStatus status = tripLikeService.getOnlyStatusOnEntity(Long.parseLong(tripId), Long.parseLong(event.getSid()));
				Map<String, Object> map = event.getAdditionalProperties();
				map.put("upvotes", status.getTotalUpvotes());
			}
		}*/
		
		Gson gson = new Gson();
		return gson.toJson(tripData);
		
		//return tripData;
	}
	
	
	
	/**
	 * Added by Santosh for facebook image sharing. Return photo for sharing
	 * 
	 * @param id
	 * @param resp
	 */
	@RequestMapping(value = "/createHeaderImage/{id}", method = RequestMethod.GET)
	public void findImage(@PathVariable("id") String id,
			HttpServletResponse resp,HttpServletRequest request) {

			if(id.length() > 6) {
				Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(id));
				id = internalTripId.toString();
			}
		/**
		 * 0) Check whether there exists an image and if it is a day old 
		 * 1) Call phantom which will hit the "getTripCoverPage/{tripId}" 
		 * 2) Phantom will generate an image 
		 * 3) Pick this image and return;
		 * 
		 */
		try {
			//String command = String.format(phantomCommand, tlDomain+ "trip/getTripCoverPage/" + id, tripPhotoDir + File.separator + id + File.separator+"share-"+id+".jpg");

			String filePath = tripPhotoDir + File.separator + id + File.separator+"share-"+id+".png" ;  

			File f = new File(filePath);
			/*long diff = new Date().getTime() - f.lastModified();
			if (diff > 1 * 24 * 60 * 60 * 1000) {
			//if (diff > 1 * 60 * 1000) {
			    f.delete();
			}*/
			
			
			if (f.exists()) {

				byte[] bytes = FileUtils.readFileToByteArray(f);

				resp.reset();
				resp.setContentType(MediaType.IMAGE_JPEG_VALUE);
				resp.setContentLength((int) f.length());

				final BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(bytes));

				FileCopyUtils.copy(in, resp.getOutputStream());
				resp.flushBuffer();
			}else{

				String cmd[] =  new String[] {phantomCommand, 
						phantomScreenShotCommand, 
						tlDomain+"trip/getTripCoverPage/" + id,
						filePath,
						"600*315"};
				
				ProcessBuilder pb = new ProcessBuilder(cmd);
				pb.redirectErrorStream(true);
				Process process = pb.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null){
				    System.out.println("tasklist: " + line);
				}
				process.waitFor();
				
				
				/*Runtime runtime = Runtime.getRuntime();
			      Process process = runtime.exec(cmd);
			      InputStream is = process.getInputStream();
			      InputStreamReader isr = new InputStreamReader(is);
			      BufferedReader br = new BufferedReader(isr);
			      String line;
			      process.waitFor();
			 
			       
			 
			      while ((line = br.readLine()) != null) {
			        System.out.println(line);
			      }*/
			    
				File f1 = new File(filePath);
				byte[] bytes = FileUtils.readFileToByteArray(f1);

				resp.reset();
				resp.setContentType(MediaType.IMAGE_JPEG_VALUE);
				resp.setContentLength((int) f.length());

				final BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(bytes));

				FileCopyUtils.copy(in, resp.getOutputStream());
				resp.flushBuffer();
				
			}
		} catch (final Exception e) {
			logger.error("Error while generating Image capture  for trip -{} and path - {}", id);
		}
	}
	
	/**
	 * Running in async mode
	 * @param id
	 * @throws Exception
	 */
	private void generateImageForShare(final String id ) throws Exception{ 
		
		logger.warn("Generating share image trip id  {} ", id );
		final String filePath = tripPhotoDir + File.separator + id + File.separator + "share-" + id + ".png";

		File f = new File(filePath);
		
		if(f.exists()) {
			logger.warn("returning the existing file {} ", id );
			return ;
		}
		/*long diff = new Date().getTime() - f.lastModified();
		if (diff > 1 * 24 * 60 * 60 * 1000) {
			logger.warn("deleting old file, file is older then {} trip is {} ", diff, id );
			f.delete();
		}else{
			logger.warn("returning recent share {} ", id );
			return;
		}*/
		
		Callable<String> shareImageGeneration = new Callable<String>() {

			@Override
			public String call() {
				try {
					String cmd[] = new String[] { phantomCommand, phantomScreenShotCommand,
							tlDomain + "trip/getTripCoverPage/" + id, filePath, "600*315" };

					ProcessBuilder pb = new ProcessBuilder(cmd);
					pb.redirectErrorStream(true);
					Process process = pb.start();
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						//System.out.println("tasklist: " + line);
						logger.warn("phantom tasklist {} ", line );
					}
					process.waitFor();
					return filePath;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				return null;
			}
		};
		RetriableTask<String> r = new RetriableTask<String>(shareImageGeneration);
		try {
			r.call();
			logger.warn("async share function called  for trip {} ", id  );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * Added by Santosh for facebook image sharing
     * : only localhost will call this page:
     * @param tripId
     * @return
     */
	@RequestMapping(method= RequestMethod.GET, value="/getTripCoverPage/{tripId}")
    public ModelAndView getTripImageForShare(@PathVariable("tripId") @Valid String tripId, HttpServletRequest request) {
		
		logger.warn("getTripCoverPage  {} ", tripId );
		
		ModelAndView modelAndView  = getTripByIdForShare(tripId, request);
		modelAndView.setViewName("trip/trip-share-image-only");
		
		Set<String> cities = new LinkedHashSet<String>();
		Set<String> hotels = new LinkedHashSet<String>();
		Set<String> attractions = new LinkedHashSet<String>();
		Set<String> activities = new LinkedHashSet<String>();
		Set<String> restaurants = new LinkedHashSet<String>();
		List<String> files = new ArrayList<String>();
		
		TimelineTrip tripData  = (TimelineTrip) modelAndView.getModel().get("trip"); 
		for (com.domain.triplived.trip.dto.SubTrip subTrip : tripData.getSubTrips()) {
			if(StringUtils.isNotEmpty(subTrip.getToCityDTO().getCityType())
					&& "destination".equalsIgnoreCase(subTrip.getToCityDTO().getCityType())) {
				cities.add(subTrip.getToCityDTO().getCityName());
				
				
				if (CollectionUtils.isNotEmpty(subTrip.getToCityDTO().getFiles())) {
					for (com.domain.triplived.trip.dto.File file : subTrip.getToCityDTO().getFiles()) {
						files.add(file.getUrl());
					}
				}
				
				List<Event> listOfEvents = subTrip.getToCityDTO().getEvents();
				
				for (Event event : listOfEvents) {
					
					if(CollectionUtils.isNotEmpty(event.getFiles())){
					  for(com.domain.triplived.trip.dto.File file : event.getFiles()){
						files.add(file.getUrl()) ;
					  }
					}
					if (CollectionUtils.isNotEmpty(event.getUpdates())) {
						for (Update update : event.getUpdates()) {

							if (CollectionUtils.isNotEmpty(update.getFiles())) {
								for (com.domain.triplived.trip.dto.File file : update.getFiles()) {
									files.add(file.getUrl());
								}
							}
						}
					}
					if("hotel".equalsIgnoreCase(event.getType())){
						//if(hotels.size() != 2) {
							hotels.add(event.getName());
						//}
					}
					if("attraction".equalsIgnoreCase(event.getType())){
						
						//if(attractions.size() !=2 ){
							attractions.add(event.getName());
						//}
					}
					
					if("restaurant".equalsIgnoreCase(event.getType())){
						restaurants.add(event.getName());
					}
					
					if("activity".equalsIgnoreCase(event.getType())){
						//if(activities.size() !=2 ){
							activities.add(event.getName());
						//}
					}
				}
			}
		}
		
		StringBuilder summary = new StringBuilder();
		//cities
		if(cities.size() > 0){
			summary.append(cities.size());
			if(cities.size() > 1){
				summary.append(" Cities");
			}else{
				summary.append(" City");
			}
		}
		
		//if(activities.size() == 0 )
		//activities
		if(activities.size() == 0 ) {
			if(attractions.size() !=0){
				summary.append(" and ");
			}
		}else{
			if(attractions.size()>0){
				summary.append(", ");
			}else{
				//summary.append(" and ");
			}
		}
		//attractions
		if(attractions.size() > 0) {
			//summary.append(",");
			summary.append(attractions.size());
			if(attractions.size() > 1){
				summary.append(" Attractions");
			}else{
				summary.append(" Attraction");
			}
		}
		
		if(activities.size() > 0) {
			summary.append(", ").append(activities.size());
			if(activities.size() > 1){
				summary.append(" Activities ");
			}else{
				summary.append(" Activity ");
			}
		}else{
			summary.append(" Visited");
		}
		
		modelAndView.addObject("summary", summary.toString());
		
		modelAndView.addObject("cities", cities);
		modelAndView.addObject("attractions", attractions);
		modelAndView.addObject("hotels", hotels);
		modelAndView.addObject("activities", activities);
		modelAndView.addObject("restaurants", restaurants);
		
		String restaurantString = StringUtils.join(restaurants, ", ");
		if(StringUtils.isNotEmpty(restaurantString) && restaurantString.length() > 45) {
			modelAndView.addObject("restaurantString", restaurantString.substring(0, 45).concat("...")); 
		}else{
			modelAndView.addObject("restaurantString", restaurantString); 
		}
		
		
		String attractionString = StringUtils.join(attractions, ", ");
		if(StringUtils.isNotEmpty(attractionString) && attractionString.length() > 45) {
			modelAndView.addObject("attractionString", attractionString.substring(0, 45).concat("...")); 
		}else{
			modelAndView.addObject("attractionString", attractionString); 
		}
		
		String hotelString = StringUtils.join(hotels, ", ");
		if(StringUtils.isNotEmpty(hotelString) && hotelString.length() > 45) {
			modelAndView.addObject("hotelString", hotelString.substring(0, 45).concat("...")); 
		}else{
			modelAndView.addObject("hotelString", hotelString); 
		}
		
		String activityString = StringUtils.join(activities, ", ");
		if(StringUtils.isNotEmpty(activityString) && activityString.length() > 45) {
			modelAndView.addObject("activityString", activityString.substring(0, 45).concat("...")); 
		}else{
			modelAndView.addObject("activityString", activityString); 
		}
		
		String cityString = StringUtils.join(cities, ", ");
		if(StringUtils.isNotEmpty(cityString) && cityString.length() > 45) {
			modelAndView.addObject("cityString", cityString.substring(0, 45).concat("...")); 
		}else{
			modelAndView.addObject("cityString", cityString); 
		}
		
		if(CollectionUtils.isNotEmpty(files) && files.size() >= 3 ){
			Collections.shuffle(files);
			for (int i = 1; i < 4; i++) {
				modelAndView.addObject("image"+i, files.get(i));
			}
			
			modelAndView.addObject("isLessImages", false);
			modelAndView.addObject("coverPage", tripData.getTripCoverUri());
		}else{
			modelAndView.addObject("coverPage", tripData.getTripCoverUri());
			modelAndView.addObject("isLessImages", true);
		}
		
		return modelAndView;
	}
	
	/**
	 * Added by Santosh for facebook image sharing
	 * @param tripId
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/viewTrip/{tripId}")
    public ModelAndView getTripByIdForShare(@PathVariable("tripId") @Valid String tripId, HttpServletRequest request) {
	 
		if(null == request.getHeader("ApplicationVersion")) {
			if(tripId.length() > 6) {
				Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
				tripId = internalTripId.toString();
			}
		}
		else {
			if(TripLivedUtil.isPublicIdEnabled(request)) {
				Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
				tripId = internalTripId.toString();
			}
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("trip/trip-share");
		try {
		////	generateImageForShare(tripId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TimelineTrip tripData = null;
		
		if(StringUtils.isNotEmpty(tripId)) {
			tripData = tripService.getTripForShare(Long.parseLong(tripId), null);
			modelAndView.addObject("trip", tripData);
		}
		
		return modelAndView;
	}
	
	
	@RequestMapping(method= RequestMethod.POST, value="/tripMonitor/{deviceId}", consumes = "text/plain")
    public String monitorTrip(@RequestBody String trip,@PathVariable("deviceId") String deviceId) {
	
    	//List<AttractionResponseDTO> attractions = staticAttractionService.getAttractionByCity(city);
    	
		//Gson gson = new Gson();
		//model.addAttribute("trip", trip);
		//Lots of logs - logger.warn("The timeline for deviceId : " + deviceId + " - " + trip);
		return null;
		//return gson.toJson(attractions);
    }
	
	@RequestMapping(method= RequestMethod.GET, value="/like")
	public @ResponseBody String triplike(
			HttpServletRequest request,
			@RequestParam("user") @Valid String userId,
			@RequestParam("trip") @Valid String tripId,
			@RequestParam(value = "value", required = false) String like) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip like request for trip - {} from user - {} and deviceId - {}", tripId, userId, deviceId);
		//String like = "true";
		//Long userId = tripService.getUserIdByTripId(Long.parseLong(tripId));
		
		if(null != deviceId && null != userId && null != tripId) {
			//Changes for moderators to be able to classify a trip as good/bad
			/*if(null != like) {
				List<String> moderatorList = Arrays.asList(moderatorIds.split(","));
				if(moderatorList.contains(userId)) {
					miscDataController.addTrendingTripException(tripId, "1", like);
				}
			}*/
			return tripService.getLikeOfUser(Long.parseLong(tripId), Long.parseLong(userId), like);
		}else {
			return null;
		}
		
	}
	
	
	@RequestMapping(method= RequestMethod.GET, value="/share")
	public @ResponseBody String tripShare(
			HttpServletRequest request,
			@RequestParam("user") @Valid String userId,
			@RequestParam("trip") @Valid String tripId) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip share request for trip - {} from user - {} and deviceId - {}", tripId, userId, deviceId);
		if(null != deviceId && null != userId && null != tripId) {
			return tripService.addSharesOfTrip(Long.parseLong(tripId), Long.parseLong(userId));
		}else {
			return null;
		}
		
	}
	
	
	@RequestMapping(method= RequestMethod.POST, value="/comment")
	public @ResponseBody String tripComments(
			HttpServletRequest request,
			@RequestParam("user") @Valid String userId,
			@RequestParam("trip") @Valid String tripId,
			@RequestParam(value = "value") String comment) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip comment request for trip - {} from user - {} and deviceId - {}", tripId, userId, deviceId);
		
		if(null != deviceId && null != userId && null != tripId) {
			return tripService.addCommentsOnTrip(Long.parseLong(tripId), Long.parseLong(userId), comment);
		}else {
			return null;
		}
	}
	
	/**
	 * This method is only used to change status of a comment/deleting a comment. For adding comment look at tripComments method above.
	 * @param request
	 * @param tripId
	 * @param commendId
	 * @param userId
	 * @param comment
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST, value="/updatecomment/{tripId}/{commendId}/{userId}")
	public @ResponseBody Status updateComment(
			HttpServletRequest request,
			@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("commendId") @Valid Long commendId,
			@PathVariable("userId") @Valid Long userId,
			@RequestParam(value = "value") String comment) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(tripId);
			tripId = internalTripId;
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip comment Update request for trip - {} from user - {} and deviceId - {} and mediaId {} and commentId {} ", tripId, userId, deviceId, commendId);
		
		if(null != deviceId && null != userId && null != tripId) {
			return tripCommentService.updateCommentStatus(tripId, userId, commendId, comment, TripElement.TRIP);
		}else {
			return null;
		}
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/get/comments")
	public @ResponseBody String getCommentsOnTrip(
			HttpServletRequest request,
			@RequestParam("trip") @Valid String tripId) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Trip Get comments request for trip -{} and deviceId - {}", tripId, deviceId);
		
		if(null != deviceId && null != tripId) {
			List<TripCommentDTO> tripComments = tripService.getCommentsOnTrip(Long.parseLong(tripId));
			Gson gson = new Gson();
			return gson.toJson(tripComments);
			
		}else {
			return null;
		}
		
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/search")
	public @ResponseBody String searchTrips(
			@RequestParam("id") @Valid String fromCityId) {
	
		List<TripSearchDTO> dtos = tripService.getTripsByDestCity(fromCityId);
		Gson gson = new Gson();
		return gson.toJson(dtos);
	}
	
	
	@RequestMapping(method= RequestMethod.GET, value="/likedTrips")
	public @ResponseBody String getLikedTripsOfUser(
			@RequestParam("id") @Valid String personId, HttpServletRequest request) {
	
		List<TripSearchDTO> likedTripsOfUser = tripService.getLikedTripsOfUser(personId);
		
		if(!CollectionUtils.isEmpty(likedTripsOfUser)) {
			for(TripSearchDTO dto : likedTripsOfUser) {
				if(!TripLivedUtil.isPublicIdEnabled(request)) {
					dto.setTripId(dto.getOldTripId());
				}
				dto.setOldTripId(null);
			}
			Gson gson = new Gson();
			return gson.toJson(likedTripsOfUser);
		}else {
			return null;
		}
	}
	
	/**
	 * This code also gets called from the video server
	 * @param tripId
	 * @param path
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/updateVideoServerPath")
	public @ResponseBody Boolean updateVideoServerPath(@RequestParam("tripId") String tripId,
			@RequestParam("path") String path) {
		
		try {
		Boolean status = tripService.updateVideoServerPath(tripId, path);
		return status;
		}catch(Exception e) {
			logger.error("Error while updating video server path in database for trip -{} and path - {}", tripId, path);
			return false;
		}
		
	}
	
	/**
	 * This code also gets called from the video server
	 * @param tripId
	 * @param path
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/updateVideoYoutubePath")
	public @ResponseBody Boolean updateVideoYoutubePath(@RequestParam("tripId") String tripId,
			@RequestParam("path") String path) {
		
		try {
			Boolean status = tripService.updateVideoYoutubePath(tripId, path);
			return status;
			}catch(Exception e) {
				logger.error("Error while updating video youtube path in database for trip -{} and path - {}", tripId, path);
				return false;
			}
	}
	
	
	

	@RequestMapping(method= RequestMethod.POST, value="/saveTimelineForReview")
    public @ResponseBody Boolean submitTripForReview(Principal principal, final HttpServletResponse response, 
    		@RequestBody TimelineTrip trip,
			HttpSession session, HttpServletRequest request) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			String publicId = trip.getTripId();
			Long internalId = tripService.getTripIdByPublicId(Long.parseLong(publicId));
			trip.setTripId(internalId.toString());
		}
		
		Gson gson = new Gson();
		String timelineTripStr = gson.toJson(trip);
				
		String appVersion = request.getHeader("ApplicationVersion");
		logger.warn("Trip Review Save: Request received for device -{} for app version - {} for saving tripid - {} and trip -{}",request.getHeader("UserDeviceId"),
				appVersion, trip.getTripId(), timelineTripStr);
		
		Integer appVersionVal = Integer.parseInt(appVersion);
		if(null != appVersion && (appVersionVal >= Constants.MIN_SUPPORTED_APP_VERSION))
		{
			logger.warn("Trip Submit: Submitting trip from device - {} ", request.getHeader("UserDeviceId"));
			Boolean status = tripService.saveTimelineTripForReview(trip, request.getHeader("UserDeviceId"));
			return status;
		}
		return false;
	}
	
	
	@RequestMapping(method= RequestMethod.GET, value="/break/reviews")
    public @ResponseBody String breakTripReviews(Principal principal, final HttpServletResponse response, 
    		@RequestParam("tripId") String tripId,
			HttpSession session, HttpServletRequest request) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		try {
			String resp = tripService.addReviewsOfEvents(tripId);
			return resp;
		}catch(Exception e) {
			logger.error("Trip reviews and experiences: Error while breaking trip for reviews for tripId - {}. Exception is - {}", tripId,e);
			return "Unsuccessful";
		}
	}
	
	/**
	 * Like/Unlike a trip Comment
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/likeComment/{tripId}/{commentId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody TripResponse likeTrip(@PathVariable("tripId") @Valid Long tripId,
			@PathVariable("commentId") @Valid String commentId, @PathVariable("userId") @Valid Long userId, @RequestBody TripLikeInfo tripMedia,
			 @RequestHeader("UserDeviceId") String userDevice, @RequestHeader("appVersion") String appVersion, 
			 @RequestHeader("ApplicationVersion") String applicationVersion
			 ) throws Exception {
	 
		logger.warn("user :{}, action:liked, trip_comments:{}, trip :{}  action {}", userId, commentId, tripId,  tripMedia.getUserAction()  );
		
		tripMedia.setId(commentId);
		tripMedia.setUserId(userId);
		tripMedia.setTripId(tripId);
		
		logger.warn("TRIP Comment Like :  {}", new Gson().toJson(tripMedia));
		
		try{
			return  tripLikeService.likeTripComment(tripMedia);
			
		}catch(Exception ex) {
			
			logger.error("Unable to like by user {} and media {}" , userId, commentId); 
			TripResponse response = new TripResponse();
			response.setCode(TripLivedResponseCode.RESPONSE_ERROR);
			response.setMessage("Unable to Like, Please try again!");
			
			return response;
		}
	}
	
	//likedBy/23/1128
	@RequestMapping(value = "/likedBy/{userid}/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PeopleLiked> whoAllLiked(@PathVariable("userid") @Valid String userid,
			@PathVariable("id") @Valid String id, @RequestHeader("UserDeviceId") String userDevice, HttpServletRequest request){
		List<PeopleLiked> whoAllLiked = new ArrayList<>();
		
		logger.warn("user :{}, action:want_photo_likes_of, entity:{} ", userid, id  );
		
		if(StringUtils.isNotEmpty(id)){
			try{
				String tripId = null; 
				if(TripLivedUtil.isPublicIdEnabled(request)) {
					Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(id));
					tripId = internalTripId.toString();
				}
				//Long identifier = Long.parseLong(id);
				if(StringUtils.isNotEmpty(tripId)){
					whoAllLiked = tripLikeService.peopleWhoLiked(Long.parseLong(tripId), tripId, TripElement.TRIP);
				}
				
			}catch(Exception ex) {
				logger.error("exception "+ ex);
				logger.error("Unable to get liked by user for media {} " , id); 
			}
		}
		
		return whoAllLiked ;
	}
	
	@RequestMapping(value = "/likedBy/json/{userid}/{id}", method = RequestMethod.GET)
	public @ResponseBody String whoAllLikedJson(@PathVariable("userid") @Valid String userid,
			@PathVariable("id") @Valid String id, @RequestHeader("UserDeviceId") String userDevice, HttpServletRequest request){
		   
		List<PeopleLiked> list = whoAllLiked(userid, id, userDevice, request);
		PeopleLikedList obj = new PeopleLikedList();
		obj.setList(list);
		if(!CollectionUtils.isEmpty(list)) {
			Gson gson = new Gson();
			return gson.toJson(obj);
		}else {
			return null;
		}
	}
	
	@RequestMapping(value = "/likedComments/{userid}/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PeopleLiked> whoAllLikedComments(@PathVariable("userid") @Valid String userid,
			@PathVariable("id") @Valid String id, @RequestHeader("UserDeviceId") String userDevice, HttpServletRequest request){
		List<PeopleLiked> whoAllLiked = new ArrayList<>();
		
		logger.warn("user :{}, action:want_comment_likes_of, entity:{} ", userid, id  );
		
		if(StringUtils.isNotEmpty(id)){
			try{
				if(TripLivedUtil.isPublicIdEnabled(request)) {
					Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(id));
					id = internalTripId.toString();
				}
				
				//Long identifier = Long.parseLong(id);
				whoAllLiked = tripLikeService.peopleWhoLiked(Long.parseLong(id), id, TripElement.TRIP_COMMENT);
				
			}catch(Exception ex) {
				logger.error("exception "+ ex);
				logger.error("Unable to get liked by user for media {} " , id); 
			}
		}
		return whoAllLiked ;
	}
	
	/**
	 * Log trip deletion api
	 * @param principal
	 * @param response
	 * @param tripId
	 * @param session
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/delete")
    public @ResponseBody String updateTripWithDeleteStatus(Principal principal, final HttpServletRequest request, final HttpServletResponse response, 
			@RequestParam("tripId") @Valid String tripId,
			@RequestParam(value = "rId", required = false) @Valid String requestorId,
			HttpSession session) {
	
		logger.warn("Request for trip deletion for trip - {}", tripId);
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			String publicId = tripId;
			Long internalId = tripService.getTripIdByPublicId(Long.parseLong(publicId));
			tripId = internalId.toString();
		}
		Long userId = tripService.getUserIdByTripId(Long.parseLong(tripId));
		if(TripLivedUtil.isTripStatusEnabled(request) && null != requestorId && null != userId && requestorId.equals(userId.toString())) {
			tripService.updateTripWithDeleteStatus(Long.parseLong(tripId));
		}
		return "true";
   }
	
	/**
	 * Log trip editing api
	 * @param principal
	 * @param response
	 * @param tripId
	 * @param session
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/edit")
    public @ResponseBody String updateTripWithEditStatus(Principal principal, final HttpServletRequest request, final HttpServletResponse response, 
			@RequestParam("id") @Valid String tripId,
			@RequestParam(value = "rId", required = false) @Valid String requestorId,
			HttpSession session) {
	
		logger.warn("Request for trip editing for trip - {}", tripId);
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			String publicId = tripId;
			Long internalId = tripService.getTripIdByPublicId(Long.parseLong(publicId));
			tripId = internalId.toString();
		}
		Long userId = tripService.getUserIdByTripId(Long.parseLong(tripId));
		if(TripLivedUtil.isTripStatusEnabled(request) && null != requestorId && null != userId && requestorId.equals(userId.toString())) {
			tripService.updateTripWithEditStatus(Long.parseLong(tripId));
		}
		return "true";
   }
	
	
	@RequestMapping(method= RequestMethod.POST, value="/sync")
    public @ResponseBody Boolean syncTripTimeline(Principal principal, final HttpServletResponse response, 
    		@RequestBody TimelineTrip trip,
			HttpSession session, HttpServletRequest request) {
		
		try {
				if(TripLivedUtil.isPublicIdEnabled(request)) {
					String publicId = trip.getTripId();
					Long internalId = tripService.getTripIdByPublicId(Long.parseLong(publicId));
					trip.setTripId(internalId.toString());
				}
				Gson gson = new Gson();
				logger.warn("Trip Sync Request for tripId - {}. Json is - {}", trip.getTripId(), gson.toJson(trip));
				
				return true;
			}catch(Exception e) {
				return false;
			}
			
	}
	@RequestMapping(method= RequestMethod.GET, value="/faq")
	public @ResponseBody String getTripFaqList(@RequestParam("id")String tripId, HttpServletRequest request) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		List<TripFaqDb> list = tripService.getTripFaqList(Long.parseLong(tripId));
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/kitlist")
	public @ResponseBody String getTripKitList(@RequestParam("id")String tripId, HttpServletRequest request) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		List<TripKitListDb> list = tripService.getTripKitList(Long.parseLong(tripId));
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	
	@RequestMapping(method= RequestMethod.POST, value="/submitTripSummary")
    public @ResponseBody Boolean submitTripFromWeb(HttpServletRequest request, @RequestBody TripSummaryDTO tripSummary) {
		
		String tripId = tripSummary.getTripId();
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		try {
			tripService.createTripFromWebExperiences(Long.parseLong(tripId), tripSummary);
			String x = "Ayan";	
			return true;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			//return false;
		}
		return null;
	}
	
	/**
	 * Update status from Web - I for inactive
	 * @param tripId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateState", method = RequestMethod.GET)
	public @ResponseBody String updateTripStatusFromWeb(HttpServletRequest request,
			@RequestParam(value = "id") String tripId,
			@RequestParam(value = "status") String status,
			@RequestParam(value = "userId") String userId
			) {
		
		if(TripLivedUtil.isPublicIdEnabled(request)) {
			Long internalTripId = tripService.getTripIdByPublicId(Long.parseLong(tripId));
			tripId = internalTripId.toString();
		}
		
		String response = tripService.updateTripStateFromWeb(tripId, status, userId);
		return response;
	}
	
	
		
}
