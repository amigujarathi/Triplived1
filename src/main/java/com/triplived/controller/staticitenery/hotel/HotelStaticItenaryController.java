package com.triplived.controller.staticitenery.hotel;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.AttractionImageDTO;
import com.triplived.service.attraction.AttractionService;

/**
 * 
 * @author Santosh Joshi 
 *
 */
@Controller
@RequestMapping("/hotelStaticItenary")
public class HotelStaticItenaryController {

	private static final Logger logger = LoggerFactory.getLogger(HotelStaticItenaryController.class );
	
	
	@Autowired
	private AttractionService attractionService;
	
    @Value("${images.upload.dir}")
    private String imagesUploadDir;
    
    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
    
    
    @RequestMapping(method= RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
			return "hotelStaticItenary";
	}
    
	@RequestMapping(value = "/ajax-post", method = RequestMethod.POST)
	public void uploadImage(Principal principal, final HttpServletResponse response, 

			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("attractionId") @Valid Long attractionId,
			@RequestParam("latitude") @Valid Double latitude,
			@RequestParam("longitude") @Valid Double longitude,
			@RequestParam("attractionDescription")  @Valid String attractionDescription,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		List<AttractionImageDTO> attractionImageList = new ArrayList<AttractionImageDTO>();
		
		for (MultipartFile multipartFile : fileToUpload) {
			
			String fileName =  cityId + File.separatorChar + attractionId + File.separatorChar  + System.currentTimeMillis()+"-"+multipartFile.getOriginalFilename();

			String imageUploadDir = imagesUploadDir +File.separatorChar + fileName;
			// uploading the original image on the file system.
			AttractionImageDTO attractionImageDTO  = uploadImageOnDisk(multipartFile, imageUploadDir, cityId, attractionId);

			attractionImageList.add(attractionImageDTO);
		}
		
		AttractionDataUploadDTO atttractionDataUpload = new AttractionDataUploadDTO();
		atttractionDataUpload.setFiles(attractionImageList);
		atttractionDataUpload.setAttractionId(attractionId);
		atttractionDataUpload.setLatitude(latitude);
		atttractionDataUpload.setLongitude(longitude);
		atttractionDataUpload.setAttractionDescription(attractionDescription);

		//Save it into Database
		attractionService.addAttractionImage(atttractionDataUpload);
		
		//Return object that will be shown on UI
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), atttractionDataUpload);
		
	}

	private AttractionImageDTO uploadImageOnDisk(final MultipartFile fileToUpload, final String destinationPath, String cityId, Long attractionId) {

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
		
		AttractionImageDTO attractionImageDTO = new AttractionImageDTO();
		attractionImageDTO.setUrl(imageReferBaseUrl+cityId +"/"+attractionId+"/"+saveImage.getName());
		attractionImageDTO.setName(saveImage.getName());
		attractionImageDTO.setSize(100);
		attractionImageDTO.setThumbnailUrl(attractionImageDTO.getUrl());
		attractionImageDTO.setType("image/jpeg");
		
		return attractionImageDTO;
	}
}
