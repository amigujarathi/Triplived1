package com.triplived.controller.image;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

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
import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.AttractionImageDTO;
import com.connectme.domain.triplived.dto.AttractionResponseDTO;
import com.connectme.domain.triplived.dto.ImageResponseDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripCityType;
import com.connectme.domain.triplived.trip.dto.TripPhotoDTO;
import com.connectme.domain.triplived.trip.dto.TripReviewDTO;
import com.google.gson.Gson;
import com.triplived.entity.TripDb;
import com.triplived.service.attraction.AttractionService;
import com.triplived.service.image.ImageResizeService;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.service.trip.TripService;

@Controller
@RequestMapping("/image")
public class ImageController {

	private static final Logger logger = LoggerFactory.getLogger(ImageController.class );
	
    @Value("${images.user.timeline.upload.dir}")
    private String imagesUploadDir;
    
    @Value("${images.user.timeline.upload.dir.external.path}")
    private String imageUploadDirExternalLink;
    
    @Value("${images.user.profile.timeline.upload.dir}")
    private String profileUploadDir;
    
    @Value("${images.user.profile.timeline.upload.dir.external.path}")
    private String profileImageUploadDirExternalLink;
    
    @Value("${videos.user.timeline.upload.dir}")
    private String videoUploadDir;
    
    @Value("${videos.user.timeline.upload.dir.external.path}")
    private String videoUploadDirExternalLink;
    

    @Autowired
    private ImageResizeService imgResizeServer;
    
    
    @RequestMapping(value = "/uploadProfile/{userId}", method = RequestMethod.POST)
	public @ResponseBody String uploadProfilePicture(Principal principal, final HttpServletResponse response, 
			@PathVariable("userId") @Valid String userId,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			HttpSession session, HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		String deviceId = request.getHeader("UserDeviceId");
		
		if(null != fileToUpload) {
			MultipartFile multipartFile = fileToUpload[0];
			if(null != multipartFile) {
				logger.warn("User Image upload request from device - {} for USER - {}. Name - {}, Content-type -{}", deviceId, userId, multipartFile.getName(), multipartFile.getContentType());	
				if(multipartFile.getContentType().contains("jpeg")) {
					return uploadImage(userId, fileToUpload, profileUploadDir, profileImageUploadDirExternalLink, ".jpg");
				}else {
					return null;
				}
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
    
       
	@RequestMapping(value = "/uploadTimelineFile", method = RequestMethod.POST)
	public @ResponseBody String uploadTimelineImage(Principal principal, final HttpServletResponse response, 
			@RequestParam("tripId") @Valid String tripId,
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,
			HttpSession session, HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		try {
			String deviceId = request.getHeader("UserDeviceId");
			
			if(null != fileToUpload) {
				MultipartFile multipartFile = fileToUpload[0];
				if(null != multipartFile) {
					logger.warn("Image/Video upload request from device - {} for trip - {}. Name - {}, Content-type -{}", deviceId, tripId,multipartFile.getName(), multipartFile.getContentType());	
					if(multipartFile.getContentType().contains("jpeg")) {
						return uploadImage(tripId, fileToUpload, imagesUploadDir, imageUploadDirExternalLink, ".jpg");
					}else if(multipartFile.getContentType().contains("mp4")) {
						return uploadImage(tripId, fileToUpload, videoUploadDir, videoUploadDirExternalLink, ".mp4");
					}else {
						logger.warn("Image/Video:Failed 1 upload request from device - {} for trip - {}. Name - {}, Content-type -{}", deviceId, tripId,multipartFile.getName(), multipartFile.getContentType());
						return null;
					}
				}else {
					logger.warn("Image/Video:Failed 2 upload request from device - {} for trip - {}. Name - {}, Content-type -{}", deviceId, tripId,multipartFile.getName(), multipartFile.getContentType());
					return null;
				}
			}else {
				logger.warn("Image/Video:Failed 3 upload request from device - {} for trip - {}. Content-type -{}", deviceId, tripId , "Not multipart");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error occured while uploading image", e);
		}
		return null;
	}
	
	
	
	
	@RequestMapping(value = "/fbTimelineImageUpload", method = RequestMethod.POST)
	public @ResponseBody String fbImagePath(Principal principal, final HttpServletResponse response, 
			@RequestParam("tripId") @Valid String tripId,
			@RequestParam("path") @Valid String path,
			HttpSession session, HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		String deviceId = request.getHeader("UserDeviceId");
		logger.warn("Image upload request from device - {} for trip - {} for facebook image, path - {}", deviceId, tripId, path);
		
		String id =  System.currentTimeMillis() + "-" + tripId;
		
		ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
		String filePath = imagesUploadDir + File.separatorChar + tripId + File.separatorChar + id + ".jpg";
		String externalFilePath = imageUploadDirExternalLink + File.separatorChar + tripId + File.separatorChar + id + ".jpg";
		imageResponseDTO.setImagePath(externalFilePath);
		
		File saveImage = new File(filePath);
		File saveImageDir = saveImage.getParentFile();
		if (!saveImageDir.exists()) {
			logger.warn("Image does not exits, create directory first");
			saveImageDir.mkdirs();
		}
	
		URL url = new URL(path);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(filePath);

		byte[] b = new byte[2048];
		int length;
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
		
		//create thumbnails for timeline images
		
			String thumbFilePath = imagesUploadDir + File.separatorChar + tripId + File.separatorChar + id + 'c' + ".jpg";
			String mediumFilePath = imagesUploadDir + File.separatorChar + tripId + File.separatorChar + id + 'm' + ".jpg";
			try {
				imgResizeServer.resizeImages(filePath, thumbFilePath, 300);
				imgResizeServer.resizeImages(filePath, mediumFilePath, 600);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error while generationg thumbnails of Fb Timeline image - {} for trip - {}", id, tripId);
				e.printStackTrace();
			}
		
			Gson gson = new Gson();
			return gson.toJson(imageResponseDTO);
		//return null;
		
	}
	
	
	@RequestMapping(value = "/uploadFile/{experienceId}", method = RequestMethod.POST)
	public @ResponseBody String uploadImageFromWebCreation(Principal principal, final HttpServletResponse response, 
			@RequestParam("fileToUpload") final MultipartFile[] fileToUpload,@PathVariable String experienceId,
			HttpSession session, HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		String tripId = experienceId.split("-")[1];
		
		try {
			String deviceId = request.getHeader("UserDeviceId");
			
			
			if(null != fileToUpload) {
				MultipartFile multipartFile = fileToUpload[0];
				if(null != multipartFile) {
					logger.warn("Image/Video upload request from device - {} for trip - {}. Name - {}, Content-type -{}", deviceId, tripId,multipartFile.getName(), multipartFile.getContentType());	
					if(multipartFile.getContentType().contains("jpeg")) {
						return uploadImageFromWebCreation(tripId, fileToUpload, imagesUploadDir, imageUploadDirExternalLink, ".jpg");
					}else if(multipartFile.getContentType().contains("mp4")) {
						return uploadImageFromWebCreation(tripId, fileToUpload, videoUploadDir, videoUploadDirExternalLink, ".mp4");
					}else {
						logger.warn("Image/Video:Failed 1 upload request from device - {} for trip - {}. Name - {}, Content-type -{}", deviceId, tripId,multipartFile.getName(), multipartFile.getContentType());
						return null;
					}
				}else {
					logger.warn("Image/Video:Failed 2 upload request from device - {} for trip - {}. Name - {}, Content-type -{}", deviceId, tripId,multipartFile.getName(), multipartFile.getContentType());
					return null;
				}
			}else {
				logger.warn("Image/Video:Failed 3 upload request from device - {} for trip - {}. Content-type -{}", deviceId, tripId , "Not multipart");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error occured while uploading image", e);
		}
		return null;
	}
	
	private String uploadImage(String tripId, final MultipartFile[] fileToUpload, String path, String externalPath, String extension) {

		ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
		
		String id =  System.currentTimeMillis() + "-" + tripId;
		
		for (MultipartFile multipartFile : fileToUpload) {
			
			String filePath = path + File.separatorChar + tripId + File.separatorChar + id + extension;
			String externalFilePath = externalPath + File.separatorChar + tripId + File.separatorChar + id + extension;
			imageResponseDTO.setImagePath(externalFilePath);
			// uploading the original image on the file system.
			uploadImageOnDisk(multipartFile, filePath);
			
			//create thumbnails for timeline images
			if(null != extension && extension.equalsIgnoreCase(".jpg")) {
				String thumbFilePath = path + File.separatorChar + tripId + File.separatorChar + id + 'c' + extension;
				String mediumFilePath = path + File.separatorChar + tripId + File.separatorChar + id + 'm' + extension;
				try {
					imgResizeServer.resizeImages(filePath, thumbFilePath, 300);
					imgResizeServer.resizeImages(filePath, mediumFilePath, 600);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error while generationg thumbnails of Timeline image - {} for trip - {}", id, tripId);
					e.printStackTrace();
				}
			}
					
			String x = "ayan";	
			//counter++;
			Gson gson = new Gson();
			return gson.toJson(imageResponseDTO);
			
		}
		return "Image upload error";

	}
	
	
	private String uploadImageFromWebCreation(String experienceId, final MultipartFile[] fileToUpload, String path, String externalPath, String extension) {

		ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
		
		String tripId = experienceId.split("-")[1];
		
		String id =  System.currentTimeMillis() + "-" + experienceId;
		
		for (MultipartFile multipartFile : fileToUpload) {
			
			String filePath = path + File.separatorChar + tripId + File.separatorChar + id + extension;
			String externalFilePath = externalPath + File.separatorChar + tripId + File.separatorChar + id + extension;
			
			// uploading the original image on the file system.
			uploadImageOnDisk(multipartFile, filePath);
			
			//create thumbnails for timeline images
			if(null != extension && extension.equalsIgnoreCase(".jpg")) {
				String thumbFilePath = path + File.separatorChar + tripId + File.separatorChar + id + 'c' + extension;
				String mediumFilePath = path + File.separatorChar + tripId + File.separatorChar + id + 'm' + extension;
				try {
					imgResizeServer.resizeImages(filePath, thumbFilePath, 300);
					imgResizeServer.resizeImages(filePath, mediumFilePath, 600);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error while generationg thumbnails of Timeline image - {} for trip - {}", id, tripId);
					e.printStackTrace();
				}
			}
					
			String x = "ayan";	
			//counter++;
			Dimension dim = getImageDim(filePath);

			File f = new File(filePath); 
			String newFilePath = getFilePathAndNameWithoutSuffix(filePath) + File.separator + dim.getWidth() + File.separator 
								 + dim.getHeight() + extension;
            
			f.renameTo(new File(newFilePath));
            
			externalFilePath = getFilePathAndNameWithoutSuffix(externalFilePath) + File.separator + dim.getWidth() + File.separator 
					 		   + dim.getHeight() + extension;
			imageResponseDTO.setImagePath(externalFilePath);
			
			Gson gson = new Gson();
			return gson.toJson(imageResponseDTO);
			
		}
		return "Image upload error";

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
	
	private Dimension getImageDim(final String path) {
	    Dimension result = null;
	    String suffix = getFileSuffix(path);
	    Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
	    if (iter.hasNext()) {
	        ImageReader reader = iter.next();
	        try {
	            ImageInputStream stream = new FileImageInputStream(new File(path));
	            reader.setInput(stream);
	            int width = reader.getWidth(reader.getMinIndex());
	            int height = reader.getHeight(reader.getMinIndex());
	            result = new Dimension(width, height);
	        } catch (IOException e) {
	        	logger.error("Error occured while fetching dimensions image {} at path  {} ", path, e.getMessage());
	        } finally {
	            reader.dispose();
	        }
	    } else {
	        
	    }
	    return result;
	}
	
	private String getFileSuffix(final String path) {
	    String result = null;
	    if (path != null) {
	        result = "";
	        if (path.lastIndexOf('.') != -1) {
	            result = path.substring(path.lastIndexOf('.'));
	            if (result.startsWith(".")) {
	                result = result.substring(1);
	            }
	        }
	    }
	    return result;
	}
	
	private String getFilePathAndNameWithoutSuffix(final String path) {
	    String result = null;
	    if (path != null) {
	        result = "";
	        if (path.lastIndexOf('.') != -1) {
	            result = path.substring(path.lastIndexOf('.'));
	            if (result.startsWith(".")) {
	                result = result.substring(0);
	            }
	        }
	    }
	    return result;
	}
	
	
	
}
