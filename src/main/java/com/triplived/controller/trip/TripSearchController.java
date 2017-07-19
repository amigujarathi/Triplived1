package com.triplived.controller.trip;

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
import org.apache.solr.client.solrj.SolrServerException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.ExploreTagsDTO;
import com.connectme.domain.triplived.dto.ProfileDTO;
import com.connectme.domain.triplived.dto.TrendingTripDTO;
import com.connectme.domain.triplived.dto.TripBetweenCityDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.google.gson.Gson;
import com.triplived.controller.profile.Person;
import com.triplived.service.trip.TripListService;
import com.triplived.service.trip.TripSearchService;
import com.triplived.service.trip.TripService;
import com.triplived.service.user.UserService;
import com.triplived.util.Constants;
import com.triplived.util.TripLivedUtil;

@Controller
@RequestMapping("/searchTrip")
public class TripSearchController {

	private static final Logger logger = LoggerFactory.getLogger(TripSearchController.class );
	
	
	@Autowired
	private TripListService tripService;
	
	@Autowired
	private TripSearchService tripSearchService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method= RequestMethod.GET, value="/cities")
    public @ResponseBody String getTripsBetweenCities(Principal principal, final HttpServletResponse response, 
			@RequestParam("source") @Valid String source,
			@RequestParam("destination") @Valid String destination,
			HttpSession session) {
	
		List<TripBetweenCityDTO> trips = tripService.getTripBetweenCities(source, destination);
		Gson gson = new Gson();
		return gson.toJson(trips);
   }
	
	@RequestMapping(method= RequestMethod.GET, value="/param")
    public @ResponseBody String autoCompleteForTripSearch(Principal principal, final HttpServletResponse response, 
			@RequestParam("param") @Valid String param,
			HttpSession session) {
	
		List<EntityResponseDTO> entities = new ArrayList<EntityResponseDTO>();
		try {
			entities = tripSearchService.autoCompleteForTripSearch(param);
			Gson gson = new Gson();
			return gson.toJson(entities);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			logger.error("Error while autocomplete for Trip Search for param - {}. Exception - {}",param, e.getStackTrace());
			return null;
		}
		
   }
	
	/**
	 * This API is invoked, when the autocomplete for user search is done and profile of a person is invoked.
	 * @param principal
	 * @param response
	 * @param personId
	 * @param session
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/user")
    public @ResponseBody String userSearch(Principal principal, final HttpServletResponse response, 
			@RequestParam("id") @Valid String personId,
			HttpSession session, HttpServletRequest request) {
	
		ProfileDTO profile = userService.loadUserProfile(personId);
		
		resolveId(profile.getTrips(), request);
				
		Gson gson = new Gson();
		return gson.toJson(profile);
		
		
	}
	
	
	@RequestMapping(method= RequestMethod.GET, value="/search")
    public @ResponseBody String tripSearch(Principal principal, final HttpServletResponse response, 
			@RequestParam(value = "id", required = false) @Valid String id,
			@RequestParam("type") @Valid String type,
			@RequestParam(value = "attraction",  required = false) @Valid String attraction,
			@RequestParam(value = "city", required = false) @Valid String city,
			@RequestParam(value = "state", required = false) @Valid String state,
			@RequestParam(value = "country", required = false) @Valid String country,
			@RequestParam(value = "user", required = false) @Valid String user,HttpServletRequest request,
			HttpSession session) {
	
		logger.warn("Trip Search Request params : id - {}, type - {}, attraction - {}, city - {}, state - {}, country - {}, user -{}", id,type,attraction,city,state,country,user);
		Gson gson = new Gson();
		try {
			if(null != type) {
				if(type.equals("attraction")) {
					if(null != attraction && null != state && null != city && null != country) {
						String param = attraction + " , " + city + " , " + state + " , " + country;
						logger.warn("Search parameter - {}", param);
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param, type);
						resolveId(trips, request);
						return gson.toJson(trips);
					}
					if(null != attraction && null == state && null != city && null != country) {
						String param = attraction + " , " + city ;
						logger.warn("Search parameter - {}", param);
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param, type);
						resolveId(trips, request);
						return gson.toJson(trips);
					}
				}else if(type.equals("city")) {
					if(null != city && null != state && null != country) {
						String param = city + " , " + state + " , " + country;
						logger.warn("Search parameter - {}", param);
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param, type);
						resolveId(trips, request);
						return gson.toJson(trips);
					}
					if(null != city && null == state && null != country) {
						String param = city + " , " + country;
						logger.warn("Search parameter - {}", param);
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param, type);
						resolveId(trips, request);
						return gson.toJson(trips);
					}
				}else if(type.equals("state")) {
					if(null != state && null != country) {
						String param = state + " , " + country;
						logger.warn("Search parameter - {}", param);
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param, type);
						resolveId(trips, request);
						return gson.toJson(trips);
					}
				}else if(type.equals("country")) {
					if(null != country) {
						String param = country;
						logger.warn("Search parameter - {}", param);
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param, type);
						resolveId(trips, request);
						return gson.toJson(trips);
					}
				}/*else if(type.equals("user")) {
					if(null != user) {
						String param = user;
						List<TripSearchDTO> trips = tripSearchService.searchTrips(param);
						return gson.toJson(trips);
					}
				}*/
			} else {
				return null;
			}
		}catch(Exception e) {
			return null;			
		}
		return null;

	}
	
	
	@RequestMapping(method= RequestMethod.GET, value="/explore/list")
    public @ResponseBody String exploreList(Principal principal, final HttpServletResponse response, 
			HttpSession session) {
		
		List<ExploreTagsDTO> list = tripSearchService.getExploreTags();
		Gson gson = new Gson();
		return gson.toJson(list);
		
		
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/explore")
    public @ResponseBody String explore(Principal principal, final HttpServletResponse response, 
			@RequestParam(value = "term", required = false) @Valid String term,
			@RequestParam(value = "type", required = false) @Valid String type,
			HttpSession session, HttpServletRequest request) {

		if(type.equalsIgnoreCase("category")) {
			
			try {
				List<TripSearchDTO> trips = tripSearchService.searchTripsByCategory(term, type);
				resolveId(trips, request);
				Gson gson = new Gson();
				return gson.toJson(trips);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return null;
	}
   	
	
	@RequestMapping(method= RequestMethod.GET, value="/filter")
    public @ResponseBody String tripFilter(Principal principal, final HttpServletResponse response, 
			@RequestParam("id") @Valid String id,
			@RequestParam("type") @Valid String type,
			@RequestParam("attraction") @Valid String attraction,
			@RequestParam("city") @Valid String city,
			@RequestParam("state") @Valid String state,
			@RequestParam("country") @Valid String country,
			@RequestParam("user") @Valid String user,
			HttpSession session) {
	
		Gson gson = new Gson();
		return null;
	}
	
	private void resolveId(List<TripSearchDTO> trips, HttpServletRequest request) {
		if(CollectionUtils.isNotEmpty(trips)) {
			for(TripSearchDTO dto : trips) {
				if(!TripLivedUtil.isPublicIdEnabled(request)) {
					dto.setTripId(dto.getOldTripId());
				}
				dto.setOldTripId(null);
			}
		}
	}
	
}