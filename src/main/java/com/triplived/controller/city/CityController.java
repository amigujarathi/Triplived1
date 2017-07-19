package com.triplived.controller.city;

import java.io.File;
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

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.dto.AttractionImageDTO;
import com.connectme.domain.triplived.dto.AttractionResponseDTO;
import com.connectme.domain.triplived.dto.TimelineCityInfo;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripCityType;
import com.connectme.domain.triplived.trip.dto.TripPhotoDTO;
import com.connectme.domain.triplived.trip.dto.TripReviewDTO;
import com.google.gson.Gson;
import com.triplived.service.image.ImageResizeService;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.service.trip.TripService;

@Controller
@RequestMapping("/city")
public class CityController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class );
	
	@Autowired
	private StaticAttractionService staticAttractionService;
	
	@Autowired
	private TripService tripService;
	
    @Value("${user-images.city.upload.dir}")
    private String imagesUploadDir;
    
    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
    
    @Value("${images.city.thumbnails.upload.dir}")
    private String imageThumbnailUploadDir;
    
    @Value("${images.city.upload.dir}")
    private String cityImageUploadDir;
    
    @Value("${images.city.temp.upload.dir}")
    private String cityTempImageUploadDir;
    
    
    @Autowired
    private ImageResizeService imgResizeServer;
    
    @RequestMapping(method= RequestMethod.GET, value="/progress")
	public String index(@RequestParam("tripId") String tripId,
			@RequestParam(value = "status", required = false) String status,
			HttpSession session, HttpServletRequest request, Model model, HttpServletResponse response) {
    		
    		String x = tripService.getTrip(Long.parseLong(tripId));
    		
    		Gson gson = new Gson();
    		Trip trip = gson.fromJson(x, Trip.class);
    		
    		SubTrip currentSubTrip = tripService.getActiveSubTrip(Long.parseLong(tripId), trip, status);
    		
    		if(null != status && status.equalsIgnoreCase("new")) {
    			List<SubTrip> subTrips = trip.getSubTrips();
    			SubTrip lastActiveSubTrip = null;
    			if(!CollectionUtils.isEmpty(subTrips)) {
    				lastActiveSubTrip = subTrips.get(subTrips.size() - 1);
    				try {
						updateTripCity(tripId,lastActiveSubTrip.getToCityDTO().getCityId(),"Source","",null,lastActiveSubTrip.getToCityDTO().getCityName());
    				}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			try {
					response.sendRedirect("progress?tripId="+tripId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		if(currentSubTrip != null) {
    			model.addAttribute("subTripId", currentSubTrip.getId());
    			TripCityDTO fromCity = currentSubTrip.getFromCityDTO();
    			TripCityDTO toCity = currentSubTrip.getToCityDTO();
    			
    			if(fromCity == null) {
    				model.addAttribute("cityType", TripCityType.Source.toString());
    			}
    			if(fromCity != null && toCity == null) {
    				model.addAttribute("cityType", TripCityType.Destination.toString());
    			}
    			if(fromCity != null && toCity != null) {
    				//edit
    				//for now, redirecting it to the next page i.e Hotels page
    				//http://www.interestify.com/hotel/progress?tripId=22&cityId=g304554&subTripId=1428747258403
    				String redirectUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/hotel/progress?tripId="+tripId+"&cityId="+currentSubTrip.getToCityDTO().getCityId()+"&subTripId="+currentSubTrip.getId();
    				return "redirect:" + redirectUrl;
    			}
    		}else {
    			model.addAttribute("cityType", TripCityType.Source.toString());
    		}
    	
    		TripCityType[] val = TripCityType.values();
    	    String[] names = new String[val.length];

    	    for (int i = 0; i < val.length; i++) {
    	        names[i] = val[i].name();
    	    }
    	    
    		model.addAttribute("cityTypes",names);
    		model.addAttribute("presentTrip", x);
			return "city-parent";
	}
    
    @RequestMapping(method= RequestMethod.GET, value="/{city}")
    public @ResponseBody String getAttractionsByCity(@PathVariable("city") String city) {
	
    	List<AttractionResponseDTO> attractions = staticAttractionService.suggestAttractionByCity(city, "*" );
    	
		Gson gson = new Gson();
		return gson.toJson(attractions);
    }
    
	@RequestMapping(value = "/ajax-post", method = RequestMethod.POST)
	public void uploadImage(Principal principal, final HttpServletResponse response, 
			@RequestParam("tripId") @Valid String tripId,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("cityType") @Valid String cityType,
			@RequestParam("cityDescription")  @Valid String cityDescription,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			@RequestParam("transportType")  @Valid String transportType,
			@RequestParam("cityName")  @Valid String cityName,
			@RequestParam("timeStamp")  @Valid String timeStamp,
			@RequestParam("desc[]") final String desc[],
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		
		//String tripPath = tripService.getTrip(tripId);
		String userId = "x@x.com";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = null;
		try {
			date = formatter.parse(timeStamp);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Long ts = date.getTime() / 1000;
		
		List<TripPhotoDTO> imageList = new ArrayList<TripPhotoDTO>();
		
		Integer counter = 0;
		
		for (MultipartFile multipartFile : fileToUpload) {
			
			String fileName =  userId + File.separatorChar + tripId + File.separatorChar + ts + File.separatorChar + cityId + File.separatorChar + cityId + File.separatorChar  + System.currentTimeMillis()+"-"+multipartFile.getOriginalFilename();

			String imageUploadDir = imagesUploadDir +File.separatorChar + fileName;
			// uploading the original image on the file system.
			if(desc.length != 0){
				TripPhotoDTO imageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, cityId, desc[counter]);
				imageList.add(imageDTO);
			}else{
				TripPhotoDTO imageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, cityId, null);
				imageList.add(imageDTO);
			}
			
			counter++;
			
		}
		
		TripCityDTO cityDTO = new TripCityDTO();
		cityDTO.setCityName(cityName);
		cityDTO.setCityId(cityId);
		cityDTO.setFiles(imageList);
		if(cityType.equals(TripCityType.Source.toString())) {
			cityDTO.setCityType(TripCityType.Source);
		}
		if(cityType.equals(TripCityType.StopOver.toString())) {
			cityDTO.setCityType(TripCityType.StopOver);
		}
		if(cityType.equals(TripCityType.Destination.toString())) {
			cityDTO.setCityType(TripCityType.Destination);
		}
		
		TripReviewDTO review = new TripReviewDTO();
		review.setReview(cityDescription);
		List<TripReviewDTO> reviews = new ArrayList<TripReviewDTO>();
		reviews.add(review);
		cityDTO.setReviews(reviews);
		cityDTO.setTimestamp(ts);
		
		tripService.updateTripCity(Long.parseLong(tripId),null, cityDTO,transportType);
		
		//Save it into Database
		//attractionService.addAttractionImage(atttractionDataUpload);
		
		//Return object that will be shown on UI
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), cityDTO);
	
		
	}

	@RequestMapping(value = "/ajax-post-wi", method = RequestMethod.POST)
	public void uploadWI(Principal principal, final HttpServletResponse response, 
			@RequestParam("tripId") @Valid String tripId,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("cityType") @Valid String cityType,
			@RequestParam("cityDescription")  @Valid String cityDescription,
			@RequestParam("transportType")  @Valid String transportType,
			@RequestParam("cityName")  @Valid String cityName,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		updateTripCity(tripId, cityId, cityType, cityDescription, transportType, cityName);
	}
	
	private void updateTripCity(String tripId, String cityId, String cityType, String cityDescription, String transportType, String cityName) {
		TripCityDTO cityDTO = new TripCityDTO();
		cityDTO.setCityId(cityId);
		cityDTO.setCityName(cityName);
		
		if(cityType.equals(TripCityType.Source.toString())) {
			cityDTO.setCityType(TripCityType.Source);
		}
		if(cityType.equals(TripCityType.StopOver.toString())) {
			cityDTO.setCityType(TripCityType.StopOver);
		}
		if(cityType.equals(TripCityType.Destination.toString())) {
			cityDTO.setCityType(TripCityType.Destination);
		}
		
		TripReviewDTO review = new TripReviewDTO();
		review.setReview(cityDescription);
		List<TripReviewDTO> reviews = new ArrayList<TripReviewDTO>();
		reviews.add(review);
		cityDTO.setReviews(reviews);
	
		
		
		
		
		tripService.updateTripCity(Long.parseLong(tripId),null, cityDTO, transportType);
		
	}

	private TripPhotoDTO uploadImageOnDisk(final MultipartFile fileToUpload, final String destinationPath, String cityId, String attractionId, String desc) {

		File saveImage = new File(destinationPath);
		File saveImageDir = saveImage.getParentFile();
		if (!saveImageDir.exists()) {
			logger.warn("Image does not exits, create directory first");
			saveImageDir.mkdirs();
		}

		try {
			if (saveImage.exists()) {
				logger.warn("Image exits, Deleting image");
				saveImage.delete();
				logger.warn("Image Deleted");
			}
			fileToUpload.transferTo(saveImage);
		} catch (IOException ex) {
			logger.error("Error occured while saving image at path   "
					+ destinationPath, ex);
		}
		
		TripPhotoDTO attractionImageDTO = new TripPhotoDTO();
		attractionImageDTO.setUrl(imageReferBaseUrl+cityId +"/"+attractionId+"/"+saveImage.getName());
		attractionImageDTO.setName(saveImage.getName());
		attractionImageDTO.setSize(100);
		attractionImageDTO.setThumbnailUrl(attractionImageDTO.getUrl());
		attractionImageDTO.setType("image/jpeg");
		attractionImageDTO.setDesc(desc);
		
		return attractionImageDTO;
	}
	
	private AttractionImageDTO imagesForResponse(final MultipartFile fileToUpload, final String destinationPath, String cityId, String attractionId, Integer i) {

		AttractionImageDTO attractionImageDTO = new AttractionImageDTO();
		attractionImageDTO.setUrl(imageReferBaseUrl+cityId +"/"+attractionId+"/"+i.toString());
		attractionImageDTO.setName(i.toString());
		attractionImageDTO.setSize(100);
		attractionImageDTO.setThumbnailUrl(attractionImageDTO.getUrl());
		attractionImageDTO.setType("image/jpeg");
		
		return attractionImageDTO;
	}
	
	
	/*@RequestMapping(method= RequestMethod.GET, value="/cityImageUploadPage")
	public String cityImageUpload(HttpSession session, HttpServletRequest request, Model model, HttpServletResponse response) {
    		
    	
			return "city-image-upload";
	}*/

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public void uploadCityImage(Principal principal, final HttpServletResponse response, 
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("cityName") @Valid String cityName,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		
		
		for (MultipartFile multipartFile : fileToUpload) {
			
			String tempFilePath = cityTempImageUploadDir + File.separatorChar + cityId + ".jpg";// + "-"+multipartFile.getOriginalFilename();
			String filePath =  cityImageUploadDir + File.separatorChar + cityId + ".jpg";// + "-"+multipartFile.getOriginalFilename();
			String thumbFilePath =  imageThumbnailUploadDir + File.separatorChar + cityId + ".jpg";// + "-"+multipartFile.getOriginalFilename();

			// uploading the original image on the file system.
			TripPhotoDTO imageDTO  = uploadImageOnDisk(multipartFile, tempFilePath, cityId, cityId, null);
			
			Boolean statusOfMainImage = imgResizeServer.resizeImages(tempFilePath, filePath, 500);
			if(!statusOfMainImage) {
				logger.error("Main image could not be uploaded for cityId - {} and cityName - {}");
			}
			Boolean statusOfThumbnailImage = imgResizeServer.resizeImages(tempFilePath, thumbFilePath, 100);
			if(!statusOfThumbnailImage) {
				logger.error("Thumbnail image could not be uploaded for cityId - {} and cityName - {}");
			}
			
			String x = "ayan";	
			//counter++;
			
		}
	}
	
	
	/**
	 * This method returns destination city specific info for timeline purpose.
	 * Currently, only city Image path and city specific attractions are being passed
	 * @param request
	 * @param cityCode
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/timelineInfo")
	public @ResponseBody String getDestinationCityInfo(
			HttpServletRequest request,
			@RequestParam(value = "city") String cityCode) {
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Destination Info requested for city - {} from deviceId - {}", cityCode, deviceId);
		
		List<AttractionResponseDTO> popularAttractions = staticAttractionService.getPopularAttractionsOfCity(cityCode);
		TimelineCityInfo city = new TimelineCityInfo();
		city.setPopularAttractions(popularAttractions);
		
		Gson gson = new Gson();
		return gson.toJson(city);
		
	}

	
}
