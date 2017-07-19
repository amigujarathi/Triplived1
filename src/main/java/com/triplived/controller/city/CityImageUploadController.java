package com.triplived.controller.city;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.connectme.domain.triplived.UserResponse;
import com.connectme.domain.triplived.dto.ImageResponseDTO;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.city.CityDataService;
import com.triplived.service.image.ImageResizeService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/cityImageUploadPage")
@SessionAttributes(Constants.LOGIN_USER)
public class CityImageUploadController {

	private static final Logger logger = LoggerFactory
			.getLogger(CityImageUploadController.class);

	@Value("${images.city.thumbnails.upload.dir}")
	private String imageThumbnailUploadDir;

	@Value("${images.city.upload.dir}")
	private String cityImageUploadDir;

	@Value("${images.city.temp.upload.dir}")
	private String cityTempImageUploadDir;

	@Autowired
	private ImageResizeService imgResizeServer;

	@Autowired
	private CityDataService cityDataService;
	
	@Autowired
	private StaticContent staticContent;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
		return "city-image-upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadCityImage(Principal principal,
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			final HttpServletResponse response,
			@RequestParam("cityId") @Valid String cityCode,
			@RequestParam("cityName") @Valid String cityName,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			HttpSession session, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if(null != user) {
			
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
			
			if (null != fileToUpload) {
				MultipartFile multipartFile = fileToUpload[0];
				if (null != multipartFile) {
					logger.warn("Image upload request for city - {}.", cityCode);
					if (multipartFile.getContentType().contains("jpeg")) {
						return uploadImage(user, fileToUpload, cityCode, ".jpg");
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}else {
			return null;
		}

	}

	private String uploadImage(Person user, final MultipartFile[] fileToUpload,
			String cityCode, String extension) {

		ImageResponseDTO imageResponseDTO = new ImageResponseDTO();

		for (MultipartFile multipartFile : fileToUpload) {

			String filePath = cityImageUploadDir + File.separatorChar
					+ cityCode + extension;

			imageResponseDTO.setImagePath(filePath);
			// uploading the original image on the file system.
			uploadImageOnDisk(multipartFile, filePath);

			// create thumbnails for timeline images
			if (null != extension && extension.equalsIgnoreCase(".jpg")) {
				String thumbFilePath = imageThumbnailUploadDir
						+ File.separatorChar + cityCode + 'c' + extension;
				String mobileFilePath = cityImageUploadDir + File.separatorChar
						+ cityCode + "mn" + extension;

				try {
					imgResizeServer.resizeImages(filePath, thumbFilePath, 200);
					imgResizeServer.resizeImages(filePath, mobileFilePath, 500);
					String imagePath = mobileFilePath.replace("/var/www/html/", "/");
					cityDataService.updateCityImagePath(cityCode, imagePath, user.getName());
					
					//Only one image for city is required to be uploaded
					Gson gson = new Gson();
					return gson.toJson(imageResponseDTO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(
							"Error while generationg thumbnails of City upload image for cityCode - {}",
							cityCode);
					e.printStackTrace();
				}
			}

			String x = "ayan";
			// counter++;
			Gson gson = new Gson();
			return gson.toJson(imageResponseDTO);

		}
		return "Image upload error";

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

	/**
	 * This API is invoked, when the user tries to add the description for city
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateCityDescription", method = RequestMethod.POST)
	public @ResponseBody void updateCityDescription(Principal principal,
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestParam("cityId") @Valid String cityCode,
			@RequestParam("cityName") @Valid String cityName,
			@RequestParam("cityDesc") @Valid String cityDescrption) {
		if (user != null) {
			
			try {
				UserResponse userResponse = staticContent.getUserDetails(user.getPersonId().toString());
				if(null != userResponse.getRole()) {
					if(!userResponse.getRole().equalsIgnoreCase("Curator")) {
						return;
					}
				}else {
					return;
				}
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cityDataService.updateCityDescription(user.getName(), cityCode,
					cityName, cityDescrption);
		}
	}
}
