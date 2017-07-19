package com.triplived.controller.hotel;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
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
import com.connectme.domain.triplived.dto.CategoryResponseDTO;
import com.connectme.domain.triplived.dto.ImageResponseDTO;
import com.connectme.domain.triplived.dto.NewAttractionDataAddDTO;
import com.connectme.domain.triplived.dto.NewOrUpdateHotelDetailsDTO;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.triplived.controller.profile.Person;
import com.triplived.dao.category.CategoryDao;
import com.triplived.entity.CategoryDb;
import com.triplived.entity.HotelDb;
import com.triplived.entity.TripDb;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.attraction.AttractionService;
import com.triplived.service.hotel.AddUpdateHotelService;
import com.triplived.service.image.ImageResizeService;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/hotel/add")
@SessionAttributes(Constants.LOGIN_USER)
public class AddOrUpdateHotelDataController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private AddUpdateHotelService addUpdateHotelService;

	@Value("${images.hotel.base.dir}")
	private String imageReferBaseUrl;

	@Value("${images.hotel.url.dir}")
	private String imageHotelUrl;

	@Autowired
	private ImageResizeService imgResizeServer;
	
	@Autowired
	private StaticContent staticContent;

	@RequestMapping(method = RequestMethod.GET)
	public String index1(HttpSession session, HttpServletRequest request) {
		return "NewHotelAdditon";
	}

	@RequestMapping(value = "/ajax-post-data", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public String addDataOnly(
			Principal principal,
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam(value = "cityname", required = false) @Valid String cityName,
			@RequestParam("hotelId") @Valid String hotelId,
			@RequestParam("hotelName") @Valid String hotelName,
			@RequestParam("latitude") @Valid String latitude,
			@RequestParam("longitude") @Valid String longitude,
			@RequestParam("descriptionId") @Valid String description,
			@RequestParam("starRatingId") @Valid String rating,
			@RequestParam("Address") @Valid String address,
			@RequestParam("type") @Valid String type,
			@RequestParam(value = "fileToUpload", required = false) final MultipartFile[] fileToUpload)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (user != null) {
			logger.warn(
					"Request for {} hotel. Parameters : Name - {}, id - {}",
					type, hotelName, hotelId);
			
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
			
			addUpdateHotel(user,cityId, hotelId, hotelName, latitude, longitude,
					description, rating, address, type, fileToUpload);
			ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
			imageResponseDTO.setImagePath("test");
			Gson gson = new Gson();
			return gson.toJson(imageResponseDTO);
		}
		return null;

	}

	private NewOrUpdateHotelDetailsDTO addUpdateHotel(Person user,String cityId,
			String hotelId, String hotelName, String latitude,
			String longitude, String description, String rating,
			String address, String type, MultipartFile[] fileToUpload) {

		NewOrUpdateHotelDetailsDTO newHotel = new NewOrUpdateHotelDetailsDTO();

		if (!StringUtils.isEmpty(hotelId)) {
			newHotel.setId(Long.parseLong(hotelId));
		}
		newHotel.setName(hotelName);
		newHotel.setAddress(address);
		newHotel.setCityId(cityId);
		newHotel.setDescription(description);
		newHotel.setCreatedBy(user.getPersonId().toString());
		if (!StringUtils.isEmpty(latitude)) {
			newHotel.setLatitude(Double.parseDouble(latitude));
		}
		if (!StringUtils.isEmpty(longitude)) {
			newHotel.setLongitude(Double.parseDouble(longitude));
		}
		if (!StringUtils.isEmpty(rating)) {
			newHotel.setRating(rating);
		}

		HotelDb returnedHotel = null;
		if (type.equalsIgnoreCase("new")) {
			returnedHotel = addUpdateHotelService.addNewHotel(newHotel);
		} else if (type.equalsIgnoreCase("update")) {
			returnedHotel = addUpdateHotelService.updateHotel(newHotel);
		}

		if (null != returnedHotel && null != fileToUpload) {
			Long returnedHotelId = returnedHotel.getId();
			List<ImageResponseDTO> images = uploadImage(user,fileToUpload,
					returnedHotelId.toString());
			String existingImages = returnedHotel.getImages();

			/*StringBuilder sb = new StringBuilder();
			if (!StringUtils.isEmpty(existingImages)) {
				sb.append(existingImages);
			}
			for (ImageResponseDTO obj : images) {
				String imagePath = obj.getImagePath();
				sb.append(imagePath);
				sb.append(",");
			}
			returnedHotel.setImages(sb.toString());*/
			returnedHotel.setUpdatedBy(user.getPersonId().toString());
			addUpdateHotelService.updateHotelInDbDirectly(returnedHotel);
		}
		return null;
	}

	private List<ImageResponseDTO> uploadImage(Person user,
			final MultipartFile[] fileToUpload, String hotelId) {

		String extension = ".jpg";
		List<ImageResponseDTO> images = new ArrayList<ImageResponseDTO>();

		for (MultipartFile multipartFile : fileToUpload) {
			ImageResponseDTO imageResponseDTO = new ImageResponseDTO();

			String id = hotelId + "-" + System.currentTimeMillis();
			String filePath = imageReferBaseUrl + File.separatorChar + id
					+ extension;
			String urlPath = imageHotelUrl + File.separatorChar + id + "m"
					+ extension;

			imageResponseDTO.setImagePath(urlPath);
			// uploading the original image on the file system.
			uploadImageOnDisk(multipartFile, filePath);

			// create thumbnails for timeline images
			if (null != extension && extension.equalsIgnoreCase(".jpg")) {
				// String thumbFilePath = imageThumbnailUploadDir +
				// File.separatorChar + cityCode + 'c' + extension;
				String mobileFilePath = imageReferBaseUrl + File.separatorChar
						+ id + "m" + extension;

				try {
					// imgResizeServer.resizeImages(filePath, thumbFilePath,
					// 100);
					imgResizeServer.resizeImages(filePath, mobileFilePath, 500);
					String imagePath = mobileFilePath.replace("/var/www/html/", "/");
					addUpdateHotelService.updateHotelImagePath(hotelId, imagePath, user.getPersonId().toString());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(
							"Error while generationg thumbnails of City upload image for hotelId - {}",
							hotelId);
					e.printStackTrace();
				}
			}

			String x = "ayan";
			// counter++;
			images.add(imageResponseDTO);

		}
		Gson gson = new Gson();
		return images;

	}

	private void uploadImageOnDisk(final MultipartFile fileToUpload,
			final String destinationPath) {

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
