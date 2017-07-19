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
import javax.ws.rs.PathParam;

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
import com.connectme.domain.triplived.dto.NewAttractionDataAddDTO;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.triplived.controller.profile.Person;
import com.triplived.dao.category.CategoryDao;
import com.triplived.entity.CategoryDb;
import com.triplived.entity.TripDb;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.attraction.AttractionService;
import com.triplived.service.staticContent.StaticAttractionService;
import com.triplived.util.Constants;

@Controller
@RequestMapping("/attraction/add")
@SessionAttributes(Constants.LOGIN_USER)
public class AttractionNewDataController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class );
	
	@Autowired
	private StaticAttractionService staticAttractionService;
	
	@Autowired
	private AttractionService attractionService;
	
    @Value("${images.upload.dir}")
    private String imagesUploadDir;
    
    @Value("${images.attraction.base.dir}")
    private String imageReferBaseUrl; 
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
	private StaticContent staticContent;
    
    
    @RequestMapping(method= RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request) {
    	     List<CategoryDb> list = categoryDao.getCategories();
			return "Newattraction";
	}
    
      @RequestMapping(value = "/ajax-post-data", method = RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public NewAttractionDataAddDTO addDataOnly(
			@ModelAttribute(Constants.LOGIN_USER) Person user,
			@RequestParam("cityId") @Valid String cityId,
			@RequestParam("attractionId") @Valid Long attractionId,
			@RequestParam("latitude") @Valid Double latitude,
			@RequestParam("longitude") @Valid Double longitude,
			@RequestParam("googlePlaceid")  @Valid String googlePlaceid,
			@RequestParam("categoryName")  @Valid String categoryName,
			@RequestParam("category_seq") @Valid Long category_seq,
			@RequestParam("address")  @Valid String address,
			@RequestParam("state")  @Valid String state,
			@RequestParam("newAttractionName")  @Valid String newAttractionName
			) throws JsonGenerationException ,JsonMappingException, IOException{
		
	
		if(user != null){
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
			
			logger.warn("New Attraction data added by user {} for attraction {} ", attractionId, newAttractionName);
			return addNewAttraction(user,cityId, attractionId, latitude, longitude,googlePlaceid,categoryName,category_seq,address,state,newAttractionName);
		}
		return null;
	}

	private NewAttractionDataAddDTO addNewAttraction(Person user,
			String cityId, Long attractionId, Double latitude,
			Double longitude, String googlePlaceid, String categoryName,Long category_seq,
			String address, String state,String newAttractionName) {
		NewAttractionDataAddDTO newDataAdd = new NewAttractionDataAddDTO();
		newDataAdd.setAttractionName(newAttractionName);
		newDataAdd.setCategoryName(categoryName);
		newDataAdd.setGooglePlaceId(googlePlaceid);
		newDataAdd.setLatitude(latitude);
		newDataAdd.setLongitude(longitude);
		newDataAdd.setAddress(address);
		newDataAdd.setState(state);
		newDataAdd.setCity_Id(cityId);
		newDataAdd.setCategory_seq(category_seq);
		if(null != user) {
			newDataAdd.setCreatedBy(user.getName());
			newDataAdd.setUpdatedBy(user.getName());
		}
		attractionService.addNewAttraction(newDataAdd);
		
		return null;
	}	
	
	//beautify json 
	@RequestMapping(value = "/ajax-beautify-json", method = RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public String prettyTripJson(
			@RequestParam("compactjson") @Valid String compactjson){
		if(compactjson != null && !compactjson.isEmpty()){
			try{
				JsonParser parser = new JsonParser();
		        JsonObject json = parser.parse(compactjson).getAsJsonObject();
		        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		        String prettyJson = gson.toJson(json);
		       return prettyJson;
			}catch(Exception e){
				e.printStackTrace();
				return compactjson;
			}
		}
		else{
			logger.warn("compactJson is null.");
			return "compactJson is null";
		}
 
	}
	
	//submit edited trip 
	
	/*@RequestMapping(value = "/attraction/add/ajax-submit-json", method = RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public void submitTripJson(
			@RequestParam("tripJson") @Valid String tripJson,@RequestParam("tripId") @Valid Long tripId){
			statictripjsonservice.submitTripJson(tripJson,tripId);
		
	}
	*/
	@RequestMapping(value = "/ajax-submit-json", method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody void submitTripJson(@RequestBody TimelineTrip trip){
			System.out.println("complete trip is : "+trip);
			//statictripjsonservice.submitTripJson(tripJson,tripId);
            
    }

}
