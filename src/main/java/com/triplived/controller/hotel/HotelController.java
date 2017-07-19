package com.triplived.controller.hotel;

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
import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.AttractionImageDTO;
import com.connectme.domain.triplived.dto.HotelResponseDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripCityType;
import com.connectme.domain.triplived.trip.dto.TripEventDTO;
import com.connectme.domain.triplived.trip.dto.TripHotelDTO;
import com.connectme.domain.triplived.trip.dto.TripPhotoDTO;
import com.connectme.domain.triplived.trip.dto.TripReviewDTO;
import com.google.gson.Gson;
import com.triplived.service.staticContent.StaticHotelService;
import com.triplived.service.trip.TripService;

@Controller
@RequestMapping("/hotel")
public class HotelController {

	private static final Logger logger = LoggerFactory.getLogger(HotelController.class );
	
	 @Autowired
	 private StaticHotelService staticHotelService;
	 
	 @Autowired
	 private TripService tripService;
		
    @Value("${user-images.hotel.upload.dir}")
    private String userImagesUploadDir;
    
    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
	 
	@RequestMapping(method= RequestMethod.GET, value="/{city}")
    public @ResponseBody String getAttractionsByCity(@PathVariable("city") String city, HttpSession session, Model model, HttpServletRequest request) {
	
    	List<HotelResponseDTO> hotels = staticHotelService.getHotelByCity(city);
		Gson gson = new Gson();
		return gson.toJson(hotels);
    }
	
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
	       		
	   			return "hotel-parent";
	   	}
		
		
		@RequestMapping(value = "/ajax-post", method = RequestMethod.POST)
		public void uploadImage(Principal principal, final HttpServletResponse response, 

				@RequestParam("tripId") @Valid String tripId,
				@RequestParam("cityId") @Valid String cityId,
				@RequestParam("hotelId") @Valid Long hotelId,
				@RequestParam("hotelDescription")  @Valid String hotelDescription,
				@RequestParam("hotelRating")  @Valid String hotelRating,
				@RequestParam("subTripId") @Valid Long subTripId,
				@RequestParam("timeStamp")  @Valid String timeStamp,
				@RequestParam("cityname")  @Valid String cityName,
				@RequestParam("hotel_name")  @Valid String hotelName,
				@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
				@RequestParam("desc[]") final String desc[],
				HttpSession session) throws JsonGenerationException,
				JsonMappingException, IOException {
			
			List<TripPhotoDTO> imagesList = new ArrayList<TripPhotoDTO>();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date date = null;
			try {
				date = formatter.parse(timeStamp);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Long ts = date.getTime() / 1000;
			
			String userId = "x@x.com";
			int counter = 0;
			
			for (MultipartFile multipartFile : fileToUpload) {
				
				String fileName =  userId + File.separatorChar + tripId + File.separatorChar + ts + File.separatorChar + cityId + File.separatorChar + hotelId + File.separatorChar  + ts+"-"+multipartFile.getOriginalFilename();

				String imageUploadDir = userImagesUploadDir +File.separatorChar + fileName;
				// uploading the original image on the file system.
				if(desc.length != 0){
					TripPhotoDTO attractionImageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, hotelId, desc[counter]);
					imagesList.add(attractionImageDTO);
				}else {
					TripPhotoDTO attractionImageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, hotelId, null);
					imagesList.add(attractionImageDTO);
				}

				
				counter++;
			}
			
			
			
			TripEventDTO tripHtlDTO = new TripEventDTO("hotel");
			tripHtlDTO.setId(hotelId);
			tripHtlDTO.setCityId(cityId);
			tripHtlDTO.setSubTripId(subTripId);
			tripHtlDTO.setTimestamp(ts);
			tripHtlDTO.setCityName(cityName);
			tripHtlDTO.setEventName(hotelName);
			tripHtlDTO.setFiles(imagesList);
			
			TripReviewDTO review = new TripReviewDTO();
			review.setReview(hotelDescription);
			List<TripReviewDTO> reviews = new ArrayList<TripReviewDTO>();
			reviews.add(review);
			tripHtlDTO.setReviews(reviews);
			
			tripService.addHotelToCity(tripId, tripHtlDTO);
			
			//Return object that will be shown on UI
			AttractionDataUploadDTO attractionDataUpload = new AttractionDataUploadDTO();
			attractionDataUpload.setCityId(cityId);
			//attractionDataUpload.setFiles(imagesList);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), tripHtlDTO);
			
		}
		
		
		@RequestMapping(value = "/ajax-post-wi", method = RequestMethod.POST)
		public void uploadWI(Principal principal, final HttpServletResponse response, 

				@RequestParam("tripId") @Valid String tripId,
				@RequestParam("cityId") @Valid String cityId,
				@RequestParam("hotelId") @Valid Long hotelId,
				@RequestParam("hotelDescription")  @Valid String hotelDescription,
				@RequestParam("hotelRating")  @Valid String hotelRating,
				@RequestParam("subTripId") @Valid Long subTripId,
				@RequestParam("timeStamp")  @Valid String timeStamp,
				@RequestParam("cityname")  @Valid String cityName,
				@RequestParam("hotel_name")  @Valid String hotelName,
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
			
			TripEventDTO tripHtlDTO = new TripEventDTO("hotel");
			tripHtlDTO.setId(hotelId);
			tripHtlDTO.setCityId(cityId);
			tripHtlDTO.setSubTripId(subTripId);
			tripHtlDTO.setTimestamp(ts);
			tripHtlDTO.setCityName(cityName);
			tripHtlDTO.setEventName(hotelName);
			
			TripReviewDTO review = new TripReviewDTO();
			review.setReview(hotelDescription);
			List<TripReviewDTO> reviews = new ArrayList<TripReviewDTO>();
			reviews.add(review);
			tripHtlDTO.setReviews(reviews);
			
			tripService.addHotelToCity(tripId, tripHtlDTO);
			
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

		
		private AttractionImageDTO imagesForResponse(final MultipartFile fileToUpload, final String destinationPath, String cityId, String attractionId, Integer i) {

			AttractionImageDTO attractionImageDTO = new AttractionImageDTO();
			attractionImageDTO.setUrl(imageReferBaseUrl+cityId +"/"+attractionId+"/"+i.toString());
			attractionImageDTO.setName(i.toString());
			attractionImageDTO.setSize(100);
			attractionImageDTO.setThumbnailUrl(attractionImageDTO.getUrl());
			attractionImageDTO.setType("image/jpeg");
			
			return attractionImageDTO;
		}
}
