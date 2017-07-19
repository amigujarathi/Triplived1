package com.triplived.controller.attraction;

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
import com.connectme.domain.triplived.dto.AttractionResponseDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripCityType;
import com.connectme.domain.triplived.trip.dto.TripEventDTO;
import com.connectme.domain.triplived.trip.dto.TripPhotoDTO;
import com.connectme.domain.triplived.trip.dto.TripReviewDTO;
import com.google.gson.Gson;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.service.trip.TripService;

@Controller
@RequestMapping("/attraction")
public class AttractionController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class );
	
	@Autowired
	private StaticAttractionService staticAttractionService;
	
	/*@Autowired
	private AttractionService attractionService;*/
	
	@Autowired
	private TripService tripService;
	
    @Value("${user-images.attraction.upload.dir}")
    private String imagesUploadDir;
    
    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
    
    
    @RequestMapping(method= RequestMethod.GET, value="/progress")
   	public String index(@RequestParam("tripId") String tripId,
   						@RequestParam("tripId") String cityId,
   						@RequestParam("tripId") String subTripId,
   						HttpSession session, HttpServletRequest request, Model model) {
       		
       		String x = tripService.getTrip(Long.parseLong(tripId));
       		Gson gson = new Gson();
    		Trip trip = gson.fromJson(x, Trip.class);
       	
       		TripCityType[] val = TripCityType.values();
       	    String[] names = new String[val.length];

       	    for (int i = 0; i < val.length; i++) {
       	        names[i] = val[i].name();
       	    }
       	    
       		model.addAttribute("cityTypes",names);
       		model.addAttribute("presentTrip", x);
       		
       		SubTrip currentSubTrip = tripService.getActiveSubTrip(Long.parseLong(tripId), trip, null);
       		TripCityDTO toCity = currentSubTrip.getToCityDTO();
       		model.addAttribute("cityName", toCity.getCityName());
       		
   			return "attraction-parent";
   	}
    
    @RequestMapping(method= RequestMethod.GET, value="/{city}")
    public @ResponseBody String getAttractionsByCity(@PathVariable("city") String city) {
	
    	List<AttractionResponseDTO> attractions = staticAttractionService.suggestAttractionByCity(city, "*");
    	
		Gson gson = new Gson();
		return gson.toJson(attractions);
    }
    
	@RequestMapping(value = "/ajax-post", method = RequestMethod.POST)
	public void uploadImage(Principal principal, final HttpServletResponse response, 

			@RequestParam("tripId") @Valid String tripId,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("attractionId") @Valid Long attractionId,
			@RequestParam("attractionDescription")  @Valid String attractionDescription,
			@RequestParam("subTripId") @Valid Long subTripId,
			@RequestParam("timeStamp")  @Valid String timeStamp,
			@RequestParam("cityname")  @Valid String cityName,
			@RequestParam("attraction_name")  @Valid String attractionName,
			@RequestParam("attractionRating")  @Valid String attractionRating,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			@RequestParam("desc[]") final String desc[],
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		List<TripPhotoDTO> attractionImageList = new ArrayList<TripPhotoDTO>();
		
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
		int counter = 0;
		for (MultipartFile multipartFile : fileToUpload) {
			
			String fileName =  userId + File.separatorChar + tripId + File.separatorChar + ts + File.separatorChar + cityId + File.separatorChar + attractionId + File.separatorChar  + System.currentTimeMillis()+"-"+multipartFile.getOriginalFilename();

			String imageUploadDir = imagesUploadDir +File.separatorChar + fileName;
			// uploading the original image on the file system.
			
			if(desc.length != 0){
				TripPhotoDTO attractionImageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, attractionId, desc[counter]);
				attractionImageList.add(attractionImageDTO);
			}else {
				TripPhotoDTO attractionImageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, attractionId, null);
				attractionImageList.add(attractionImageDTO);
			}
						
			counter++;
		}
		
		
		
		
		TripEventDTO tripAttrDTO = new TripEventDTO("attraction");
		tripAttrDTO.setId(attractionId);
		tripAttrDTO.setCityId(cityId);
		tripAttrDTO.setSubTripId(subTripId);
		tripAttrDTO.setTimestamp(ts);
		tripAttrDTO.setCityName(cityName);
		tripAttrDTO.setEventName(attractionName);
		TripReviewDTO review = new TripReviewDTO();
		review.setReview(attractionDescription);
		List<TripReviewDTO> reviews = new ArrayList<TripReviewDTO>();
		reviews.add(review);
		tripAttrDTO.setFiles(attractionImageList);
		tripAttrDTO.setReviews(reviews);
		

		//Save it into Database
		//attractionService.addAttractionImage(attractionDataUpload);
		//Long tripId = 2L;
		tripService.addAttractionToCity(tripId, tripAttrDTO);
		
		//Return object that will be shown on UI
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), tripAttrDTO);
		
	}
	
	
	@RequestMapping(value = "/ajax-post-wi", method = RequestMethod.POST)
	public void uploadWI(Principal principal, final HttpServletResponse response, 

			@RequestParam("tripId") @Valid String tripId,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("attractionId") @Valid Long attractionId,
			@RequestParam("attractionDescription")  @Valid String attractionDescription,
			@RequestParam("subTripId") @Valid Long subTripId,
			@RequestParam("timeStamp")  @Valid String timeStamp,
			@RequestParam("cityname")  @Valid String cityName,
			@RequestParam("attraction_name")  @Valid String attractionName,
			@RequestParam("attractionRating")  @Valid String attractionRating,
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
		
		
		TripEventDTO tripAttrDTO = new TripEventDTO("attraction");
		tripAttrDTO.setId(attractionId);
		tripAttrDTO.setCityId(cityId);
		tripAttrDTO.setSubTripId(subTripId);
		tripAttrDTO.setTimestamp(ts);
		tripAttrDTO.setCityName(cityName);
		tripAttrDTO.setEventName(attractionName);
		TripReviewDTO review = new TripReviewDTO();
		review.setReview(attractionDescription);
		List<TripReviewDTO> reviews = new ArrayList<TripReviewDTO>();
		reviews.add(review);
		tripAttrDTO.setReviews(reviews);
		

		//Save it into Database
		//attractionService.addAttractionImage(attractionDataUpload);
		//Long tripId = 2L;
		tripService.addAttractionToCity(tripId, tripAttrDTO);
		
		
	}


	private TripPhotoDTO uploadImageOnDisk(final MultipartFile fileToUpload, final String destinationPath, String cityId, Long attractionId, String desc) {

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
		attractionImageDTO.setUrl(destinationPath);
		attractionImageDTO.setName(saveImage.getName());
		attractionImageDTO.setSize(100);
		attractionImageDTO.setThumbnailUrl(destinationPath);
		attractionImageDTO.setType("image/jpeg");
		attractionImageDTO.setDesc(desc);
		
		return attractionImageDTO;
	}
	
}
