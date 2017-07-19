package com.triplived.controller.attraction;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.solr.client.solrj.SolrServerException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.UserResponse;
import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.AttractionImageDTO;
import com.connectme.domain.triplived.dto.ImageResponseDTO;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.attraction.AttractionService;
import com.triplived.service.image.ImageResizeService;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/attraction/upload")
@SessionAttributes(Constants.LOGIN_USER)
public class AttractionDataUploadController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class );
	
	@Autowired
	private StaticAttractionService staticAttractionService;
	
	@Autowired
	private AttractionService attractionService;

    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
    
    @Value("${images.attraction.url.dir}")
    private String imageAttractionUrl; 
    
    @Autowired
	private StaticContent staticContent;
    
    @Autowired
    private ImageResizeService imgResizeServer;
    
    
    @RequestMapping(method= RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
			return "attraction";
	}
    
	@RequestMapping(value = "/ajax-post-data", method = RequestMethod.POST)
	@ResponseBody
	public AttractionDataUploadDTO uploadDataOnly(Principal principal,
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("attractionId") @Valid Long attractionId,
			@RequestParam("latitude") @Valid Double latitude,
			@RequestParam("longitude") @Valid Double longitude,
			@RequestParam("attractionDescription")  @Valid String attractionDescription,
			@RequestParam("cityDescription")  @Valid String cityDescription,
			@RequestParam("attractionPunchline")  @Valid String attractionPunchLine,
			@RequestParam("googlePlaceid")  @Valid String googlePlaceid,
			@RequestParam("googlePlaceName")  @Valid String googlePlaceName,
			@RequestParam("placeTiming")  @Valid String placeTime,
			@RequestParam("placeAddress")  @Valid String placeAddress,
			@RequestParam("bestTime")  @Valid String bestTime,
			@RequestParam("reqTime")  @Valid String reqTime,
			@RequestParam("phone")  @Valid String phone,
			@RequestParam("webSite")  @Valid String webSite,
			@RequestParam("ticket")  @Valid String ticket
			) throws JsonGenerationException ,JsonMappingException, IOException{
		
			if(user != null){
				
				logger.warn("IAttraction data upload by user {} for attraction {} ", user.getPersonId(), attractionId);
				
				try {
					UserResponse userResponse = staticContent.getUserDetails(user.getPersonId().toString());
					if(null != userResponse.getRole()) {
						if(!userResponse.getRole().equalsIgnoreCase("Curator")) {
							return null;
						}
					}else {
						return null;
					}
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return getAttractionUploadDTO(user, cityId, attractionId, latitude,
						longitude, attractionDescription, cityDescription,
						attractionPunchLine, googlePlaceid, googlePlaceName, null, placeTime, placeAddress, bestTime, reqTime,
						phone, webSite, ticket);
			}
		return null;
 
	}

	private AttractionDataUploadDTO getAttractionUploadDTO(Person user, String cityId,
			Long attractionId, Double latitude, Double longitude,
			String attractionDescription, String cityDescription,
			String attractionPunchLine, String googlePlaceid, String googlePlaceName, List<AttractionImageDTO> attractionImageList,
			String timing, String address, String bestTime, String reqTime, String phone, String webSite, String ticket) {
		AttractionDataUploadDTO atttractionDataUpload = new AttractionDataUploadDTO();
		atttractionDataUpload.setFiles(Collections.<AttractionImageDTO> emptyList());
		atttractionDataUpload.setAttractionId(attractionId);
		atttractionDataUpload.setLatitude(latitude);
		atttractionDataUpload.setLongitude(longitude);
		atttractionDataUpload.setAttractionDescription(attractionDescription);
		atttractionDataUpload.setAttractionPunchLine(attractionPunchLine);
		atttractionDataUpload.setCityDescription(cityDescription);
		atttractionDataUpload.setCityCode(cityId);
		if(null != user) {
			atttractionDataUpload.setCreatedBy(user.getPersonId()+"");
		}
		
		atttractionDataUpload.setGooglePlaceId(googlePlaceid);
		atttractionDataUpload.setGoogleplaceName(googlePlaceName);
		atttractionDataUpload.setAttractionAddress(address);
		atttractionDataUpload.setAttractionTiming(timing);
		
		atttractionDataUpload.setBestTime(bestTime);
		atttractionDataUpload.setReqTime(reqTime);
		
		atttractionDataUpload.setFiles(attractionImageList);
		
		atttractionDataUpload.setPhone(phone);
		atttractionDataUpload.setWebSite(webSite);
		atttractionDataUpload.setTicket(ticket);
		
		//Save it into Database
		attractionService.addAttractionImage(atttractionDataUpload);
		
		return atttractionDataUpload;
	}
			
	@RequestMapping(value = "/ajax-post", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImage(Principal principal,
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestParam("fileFrom") String[] fileFrom,
			@RequestParam("fileAuthor") String[] fileAuthor,
			@RequestParam("fileTitle") String[] fileTitle,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("attractionId") @Valid Long attractionId,
			@RequestParam(value = "latitude", required = false) @Valid Double latitude,
			@RequestParam(value = "longitude", required = false) @Valid Double longitude,
			@RequestParam(value = "attractionDescription", required = false)  @Valid String attractionDescription,
			@RequestParam(value = "cityDescription", required = false)  @Valid String cityDescription,
			@RequestParam(value = "attractionPunchline", required = false)  @Valid String attractionPunchLine,
			@RequestParam("googleplaceid1")  @Valid String googlePlaceid,
			@RequestParam(value = "googleplacename1", required = false)  @Valid String googlePlaceName,
			@RequestParam(value = "placeTimeid1", required = false)  @Valid String placeTime,
			@RequestParam(value = "placeAddressid1", required = false)  @Valid String placeAddress,
			@RequestParam(value = "bestTimeid1", required = false)  @Valid String bestTime,
			@RequestParam(value = "reqTimeid1", required = false)  @Valid String reqTime,
			@RequestParam("phone")  @Valid String phone,
			@RequestParam("webSite")  @Valid String webSite,
			@RequestParam("ticket")  @Valid String ticket,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		if(user == null){
			
			return null;
		}
		logger.warn("Attraction and image upload for attraction {} ", attractionId);
		
		try {
			UserResponse userResponse = staticContent.getUserDetails(user.getPersonId().toString());
			if(null != userResponse.getRole()) {
				if(!userResponse.getRole().equalsIgnoreCase("Curator")) {
					return null;
				}
			}else {
				return null;
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String extension = ".jpg";
		List<AttractionImageDTO> images = new ArrayList<AttractionImageDTO>();
		
		if(fileToUpload != null){
			int index = 0;
			for (MultipartFile multipartFile : fileToUpload) {
				AttractionImageDTO imageResponseDTO = new AttractionImageDTO();
				

				String id = attractionId + "-" + System.currentTimeMillis();
				String filePath =  imageReferBaseUrl + File.separatorChar + id + extension;
				String urlPath = imageAttractionUrl + File.separatorChar + id + "m" + extension;
				
				imageResponseDTO.setUrl(urlPath);
				// uploading the original image on the file system.
				uploadImageOnDisk(multipartFile, filePath);
				
				imageResponseDTO.setName(id);
				imageResponseDTO.setFiletitle(id);
				if(null != fileAuthor) {
					imageResponseDTO.setFileAuthor(fileAuthor[0]);
				}
				if(null != fileFrom) {
					imageResponseDTO.setFileFrom(fileFrom[0]);
				}
				imageResponseDTO.setUpdatedBy(user.getPersonId().toString());
				
				images.add(imageResponseDTO);
				
				index ++;
				
				//create thumbnails for timeline images
				if(null != extension && extension.equalsIgnoreCase(".jpg")) {
					//String thumbFilePath = imageThumbnailUploadDir + File.separatorChar + cityCode + 'c' + extension;
					String mobileFilePath = imageReferBaseUrl + File.separatorChar +  id + "m" + extension;
					
					try {
						//imgResizeServer.resizeImages(filePath, thumbFilePath, 100);
						imgResizeServer.resizeImages(filePath, mobileFilePath, 500);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error("Error while generationg mobile size image for attractionId - {}", attractionId);
						e.printStackTrace();
					}
				}
			}
	
		}
				
		AttractionDataUploadDTO obj = getAttractionUploadDTO(null, cityId, attractionId, latitude,
				longitude, attractionDescription, cityDescription,
				attractionPunchLine, googlePlaceid, googlePlaceName, images, placeTime, placeAddress, bestTime, reqTime, phone, webSite, ticket);
		
		Gson gson = new Gson();
		return gson.toJson("true");
		
	}
	
	@RequestMapping(value = "/delete/{name}/{attractionId}", method = RequestMethod.DELETE)
    public @ResponseBody Integer delete(@ModelAttribute(Constants.LOGIN_USER) Person user, @PathVariable String name, @PathVariable String attractionId) {
		
		if(user == null ){
			return -1 ;
		}
        Integer totalUpdates = attractionService.inActivateImage(name, attractionId);
		logger.warn("data deleted by user user {} for attraction image id  {} ", user.getId(), attractionId);
        return totalUpdates;
    }
	
	@RequestMapping(value = "/inactivateAttraction/{attractionId}", method = RequestMethod.DELETE)
    public @ResponseBody Integer delete(@ModelAttribute(Constants.LOGIN_USER) Person user, @PathVariable String attractionId) {
		
		if(user == null ){
			return -1 ;
		}
        Integer totalUpdates = attractionService.inActivateAttraction(attractionId, user.getId()+"");
		logger.warn("attraction id {} inactivated by  user {}  ",attractionId, user.getId());
        return totalUpdates;
    }

	private void uploadImageOnDisk(final MultipartFile fileToUpload, final String destinationPath) {

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
	}
	
}
