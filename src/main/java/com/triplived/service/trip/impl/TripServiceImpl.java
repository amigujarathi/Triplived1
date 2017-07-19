package com.triplived.service.trip.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;

import javax.persistence.PrePersist;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.RequiredStatus;
import com.connectme.domain.triplived.VideoStatus;
import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.TripCommentDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.connectme.domain.triplived.dto.TripSummaryDTO;
import com.connectme.domain.triplived.dto.WebExperienceDTO;
import com.connectme.domain.triplived.dto.WebTripDTO;
import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.trip.dto.SubTrip;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.connectme.domain.triplived.trip.dto.TripCityDTO;
import com.connectme.domain.triplived.trip.dto.TripCityType;
import com.connectme.domain.triplived.trip.dto.TripEventDTO;
import com.connectme.domain.triplived.trip.dto.TripTransportDTO;
import com.domain.triplived.trip.dto.Event;
import com.domain.triplived.trip.dto.File;
import com.domain.triplived.trip.dto.TimelineTrip;
import com.domain.triplived.trip.dto.ToCityDTO;
import com.domain.triplived.trip.dto.Update;
import com.domain.triplived.trip.dto.User;
import com.google.gson.Gson;
import com.triplived.dao.experience.ExperienceDAO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripEntityDAO;
import com.triplived.dao.trip.TripMediaDAO;
import com.triplived.dao.trip.TripVideoDao;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripAttractionDetailsDb;
import com.triplived.entity.TripCityDb;
import com.triplived.entity.TripCommentsDb;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripEntityDb;
import com.triplived.entity.TripEntityMediaDb;
import com.triplived.entity.TripExperiencesDb;
import com.triplived.entity.TripFaqDb;
import com.triplived.entity.TripHistoryDb;
import com.triplived.entity.TripHotelDetailsDb;
import com.triplived.entity.TripKitListDb;
import com.triplived.entity.TripLikesDb;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.TripShareDb;
import com.triplived.entity.TripStatusDb;
import com.triplived.entity.TripUserDb;
import com.triplived.entity.TripVideoDb;
import com.triplived.entity.UserAccountsDb;
import com.triplived.entity.WebExperiencesDb;
import com.triplived.entity.WebExperiencesMediaDb;
import com.triplived.mail.client.UserTripReviewMail;
import com.triplived.mail.client.VideoGenerationRequestMail;
import com.triplived.rest.trip.TripClient;
import com.triplived.service.AppUtils;
import com.triplived.service.experience.ExperienceService;
import com.triplived.service.notification.GCMNotificationService;
import com.triplived.service.trip.TripService;
import com.triplived.service.user.UserService;
import com.triplived.util.Constants;
import com.triplived.util.TripLivedUtil;

@Service
public class TripServiceImpl implements TripService {

	private static final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);
	
	@Autowired
	private TripDAO tripDAO;
	
	@Autowired
	private ExperienceDAO experienceDAO;
	
	@Autowired
	private TripVideoDao tripVideoDao;
	
	@Autowired
	private TripEntityDAO tripEntitydao;
	
	@Autowired
	private TripMediaDAO tripEntityMediadao;
	
	/*@Autowired
    private VideoController videoController;*/
    
    
	@Autowired
	private UserTripReviewMail userTripReviewMail;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GCMNotificationService gcmService;
	
	@Autowired
	private TripClient tripClient;
	
	@Autowired
    private VideoGenerationRequestMail videoGenerationRequestMail;
	
	@Autowired
	private AppUtils apputils;
	
	@Autowired
	private ExperienceService experienceService;
	/*@Autowired
	@Qualifier("commentHandler")
	private Handler commentsHandler;
	
	@Autowired
	@Qualifier("likeHandler")
	private Handler likeHandler;
	
	@Autowired
	@Qualifier("tripSubmitHandler")
	private Handler tripSubmitHandler;
	*/
	
	/*public void setTripSubmitHandler(Handler tripSubmitHandler) {
		this.tripSubmitHandler = tripSubmitHandler;
	}
	
	public Handler getTripSubmitHandler() {
		return tripSubmitHandler;
	}*/
	
	
	@Transactional(readOnly=false)
	@Override
	public void updateTripCity(Long tripId, Long subTripId, TripCityDTO cityDTO, String transportType) {
		
		TripDb tripDb = tripDAO.getTrip(tripId);
		
		Gson gson = new Gson();
		Trip trip = gson.fromJson(tripDb.getTripData(), Trip.class);
		
		if(trip == null) {
			trip = new Trip();
		}
		
		//Always assuming that the person would not be able to create a new subtrip without completing the current one.
		if(trip.getSubTrips() != null) {
			for(SubTrip sTrip : trip.getSubTrips()) {
				if(!sTrip.getComplete()) {
					 subTripId = sTrip.getId();
					}
			}
		}
		SubTrip subTrip = null;
		
		//If trip is not empty, find a subtrip that is currently active
		List<SubTrip> subTrips = trip.getSubTrips();
		if(subTrips == null) {
			subTrips = new ArrayList<SubTrip>();
			trip.setSubTrips(subTrips);
		}
		if(!CollectionUtils.isEmpty(subTrips)) {
		for(SubTrip sTrip : subTrips) {
			if(!sTrip.getComplete()) {
				subTrip = sTrip;
				}
			}
		}
		int flag = 0;
		//Decide whether this is normal progression flow or an editing flow
		if(subTrip != null && subTrip.getId() == subTripId) { //progression in an incomplete subTrip
			flag = 1;
		} else if(subTrip == null && subTripId == null) { //progression and beginning of a new subTrip
			flag = 0;
		} else if((subTrip == null && subTripId != null) || (subTripId != null && subTrip.getId() != subTripId)) { //editing
			for(SubTrip sTrip : subTrips) {
				if(subTripId == sTrip.getId()) {
					subTrip = sTrip;
					}
				}
			flag = 2;
		}
		//After the above conditions, we would definitely have a subTrip with us.
		
		if(flag == 0) { // progression and beginning of a new subTrip, only when a city is source
			Long id = System.currentTimeMillis();
			if(cityDTO.getCityType().toString().equalsIgnoreCase(TripCityType.Source.toString())) {
				cityDTO.setSubTripId(id);
				
				//Transforming a bit for Shadman's requirement of having first source city as a toCity in new subtrip.
				if(subTrips.size() == 0) {
					SubTrip s = new SubTrip(id);
					s.setFromCityDTO(null);
					s.setToCityDTO(cityDTO);
					s.setTripTransportDTO(null);
					s.setComplete(true);
					subTrips.add(s);
				}
				subTrip = new SubTrip(id);
				
				subTrip.setFromCityDTO(cityDTO);
				subTrips.add(subTrip);
			}
		}
		
		if(flag == 1) { //progression in an incomplete subTrip, this can be when 
			if(cityDTO.getCityType().toString().equalsIgnoreCase(TripCityType.Source.toString())) {
				TripCityDTO cityObj = subTrip.getFromCityDTO();
				if(cityObj != null) {
					//cannot modify cities
				} else {
					//cityObj = cityDTO;
					cityDTO.setSubTripId(subTripId);
					subTrip.setFromCityDTO(cityDTO);
					//subTrips.add(subTrip);
				}
				
			}
			if(cityDTO.getCityType().toString().equalsIgnoreCase(TripCityType.Destination.toString())) {
				TripCityDTO cityObj = subTrip.getToCityDTO();
				TripTransportDTO transport = new TripTransportDTO();
				transport.setTransportType(transportType);
				List<TripTransportDTO> list = new ArrayList<TripTransportDTO>();
				list.add(transport);
				subTrip.setTripTransportDTO(list);
				
				if(cityObj != null) {
					//cannot modify cities
				} else {
					//cityObj = cityDTO;
					cityDTO.setSubTripId(subTripId);
					subTrip.setToCityDTO(cityDTO);
					//subTrips.add(subTrip);
				}
			}
		}
		
		if(flag == 2) {
			
		}
		
		TripDb tripdB = tripDAO.getTrip(tripId);
			
		tripdB.setTripData(gson.toJson(trip));
		tripDAO.updateTrip(tripdB);
		
	}
	
	@Transactional(readOnly=false)
	@Override
	public void addAttractionToTrip(Long tripId, AttractionDataUploadDTO attrObj) {
		
		
	}
	
	@Transactional(readOnly=false)
	@Override
	public void addAttractionToCity(String tripId, TripEventDTO attrDTO) {
		
		TripDb tripDb = tripDAO.getTrip(Long.parseLong(tripId));
		
		Gson gson = new Gson();
		Trip trip = gson.fromJson(tripDb.getTripData(), Trip.class);
		
		SubTrip thisSubTrip = null;
		List<SubTrip> subTrips = trip.getSubTrips();
		if(subTrips != null) {
			for(SubTrip s : subTrips) {
				if(s.getId().equals(attrDTO.getSubTripId())) {
					thisSubTrip = s;
				}
			}
		}
		
		TripCityDTO toCity = thisSubTrip.getToCityDTO();
		List<TripEventDTO> attractions = toCity.getEvents();
		if(attractions == null) {
			attractions = new ArrayList<TripEventDTO>();
		}
		attractions.add(attrDTO);
		toCity.setEvents(attractions);
		
		
		tripDb.setTripData(gson.toJson(trip));
		tripDAO.updateTrip(tripDb);
	}
	
	
	@Transactional(readOnly=false)
	@Override
	public void addHotelToCity(String tripId, TripEventDTO attrDTO) {
		
		TripDb tripDb = tripDAO.getTrip(Long.parseLong(tripId));
		
		Gson gson = new Gson();
		Trip trip = gson.fromJson(tripDb.getTripData(), Trip.class);
		
		SubTrip thisSubTrip = null;
		List<SubTrip> subTrips = trip.getSubTrips();
		if(subTrips != null) {
			for(SubTrip s : subTrips) {
				if(s.getId().equals(attrDTO.getSubTripId())) {
					thisSubTrip = s;
				}
			}
		}
		
		TripCityDTO toCity = thisSubTrip.getToCityDTO();
		List<TripEventDTO> events = toCity.getEvents();
		if(events == null) {
			events = new ArrayList<TripEventDTO>();
		}
		events.add(attrDTO);
		toCity.setEvents(events);
		
		
		tripDb.setTripData(gson.toJson(trip));
		tripDAO.updateTrip(tripDb);
	}
	
	@Transactional(readOnly=false)
	@Override
	public String getTrip(Long tripId) {
		TripDb tripDb = tripDAO.getTrip(tripId);
		
		Gson gson = new Gson();
		Trip trip = gson.fromJson(tripDb.getTripData(), Trip.class);
		sortEvents(trip);
		
		String tripJson = gson.toJson(trip);
		
		return tripJson;
	}
	
	@Transactional(readOnly=false)
	@Override
	public WebTripDTO getTripForWebEdit(String newTripId, Long internalTripId) {
		
		List<WebExperienceDTO> experiences = experienceService.getWebExperiencesByTripId(newTripId);
		TripDb tripDb = tripDAO.getTrip(internalTripId);
		
		Gson gson = new Gson();
		TimelineTrip trip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
		
		Long userId = getUserIdByTripId(internalTripId);
		
		WebTripDTO obj = new WebTripDTO();
		obj.setExperiences(experiences);
		obj.setTripName(trip.getTripName());
		obj.setTripCoverUri(trip.getTripCoverUri());
		if(trip.getAdditionalProperties().containsKey("tripSummary")) {
			obj.setTripSummary(trip.getAdditionalProperties().get("tripSummary").toString());
		}
		obj.setUserId(userId);
		obj.setTripCategory(trip.getTripCategories());
		return obj;
	}
	
	
	@Transactional(readOnly=false)
	@Override
	@PrePersist
	public Long createTrip(TimelineTrip trip, String endDateTimestamp, String source) {
		Gson gson = new Gson();
		TripDb tripdB = new TripDb();
		
		
		tripdB.setTripData(gson.toJson(trip));
		tripdB.setTripName(trip.getTripName());
		tripdB.setCreateDate(new Date());
		if(null != endDateTimestamp) {
			Long value = Long.parseLong(endDateTimestamp);
			tripdB.setEndDate(value/1000);
		}
		if(null != source) {
			tripdB.setTripSource("web");
			tripdB.setTripType("TIMELINE_WEB");
		}
		tripDAO.updateTrip(tripdB);
		
		trip.setTripId(tripdB.getId().toString());
		return tripdB.getId();
	}
	
	@Transactional(readOnly=false)
	@Override
	public String updateTripWithRecordingStatus(Long tripId) {
		TripStatusDb tripStatusDb = new TripStatusDb();
		tripStatusDb.setId(tripId);
		tripStatusDb.setPublicId(null);
		tripStatusDb.setUpdateType(Constants.TRIP_RECORDING_STATE);
		tripStatusDb.setLastUpdatedTs(System.currentTimeMillis() / 1000L);
		tripDAO.updateTripStatus(tripStatusDb);
		
		return "true";
	}
	
	@Transactional(readOnly=false)
	@Override
	public String updateTripWithDeleteStatus(Long tripId) {
		TripStatusDb obj = tripDAO.getTripStatusObj(tripId);
		if(null != obj) {
			obj.setUpdateType(Constants.TRIP_DELETED_STATE);
			obj.setLastUpdatedTs(System.currentTimeMillis() / 1000L);
			tripDAO.updateTripStatus(obj);
		}
		return "true";
	}
	
	@Override
	@Transactional
	@Async
	public Future<Boolean> updateTripWithFinalizedStatus(Long tripId) {
		TripStatusDb obj = tripDAO.getTripStatusObj(tripId);
		if(null != obj) {
			obj.setUpdateType(Constants.TRIP_FINALIZED_STATE);
			obj.setLastUpdatedTs(System.currentTimeMillis() / 1000L);
			tripDAO.updateTripStatus(obj);
		}
		return new AsyncResult<Boolean>(true);
	}
	
	@Transactional(readOnly=false)
	@Override
	public String updateTripWithEditStatus(Long tripId) {
		TripStatusDb obj = tripDAO.getTripStatusObj(tripId);
		if(null != obj) {
			obj.setUpdateType(Constants.TRIP_EDITING_STATE);
			obj.setLastUpdatedTs(System.currentTimeMillis() / 1000L);
			tripDAO.updateTripStatus(obj);
		}
		return "true";
	}
	
	@Transactional(readOnly=false)
	@Override
	public void endTrip(Long tripId, Long personId) {
		TripUserDb tripDb = tripDAO.getTripIdByEmail(personId);
		
		tripDb.setComplete(true);
		tripDAO.updateUserTrip(tripDb);
		
		TripDb tripdB = tripDAO.getTrip(tripId);
		Gson gson = new Gson();
		Trip trip = gson.fromJson(tripdB.getTripData(), Trip.class);
		
		TripCityDb tripCityDb = new TripCityDb();
		
		int index = 1;
		
		if(null != trip && !CollectionUtils.isEmpty(trip.getSubTrips()))
		for(SubTrip st : trip.getSubTrips()) {
			if(index == 1) {
				if(null != st.getFromCityDTO())
				tripCityDb.setFromCityId(st.getFromCityDTO().getCityId());
			}
			
			if(index == (trip.getSubTrips().size() - 1)) {
				if(null != st.getToCityDTO())
					tripCityDb.setToCityId(st.getToCityDTO().getCityId());
			}
			else if (index != (trip.getSubTrips().size() - 1)) {
				if(null != st.getToCityDTO()) {
					if(tripCityDb.getStopoverCityIds() != null) {
					      String x = tripCityDb.getStopoverCityIds();
					      tripCityDb.setStopoverCityIds(x + st.getToCityDTO().getCityId() +  ",");
					}else {
						tripCityDb.setStopoverCityIds(st.getToCityDTO().getCityId() + ",");
					}
				}
			}
			index++;
		}
		
		tripCityDb.setTripId(tripId);
		tripDAO.updateTripCityWise(tripCityDb);
	}
	
	
	@Override
	public void populateTimelineTripData(TimelineTrip trip, boolean breakCityWise, boolean breakEntityWise) {
		Long tripId = Long.parseLong(trip.getTripId());
		/*TripUserDb tripDb = tripDAO.getUserIdByTripId(tripId);
		
		//tripDb.setComplete(true);
		tripDAO.updateUserTrip(tripDb);
		*/
		
		//countTrip to determine if the trip is new or not
		int countTrip = 0;
		 TripUserDb tripUserDb = tripDAO.getUserIdByTripId(Long.parseLong(trip.getTripId()));
			if(tripUserDb.getComplete()) {
				tripUserDb.setComplete(false);
				tripDAO.updateUserTrip(tripUserDb);
				countTrip = 1;
			}
			
		
		Set<String> countCities = new HashSet<String>();
		Set<String> countAttractions = new HashSet<String>() ;
		int countActivities = 0; 
		Set<String> countHotels = new HashSet<String>();
		Set<String> countRestaruants =  new HashSet<String>();
		int countExperiences =  0;
		int countPhotos =  0;
		Set<String> countCountries =  new HashSet<String>();
				
		String sourceCityId = null;
		
		if(breakCityWise) {
			// Inactivate the earlier breakup of the trip cities
			List<TripCityDb> tripCities = tripDAO.getTripCities(tripId);
			
			if(!CollectionUtils.isEmpty(tripCities)) {
				for(TripCityDb tripCity : tripCities) {
					tripCity.setStatus("I");
					tripCity.setUpdateDate(new Date());
					tripDAO.updateTripCityWise(tripCity);
				}
			}
			
			if(null != trip && !CollectionUtils.isEmpty(trip.getSubTrips())) {
					for(com.domain.triplived.trip.dto.SubTrip st : trip.getSubTrips()) {
								if(null != st.getToCityDTO() && st.getToCityDTO().getCityType().equalsIgnoreCase(TripCityType.Source.toString())) { 
									sourceCityId = st.getToCityDTO().getCityId();
									break;
								}
					}
					for(com.domain.triplived.trip.dto.SubTrip st : trip.getSubTrips()) {
						
						if(null != st.getToCityDTO() && st.getToCityDTO().getCityType().equalsIgnoreCase(TripCityType.Destination.toString())) { 
							TripCityDb tripCityDb = new TripCityDb();
							tripCityDb.setFromCityId(sourceCityId);
							tripCityDb.setToCityId(st.getToCityDTO().getCityId());
							tripCityDb.setTripId(tripId);
							tripCityDb.setStatus("A");
							tripCityDb.setUpdateDate(new Date());
							tripDAO.updateTripCityWise(tripCityDb);
							
							//TODO get total countries
							
							countCountries.add("1");
							countCities.add(st.getToCityDTO().getCityId());
						}
						
					}
			 }
		}
		
		//code to break entities
		if(breakEntityWise){
			
			//inactivate the previous breakup of entries of this trip.
			/*List<TripEntityDb> entities = tripEntitydao.getTripEntities(tripId);
			if(!CollectionUtils.isEmpty(entities)) {
				for(TripEntityDb entity : entities) {
					entity.setStatus("I");
					entity.setUpdateDate(new Date());
					tripEntitydao.save(entity);
				}
			}*/
			
				for(com.domain.triplived.trip.dto.SubTrip st : trip.getSubTrips()) {
					List<Event> listOfEvents = st.getToCityDTO().getEvents();
					if(!CollectionUtils.isEmpty(listOfEvents)){
						
						Date createDate= new Date();
						for(Event eventlist : listOfEvents){
							
							//Add event names with id in concerned table
							
									TripEntityDb tripEntity = new TripEntityDb();
									if(null != eventlist.getSid() && StringUtils.isNumeric(eventlist.getSid())) {
										tripEntity = tripEntitydao.getTripEntity(Long.parseLong(eventlist.getSid()));
									}
									tripEntity.setCreatedDate(createDate);
									tripEntity.setUpdateDate(createDate);
									tripEntity.setEntityId(eventlist.getId());
									tripEntity.setEntityTimestamp(eventlist.getTimestamp());
									tripEntity.setEntityType(eventlist.getType());
									tripEntity.setTripId(Long.parseLong(trip.getTripId()));
									tripEntity.setEntityCityCode(st.getToCityDTO().getCityId());
									tripEntity.setEntityName(eventlist.getName());
									tripEntity.setStatus("A");
									
									if("hotel".equalsIgnoreCase(eventlist.getType())) {
										countHotels.add(eventlist.getName());
									}else if("restaurant".equalsIgnoreCase(eventlist.getType())) {
										countRestaruants.add(eventlist.getName());
									}else if("attraction".equalsIgnoreCase(eventlist.getType())) {
										countAttractions.add(eventlist.getName());
									}else if("memory".equalsIgnoreCase(eventlist.getType())) {
										
										if(CollectionUtils.isNotEmpty(eventlist.getUpdates())){
											if(eventlist.getUpdates().get(0) != null){
												String type = (String) eventlist.getUpdates().get(0).getAdditionalProperties().get("type");
												if(StringUtils.isNotEmpty(type) && "activity".equals(type) ){
													countActivities++;
												}else{
													countExperiences++;	
												}
											}
										}
										
										if(CollectionUtils.isNotEmpty(eventlist.getUpdates())){
											
											for(Update update :  eventlist.getUpdates()){
												String type = (String) update.getAdditionalProperties().get("type");
												if(StringUtils.isNotEmpty(type) && "activity".equals(type) ){
													countActivities++;
												}else{
													countExperiences++;	
												}
											}
										}
										
									}else if("activity".equalsIgnoreCase(eventlist.getType())) {
										countActivities++;
									}
									
									if(!"memory".equalsIgnoreCase(eventlist.getType())){
										
										if(CollectionUtils.isNotEmpty(eventlist.getUpdates())){
											
											for(Update update :  eventlist.getUpdates()){
												String type = (String) update.getAdditionalProperties().get("type");
												if(StringUtils.isNotEmpty(type) && "activity".equals(type) ){
													countActivities++;
												}else{
													countExperiences++;	
												}
												
												if(CollectionUtils.isNotEmpty(update.getFiles())) {
													countPhotos += update.getFiles().size();
												}
											}
										}
									}
									
									
									Map<String, Object> addprop = eventlist.getAdditionalProperties();
									if(addprop != null){
										for (Entry<String, Object> entry : addprop.entrySet()) {
											//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
											if(entry.getKey().equalsIgnoreCase("latitude")){
												tripEntity.setEntityLat(Double.parseDouble(entry.getValue().toString()));
											}
											if(entry.getKey().equalsIgnoreCase("longitude")){
												tripEntity.setEntityLng(Double.parseDouble(entry.getValue().toString()));
											}
										}// end of for
									}//end of if(addprop)
									
									//SAVE Photos
									List<File> files = new ArrayList<File>();
									files.addAll(eventlist.getFiles());
									
									//In case of Memory, all images are stored under Updates
									if(eventlist.getType().equalsIgnoreCase("Memory")) {
										if(CollectionUtils.isNotEmpty(eventlist.getUpdates())) {
											for(Update update : eventlist.getUpdates()) {
												if(CollectionUtils.isNotEmpty(update.getFiles())) {
													files.addAll(update.getFiles());
												}
											}
										}
									}
									
									if(CollectionUtils.isNotEmpty(files)){
										countPhotos += files.size();
										Set<TripEntityMediaDb> tripMedia = new HashSet<TripEntityMediaDb>();
										
										for(File imageFile : files){
											
											if(null == imageFile.getUrl()) {
												continue;//Since there is no path, no point in storing this info.
											}
											String id = imageFile.getUrl().replaceAll(".*/", "").replaceAll("\\..*", "");
											
											TripEntityMediaDb media = tripEntityMediadao.getTripMediaEntity(id);
											if(null == media) {
												media = new TripEntityMediaDb();
												media.setCreatedDate(createDate);
											}
											
											media.setUpdateDate(new Date());
											//media.setEntityName(eventlist.getName());
											media.setEntityType(eventlist.getType());
											media.setCaption(imageFile.getDesc());
											media.setEntityId(eventlist.getId());
											media.setMediaId(id);
											media.setEntityTimestamp(Long.parseLong(media.getMediaId().split("-")[0]));
											
											setLatLongForMedia(imageFile, media);
													
											
											media.setMediaPath(imageFile.getUrl());
											media.setOthers("");
											//if(StringUtils.isNotEmpty(imageFile.getStatus())){
												//Needs to be changed when public/private is done
												//media.setStatus(imageFile.getStatus());
												media.setStatus("A");
											//}
											media.setTripId(Long.parseLong(trip.getTripId()));
											media.setMediaType(imageFile.getType()); 
											media.setTripEntityDb(tripEntity);
											
											tripMedia.add(media);
										}
										tripEntity.setTripMedia(tripMedia);
									}
						        	TripEntityDb  ent = tripEntitydao.saveOrUpdate(tripEntity);
									eventlist.setSid(ent.getId().toString());
							
							}//end for loop for entities
					}
					com.domain.triplived.trip.dto.TripTransportDTO transport = st.getTripTransportDTO();
					if(null != transport) {
						List<File> files = transport.getFiles();
						if(CollectionUtils.isNotEmpty(files)) {
							countPhotos +=files.size();
							for(File imageFile : files){
								if(null == imageFile.getUrl()) {
									continue;//Since there is no path, no point in storing this info.
								}
								String id = imageFile.getUrl().replaceAll(".*/", "").replaceAll("\\..*", "");
								TripEntityMediaDb media = tripEntityMediadao.getTripMediaEntity(id);
								if(null == media) {
									media = new TripEntityMediaDb();
									media.setMediaId(id);
									media.setCreatedDate(new Date());
									media.setUpdateDate(new Date());
								}else {
									media.setUpdateDate(new Date());
								}
								
								media.setEntityType("Transport");
								media.setCaption(imageFile.getDesc());
								
								setLatLongForMedia(imageFile, media);
								media.setMediaPath(imageFile.getUrl());
								media.setOthers("");
								media.setStatus("A");
								media.setTripId(Long.parseLong(trip.getTripId()));
								media.setMediaType(imageFile.getType());
								media.setEntityTimestamp(Long.parseLong(media.getMediaId().split("-")[0]));
								tripEntitydao.updateTripMedia(media);
							}
					   }
					}//end of transport if
					
					com.domain.triplived.trip.dto.ToCityDTO destCity = st.getToCityDTO();
					if(null != destCity) {
						List<File> files = destCity.getFiles();
						if(CollectionUtils.isNotEmpty(files)) {
							countPhotos +=files.size();
							for(File imageFile : files){
								if(null == imageFile.getUrl()) {
									continue;//Since there is no path, no point in storing this info.
								}
								
								String id = imageFile.getUrl().replaceAll(".*/", "").replaceAll("\\..*", "");
								TripEntityMediaDb media = tripEntityMediadao.getTripMediaEntity(id);
								if(null == media) {
									media = new TripEntityMediaDb();
									media.setMediaId(id);
									media.setCreatedDate(new Date());
									media.setUpdateDate(new Date());
								}else {
									media.setUpdateDate(new Date());
								}
								
								media.setEntityType("City");
								media.setCaption(imageFile.getDesc());
								
								
								setLatLongForMedia(imageFile, media);
								
								media.setMediaPath(imageFile.getUrl());
								media.setOthers("");
								media.setStatus("A");
								media.setTripId(Long.parseLong(trip.getTripId()));
								media.setMediaType(imageFile.getType());
								media.setEntityTimestamp(Long.parseLong(media.getMediaId().split("-")[0]));
								tripEntitydao.updateTripMedia(media);
							}
					   }
					}//end of city if
				}//end of for subTrip
		}

		Map<TripMetaData, Integer> entityCountMap = new HashMap<>();
		entityCountMap.put(TripMetaData.ACTIVITIES, countActivities);
		entityCountMap.put(TripMetaData.ATTRACTIONS, countAttractions.size());
		entityCountMap.put(TripMetaData.CITIES, countCities.size());
		entityCountMap.put(TripMetaData.COUNTRIES, countCountries.size());
		entityCountMap.put(TripMetaData.EXPERIENCES, countExperiences);
		entityCountMap.put(TripMetaData.HOTELS, countHotels.size());
		entityCountMap.put(TripMetaData.RESTARUANTS, countRestaruants.size());
		entityCountMap.put(TripMetaData.PHOTOS, countPhotos);
		entityCountMap.put(TripMetaData.TRIP_SUBMITTED, countTrip);
		
		fireTripSubmitEvent(trip, entityCountMap);
	}

	private void setLatLongForMedia(File imageFile, TripEntityMediaDb media) {
		if(StringUtils.isNotEmpty(imageFile.getLatitude()) && !(imageFile.getLatitude().equalsIgnoreCase("NaN"))){
			try{
				media.setLatitude(Double.parseDouble(imageFile.getLatitude()));
			}catch(Exception ex) {
				media.setLatitude(999.0);
			}
		}else{
			media.setLatitude(999.0);
		}
		
		if(StringUtils.isNotEmpty(imageFile.getLongitude()) && !(imageFile.getLongitude().equalsIgnoreCase("NaN"))){
			try{
				media.setLongitude(Double.parseDouble(imageFile.getLongitude()));
			}catch(Exception ex) {
				media.setLongitude(999.0);
			}
		}else{
			media.setLongitude(999.0);
		}
	}
	
	@Override
	@Transactional
	public String addReviewsOfEvents(String tripId) throws Exception {
		
		logger.warn("Trip reviews and experiences: Breaking for tripId - {}", tripId);
		TripDb tripDb = tripDAO.getTripById(Long.parseLong(tripId));
		Gson gson = new Gson();
		TimelineTrip trip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
		/*
		if(!checkForValidTripEntities(trip)) {
			return "There are custom user entities in the trip. Please curate the trip before it can be processed further";
		}*/
		
		for(com.domain.triplived.trip.dto.SubTrip st : trip.getSubTrips()) {
			List<Event> listOfEvents = st.getToCityDTO().getEvents();
			if(!CollectionUtils.isEmpty(listOfEvents)){
				for(Event eventlist : listOfEvents){			
				
				
				if(eventlist.getType().equalsIgnoreCase("Attraction")) {	
					TripAttractionDetailsDb tripEntity = new TripAttractionDetailsDb();
					if(null != eventlist.getSid()) {
						tripEntity = tripDAO.getTripAttractionEntityBySid(Long.parseLong(eventlist.getSid()));
						if(null == tripEntity) {
							tripEntity = new TripAttractionDetailsDb();
							tripEntity.setTripEntityId(Long.parseLong(eventlist.getSid()));
						}
					}else {
						continue;
					}
					tripEntity.setCreatedDate(new Date());
					tripEntity.setUpdateDate(new Date());
					tripEntity.setAttractionId(eventlist.getId());
					
					if(!CollectionUtils.isEmpty(eventlist.getReviews())) {
						tripEntity.setReview(eventlist.getReviews().get(0).getReview());
						tripEntity.setSuggestion(eventlist.getReviews().get(0).getReview());
					}
					
					tripEntity.setTimeStamp(eventlist.getTimestamp());
					tripEntity.setCurated(RequiredStatus.valueOf("N"));
					
					if(null != eventlist.getAdditionalProperties()) {
						Map<String , Object> eventMap = eventlist.getAdditionalProperties();
						if(eventMap.containsKey("recommendation")) {
							String recom = eventMap.get("recommendation").toString();
							if(recom.equalsIgnoreCase("recommended")) {
								tripEntity.setRecommended(RequiredStatus.valueOf("Y"));
							}
							if(recom.equalsIgnoreCase("not_recommended")) {
								tripEntity.setRecommended(RequiredStatus.valueOf("N"));
							}
							if(recom.equalsIgnoreCase("unselected")) {
								tripEntity.setRecommended(RequiredStatus.valueOf("U"));
							}
						}
						if(eventMap.containsKey("guides_required")) {
							String guideReq = eventMap.get("guides_required").toString();
							if(guideReq.equalsIgnoreCase("yes")) {
							 tripEntity.setGuideRequired(RequiredStatus.valueOf("Y"));
							}
							if(guideReq.equalsIgnoreCase("no")) {
								 tripEntity.setGuideRequired(RequiredStatus.valueOf("N"));
							}
							if(guideReq.equalsIgnoreCase("unselected")) {
								 tripEntity.setGuideRequired(RequiredStatus.valueOf("U"));
							}
						}
						if(eventMap.containsKey("visit_time")) {
							tripEntity.setVisitTime(eventMap.get("visit_time").toString());
						}
						if(eventMap.containsKey("suggestion")) {
							tripEntity.setReview(eventMap.get("suggestion").toString());
							//tripEntity.setSuggestion(eventMap.get("suggestion").toString());
						}
					}
					
					tripEntity.setTripId(Long.parseLong(trip.getTripId()));
					
					tripEntity.setStatus("A");
					tripDAO.updateTripAttractionDetails(tripEntity);
					logger.warn("Trip reviews and experiences: Done for attraction with sid - {} for tripId - {}", eventlist.getSid(), tripId);
					
				  }else if(eventlist.getType().equalsIgnoreCase("Hotel")) {	
						TripHotelDetailsDb tripEntity = new TripHotelDetailsDb();
						
						if(null != eventlist.getSid()) {
							tripEntity = tripDAO.getTripHotelEntityBySid(Long.parseLong(eventlist.getSid()));
							if(null == tripEntity) {
								tripEntity = new TripHotelDetailsDb();
								tripEntity.setTripEntityId(Long.parseLong(eventlist.getSid()));
							}
						}else {
							continue;
						}
						
						tripEntity.setCreatedDate(new Date());
						tripEntity.setUpdateDate(new Date());
						tripEntity.setHotelId(eventlist.getId());
						if(!CollectionUtils.isEmpty(eventlist.getReviews())) {
							tripEntity.setReview(eventlist.getReviews().get(0).getReview());
							//tripEntity.setSuggestion(eventlist.getReviews().get(0).getReview());
						}
						
						tripEntity.setTimestamp(eventlist.getTimestamp());
						tripEntity.setCurated(RequiredStatus.valueOf("N"));
						
						if(null != eventlist.getAdditionalProperties()) {
							Map<String , Object> eventMap = eventlist.getAdditionalProperties();
							if(eventMap.containsKey("recommendation")) {
								String recom = eventMap.get("recommendation").toString();
								if(recom.equalsIgnoreCase("recommended")) {
									tripEntity.setRecommended(RequiredStatus.valueOf("Y"));
								}
								if(recom.equalsIgnoreCase("not_recommended")) {
									tripEntity.setRecommended(RequiredStatus.valueOf("N"));
								}
								if(recom.equalsIgnoreCase("unselected")) {
									tripEntity.setRecommended(RequiredStatus.valueOf("U"));
								}
							}
							if(eventMap.containsKey("recommendation_reason")) {
								tripEntity.setRecommendedReason(eventMap.get("recommendation_reason").toString());
							}
							if(eventMap.containsKey("suggestion")) {
								tripEntity.setReview(eventMap.get("suggestion").toString());
								//tripEntity.setSuggestion(eventMap.get("suggestion").toString());
							}
						}
						
						tripEntity.setTripId(Long.parseLong(trip.getTripId()));
						
						tripEntity.setStatus("A");
						tripDAO.updateTripHotelDetails(tripEntity);
						logger.warn("Trip reviews and experiences: Done for hotel with sid - {} for tripId - {}", eventlist.getSid(), tripId);
						
					  }else if(eventlist.getType().equalsIgnoreCase("Memory")) {	
							TripExperiencesDb tripEntity = new TripExperiencesDb();
							if(null != eventlist.getSid()) {
								tripEntity = tripDAO.getTripExperienceBySid(Long.parseLong(eventlist.getSid()));
								if(null == tripEntity) {
									tripEntity = new TripExperiencesDb();
									tripEntity.setTripEntityId(Long.parseLong(eventlist.getSid()));
									tripEntity.setCreatedDate(new Date());
								}else {
									tripEntity.setUpdateDate(new Date());
								}
							}else {
								continue;
							}
							
							
							if(!CollectionUtils.isEmpty(eventlist.getUpdates())) {
								tripEntity.setExperience(eventlist.getUpdates().get(0).getMessage());
							}else {
								continue;
							}
							
							tripEntity.setTimeStamp(eventlist.getTimestamp());
							
							if(null != eventlist.getAdditionalProperties()) {
								Map<String , Object> eventMap = eventlist.getAdditionalProperties();
								if(eventMap.containsKey("location-type")) {
									String eventType = eventMap.get("location-type").toString();
									if(eventType.equalsIgnoreCase("no_location")) {
										tripEntity.setEntityType("CITY");
										tripEntity.setEntityId(st.getToCityDTO().getCityId());
									}else {
										tripEntity.setEntityType(eventType.toUpperCase());
										tripEntity.setEntityId(eventlist.getId());
									}
								}else {
									continue;
								}
							}
							
							tripEntity.setTripId(Long.parseLong(trip.getTripId()));
							
							tripEntity.setStatus("A");
							tripDAO.updateTripExperience(tripEntity);
							logger.warn("Trip reviews and experiences: Done for memory with sid - {} for tripId - {}", eventlist.getSid(), tripId);
							
						  }
			    }//inner for
		     }
		}//outer for
		
		return "Successfully done";
		
	}
	
	/**
	 * This method checks if any entity in a trip is custom. If yes, it returns false, telling the user to first curate and link 
	 * all entities to our database.
	 * @param trip
	 * @return
	 */
	public Boolean checkForValidTripEntities(TimelineTrip trip) {
		
		for(com.domain.triplived.trip.dto.SubTrip st : trip.getSubTrips()) {
			List<Event> listOfEvents = st.getToCityDTO().getEvents();
			if(!CollectionUtils.isEmpty(listOfEvents)){
				for(Event event : listOfEvents){
					if(null != event.getId()) {
						if(event.getId().contains("CUSTOM_")) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	@Transactional(readOnly=false)
	@Override
	public void createUserTrip(Long personId, Long tripId) {
		TripUserDb tripDb = new TripUserDb();
		
		tripDb.setTripId(tripId);
		tripDb.setUserId(personId);
		tripDb.setComplete(false);
		
		tripDAO.updateUserTrip(tripDb);
	}
	
	@Transactional(readOnly=false)
	@Override
	public String updateTripPublicId(Long tripId) {

		TripDb tripDb = tripDAO.getTrip(tripId);
		try {
		if(null != tripDb) {
			String randomPublicId = TripLivedUtil.generatePublicTripId(tripId);
			tripDb.setTripPublicId(Long.parseLong(randomPublicId));
			tripDAO.updateTrip(tripDb);
			logger.warn("Trip public id for trip - {} is {}", tripId, randomPublicId);
			return randomPublicId;
		}
		}catch(Exception e) {
			logger.error("ERROR : while generating trip public id for trip - {}", tripId);
		}
		return tripId.toString();
	}
	
	
	
	private void sortEvents(Trip trip) {
		
		if(!CollectionUtils.isEmpty(trip.getSubTrips())) {
			for(SubTrip subTrip : trip.getSubTrips()) {
				if(null != subTrip.getToCityDTO()) {
					TripCityDTO toCityDto = subTrip.getToCityDTO();
					if(!CollectionUtils.isEmpty(toCityDto.getEvents())) {
						List<TripEventDTO> events = toCityDto.getEvents();
						Collections.sort(events);
					}
				}
			}
		}
	}

	@Transactional(readOnly=false)
	@Override
	public SubTrip getActiveSubTrip(Long tripId, Trip trip, String status) {
		TripDb tripdB = tripDAO.getTrip(tripId);
		SubTrip activeSubTrip = null;
		List<SubTrip> subTrips = trip.getSubTrips();
		if(!CollectionUtils.isEmpty(subTrips)) {
			for(SubTrip sTrip : subTrips) {
				if(!sTrip.getComplete()) {
						if(null != status && status.equalsIgnoreCase("new")) {
							sTrip.setComplete(true);
							Gson gson = new Gson();
							
							tripdB.setTripData(gson.toJson(trip));
							tripDAO.updateTrip(tripdB);
						}else {
							activeSubTrip = sTrip;
						}
					}
				}
		}
		return activeSubTrip;
		
	}
	
	@Transactional(readOnly=false)
	@Override
	public Long getTripIdByEmail(Long email) {
		TripUserDb tripUser = tripDAO.getTripIdByEmail(email);
		if(tripUser == null) {
			return null;
		} else {
			return tripUser.getTripId();
			
		}
	}
	
	
	/**
	 * Use this method instead of getTrip
	 */
	@Transactional(readOnly=false)
	@Override
	public TimelineTrip getTripForShare(Long tripId, String requestorId) {
		
		
		TripDb tripDb = tripDAO.getTripById(tripId);
		
		Gson gson = new Gson();
		TimelineTrip trip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
		
		/*if(null != tripDb.getVideoServerPath()) {
			trip.setTripVideoUrl(tripDb.getVideoServerPath());
		}*/
		if(null != tripDb.getVideoYoutubePath() && null != tripDb.getVideoYoutubeStatus() && 
				tripDb.getVideoYoutubeStatus().equalsIgnoreCase("A")) {
			trip.setTripVideoUrl(tripDb.getVideoYoutubePath());
		}
		
		populateSocialDetails(tripId, trip);
		
		
		
		if(null != requestorId) {
			try {
				Map<String, Object> additionalMap = trip.getAdditionalProperties();
				if(null != additionalMap) {
					String editUrl = userService.isTripEditableByUser(requestorId, tripId.toString());
					additionalMap.put("editUrl", editUrl);	
					additionalMap.put("source", tripDb.getTripSource());
				}
				
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				logger.error("Error while checking if user is authorized to edit trip. User - {}, trip - {}", requestorId, tripId);
				e.printStackTrace();
			}
			
		}
		
		Long userId = getUserIdByTripId(tripId);
		PersonDb person = userService.loadUserDetails(userId.toString());
		User user = new User();
		String name = person.getName() ;
		if(StringUtils.isNotEmpty(person.getLastName())){
			name = name + " "+person.getLastName();
		}
		user.setName(name);
		user.setUserId(Integer.parseInt(userId.toString()));
		
		Map<String, Object> additionalMap = user.getAdditionalProperties();
		additionalMap.put("userFbId", person.getId());
		
		if(null != tripDb.getTripCategory()) {
			String categories = tripDb.getTripCategory();
			if(!StringUtils.isEmpty(categories)) {
				List<String> categoryList = Arrays.asList(categories.split(","));
				trip.setTripCategories(categoryList);
			}
		}
		
		trip.setUser(user);
		
		trip.setTripId(tripDb.getTripPublicId().toString());
		return trip;
	}

	private void populateSocialDetails(Long tripId, TimelineTrip trip) {
		
		Map<String, Object> additionalMap = trip.getAdditionalProperties();	
		if(additionalMap == null){
			additionalMap = new HashMap<String, Object>();
		}
		
		//Trip Likes
		Long tripLikes = tripDAO.getTripLikes(tripId);
		if(null != tripLikes) {
			additionalMap.put("likes", tripLikes);
		}
		
		//Trip Comments
		Long tripCommentsCount = tripDAO.getTripCommentsCount(tripId);
		if(null != tripCommentsCount) {
			additionalMap.put("comments", tripCommentsCount);
		}

		//Trip Photos
		int mediaCount = 0;
		for(com.domain.triplived.trip.dto.SubTrip subTrip : trip.getSubTrips()) {
			
			com.domain.triplived.trip.dto.TripTransportDTO transportDTO = subTrip.getTripTransportDTO();
			if(transportDTO != null && CollectionUtils.isNotEmpty( transportDTO.getFiles())){
				mediaCount += transportDTO.getFiles().size();
			}
			
			ToCityDTO cityDto = subTrip.getToCityDTO();
			
			if(cityDto != null){
				List<Event> events = cityDto.getEvents();
				
				//add images from city
				if(CollectionUtils.isNotEmpty(cityDto.getFiles())){
					mediaCount += cityDto.getFiles().size();
				}
				
				if(CollectionUtils.isNotEmpty(events)){
					for (Event event : events) {
						if(CollectionUtils.isNotEmpty(event.getFiles())){
							mediaCount += event.getFiles().size();
						}
						
						if(CollectionUtils.isNotEmpty(event.getUpdates())){
							
							for (Update update : event.getUpdates()){
								if(CollectionUtils.isNotEmpty(update.getFiles())){
									mediaCount += update.getFiles().size();
								}
							}
						}
					}
				}	
			}
		}
		
		if(null != tripCommentsCount) {
			additionalMap.put("media", mediaCount);
		}
		
	}
	
	/**
	 * Use this method instead of getTrip
	 */
	@Transactional(readOnly=false)
	@Override
	public TimelineTrip getTripById(Long tripId, String requestorId) {
		
		TimelineTrip trip = getTripForShare(tripId, requestorId);
		return trip;
	}
	
	@Transactional(readOnly=false)
	@Override
	public Long getUserIdByTripId(Long tripId) {
		TripUserDb tripUserDb = tripDAO.getUserIdByTripId(tripId);
		if(null != tripUserDb) {
			return tripUserDb.getUserId();
		}
		return null;
	}
	
	
	@Transactional(readOnly=false)
	@Override
	public String getTripNameById(Long tripId) {
		TripDb tripDb = tripDAO.getTripById(tripId);
		return tripDb.getTripName();
	}
	
	@Override
	@Transactional
	public Boolean saveTimelineTrip(TimelineTrip timelineTrip, String deviceId) {
		
		String tripId = timelineTrip.getTripId();
		
		Gson gson = new Gson();
		TripDb tripDb = tripDAO.getTripById(Long.parseLong(tripId));
		
		if(null == tripDb) {
			tripDb = new TripDb();
			tripDb.setId(Long.parseLong(tripId));
		}
		
		String timelineTripStr = gson.toJson(timelineTrip);
		
		tripDb.setTripName(timelineTrip.getTripName());
		tripDb.setTripType("TIMELINE");
		tripDb.setTripData(timelineTripStr);
		tripDb.setTripCoverUri(timelineTrip.getTripCoverUri());
		
		if(!CollectionUtils.isEmpty(timelineTrip.getTripCategories())) {
			StringBuilder categoryStr = new StringBuilder();
			int count = 1;
			for(String str : timelineTrip.getTripCategories()) {
				categoryStr.append(str);
				if(count++ != timelineTrip.getTripCategories().size()) {
					categoryStr.append(",");
				}
			}
			tripDb.setTripCategory(categoryStr.toString());
		}
		
		
		tripDb.setUpdateDate(new Date());
		tripDAO.updateTrip(tripDb);
		populateTimelineTripData(timelineTrip, true, true);
		
		String editedTimeline = gson.toJson(timelineTrip);
		tripDb.setTripData(editedTimeline);
		tripDb.setTripDataEdited(editedTimeline);
		tripDAO.updateTrip(tripDb);
		
		TripHistoryDb history = new TripHistoryDb();
		history.setUpdateDate(new Date());
		history.setTripData(tripDb.getTripData());
		history.setTripId(tripDb.getId());
		history.setTripPublicId(tripDb.getTripPublicId());
		history.setSubmitType("OWNER");
		tripDAO.updateTripHistory(history);
		
		return true;
	}

	//TODO pass populateTimelineTrip data from here also
	@Override
	@Transactional
	public Boolean saveTimelineTripCreatedFromWeb(TimelineTrip timelineTrip, String deviceId) {
		
		String tripId = timelineTrip.getTripId();
		
		Gson gson = new Gson();
		TripDb tripDb = tripDAO.getTripById(Long.parseLong(tripId));
		
		if(null == tripDb) {
			tripDb = new TripDb();
			tripDb.setId(Long.parseLong(tripId));
		}
		
		String timelineTripStr = gson.toJson(timelineTrip);
		
		tripDb.setTripName(timelineTrip.getTripName());
		tripDb.setTripType("TIMELINE");
		tripDb.setTripData(timelineTripStr);
		tripDb.setTripCoverUri(timelineTrip.getTripCoverUri());
		
		if(!CollectionUtils.isEmpty(timelineTrip.getTripCategories())) {
			StringBuilder categoryStr = new StringBuilder();
			int count = 1;
			for(String str : timelineTrip.getTripCategories()) {
				categoryStr.append(str);
				if(count++ != timelineTrip.getTripCategories().size()) {
					categoryStr.append(",");
				}
			}
			tripDb.setTripCategory(categoryStr.toString());
		}
		
		
		tripDb.setUpdateDate(new Date());
		tripDAO.updateTrip(tripDb);
		/*populateTimelineTripData(timelineTrip, true, true);
		
		String editedTimeline = gson.toJson(timelineTrip);
		tripDb.setTripData(editedTimeline);
		tripDb.setTripDataEdited(editedTimeline);
		tripDAO.updateTrip(tripDb);
		
		TripHistoryDb history = new TripHistoryDb();
		history.setUpdateDate(new Date());
		history.setTripData(tripDb.getTripData());
		history.setTripId(tripDb.getId());
		history.setTripPublicId(tripDb.getTripPublicId());
		history.setSubmitType("OWNER");
		tripDAO.updateTripHistory(history);*/
		
		return true;
	}

	
	

	@Override
	@Transactional
	public Boolean saveTimelineTripForReview(TimelineTrip timelineTrip, String deviceId) {
		
		String tripId = timelineTrip.getTripId();
		
		Gson gson = new Gson();
		
		Long userId = getUserIdByTripId(Long.parseLong(tripId));
		PersonDb person = userService.loadUserDetails(userId.toString());
		
		TripDb tripDb = tripDAO.getTripById(Long.parseLong(tripId));
		tripDb.setTripName(timelineTrip.getTripName());
		//tripDb.setTripType("TIMELINE_REVIEW");
		tripDb.setTripData(gson.toJson(timelineTrip));
		tripDb.setTripDataEdited(gson.toJson(timelineTrip));
		tripDb.setTripCoverUri(timelineTrip.getTripCoverUri());
		
		if(!CollectionUtils.isEmpty(timelineTrip.getTripCategories())) {
			StringBuilder categoryStr = new StringBuilder();
			int count = 1;
			for(String str : timelineTrip.getTripCategories()) {
				categoryStr.append(str);
				if(count++ != timelineTrip.getTripCategories().size()) {
					categoryStr.append(",");
				}
			}
			tripDb.setTripCategory(categoryStr.toString());
		}
		
		try {
			Long publicId = getPublicTripIdById(Long.parseLong(tripId));
			userTripReviewMail.sendMail(publicId.toString(), timelineTrip.getTripName(), person.getName());
			}catch(Exception e) {
				logger.error("Exception while getting public id for sending trip review mail - {}", tripId);
		}
		
		
		tripDb.setUpdateDate(new Date());
		tripDAO.updateTrip(tripDb);
		
		populateTimelineTripData(timelineTrip, true, true);
		tripDb.setTripData(gson.toJson(timelineTrip));
		tripDb.setTripDataEdited(gson.toJson(timelineTrip));
		tripDAO.updateTrip(tripDb);
		
		try {
			addReviewsOfEvents(tripDb.getId().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Trip reviews and experiences: Error while breaking trip for reviews for tripId - {}. Exception is - {}", tripDb.getId().toString(),e);
		}
		
		TripHistoryDb history = new TripHistoryDb();
		history.setUpdateDate(new Date());
		history.setTripData(tripDb.getTripData());
		history.setTripId(tripDb.getId());
		history.setTripPublicId(tripDb.getTripPublicId());
		history.setSubmitType("REVIEWER");
		tripDAO.updateTripHistory(history);
		
		return true;
	}
	
	//Method to save events in another table
	@Override
	@Transactional
	public Boolean saveEventsFromUserTrip(Long tripId, String deviceId,Boolean flag) {
		TripDb tripDb = tripDAO.getTripById(tripId);
		Gson gson = new Gson();
		 TimelineTrip timelineTrip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
		populateTimelineTripData(timelineTrip,false,flag);
		return true;
	}
	
	//Method to save cities in another table
		@Override
		@Transactional
		public Boolean saveCitiesFromUserTrip(Long tripId, String deviceId,Boolean flag) {
			TripDb tripDb = tripDAO.getTripById(tripId);
			Gson gson = new Gson();
			 TimelineTrip timelineTrip = gson.fromJson(tripDb.getTripData(), TimelineTrip.class);
			populateTimelineTripData(timelineTrip,flag,false);
			return true;
		}
	
	@Override
	@Transactional
	public String getLikeOfUser(Long tripId, Long userId, String like){
		
		TripLikesDb obj = tripDAO.getLikeOfUser(tripId, userId);
		if(null == like) {
			if(null != obj) {
				if(obj.getStatus().equalsIgnoreCase("A")) {
					return "You have already liked this trip";
				} else {
					return null;
				}
			}else {
				return null;
			}
		} else {
			if(obj == null) {
				TripLikesDb newObj = new TripLikesDb();
				newObj.setTripId(tripId);
				newObj.setUserId(userId);
				newObj.setStatus(like);
				newObj.setUpdateDate(new Date());
				newObj.setTimeStamp(System.currentTimeMillis() / 1000L);
				
				TripUserDb tripUser = tripDAO.getUserIdByTripId(tripId);
				newObj.setTripUserId(tripUser.getUserId());
				
				tripDAO.updateTripLike(newObj);
				try {
					
					//Lets not send Like notification to same user
					if(userId != tripUser.getUserId()){
						gcmService.sendTripLikeNotification(tripId.toString(), userId.toString());
					}
					
					fireLikeEvent(userId, tripId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error while sending trip like notification for trip -{} by user -{} . Exception is - {}", tripId, userId, e.getStackTrace());
				}
				return "Updated";
			} else {
				//return "You have already liked this trip";
				if(like.equalsIgnoreCase("A") && null != obj.getStatus() && obj.getStatus().equals(like)) {
					return "You have already liked this trip";
				}else if(like.equalsIgnoreCase("I") && null != obj.getStatus() && obj.getStatus().equals(like)) {
					return "You have already unliked this trip";
				}else if(like.equalsIgnoreCase("A") || like.equalsIgnoreCase("I")){
					obj.setStatus(like);
					obj.setUpdateDate(new Date());
					obj.setTimeStamp(System.currentTimeMillis() / 1000L);
					tripDAO.updateTripLike(obj);
					return "Updated";
				}else {
					return null;
				}
			}
		}
	}
	
	@Override
	@Transactional
	public String addCommentsOnTrip(Long tripId, Long userId, String comment){
		
				TripCommentsDb newObj = new TripCommentsDb();
				newObj.setTripId(tripId);
				newObj.setUserId(userId);
				newObj.setStatus("A");
				newObj.setUpdateDate(new Date());
				newObj.setComment(comment);
				newObj.setTimeStamp(System.currentTimeMillis() / 1000L);
				
				TripUserDb tripUser = tripDAO.getUserIdByTripId(tripId);
				newObj.setTripUserId(tripUser.getUserId());
				
				tripDAO.updateTripComment(newObj);
				List<TripCommentDTO> tripComments = getCommentsOnTrip(tripId);
				Set<Long> usersToSendNotification = new HashSet<Long>();
				
				if(userId != tripUser.getUserId()){
					usersToSendNotification.add(tripUser.getUserId());	
				}
				
				try {
					for(TripCommentDTO obj : tripComments) {
						if(userId != Long.parseLong(obj.getUserId())){
							usersToSendNotification.add(Long.parseLong(obj.getUserId()));	
						}
				}
				gcmService.sendTripCommentNotification(tripId.toString(), userId.toString(), usersToSendNotification);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error while adding comment to trip - {} by user - {} . Exception is - {}", tripId, userId, e.getStackTrace());
				}
				
				fireCommentEvent(userId, tripId);
				return "Updated-"+newObj.getId();
	}
	
	private void fireLikeEvent(Long userId, Long tripId){
		TlEvent event = getEvent(userId, tripId, TripMetaData.LIKES);
		apputils.getHandler("likeHandler").setState(event);

	}
	
	@Override
	public void fireTripSubmitEvent(TimelineTrip trip, Map<TripMetaData, Integer>  tripStats){
		
		Long userId = getUserIdByTripId(Long.parseLong(trip.getTripId()));
	
		TlEvent event =  new TlEvent(); //getEvent(userId, tripId, TripMetaData.TRIP);
		event.setUser(userId);
		event.setVerb(TripMetaData.TRIP_SUBMITTED.toString());
		event.setActionDetails(trip.getTripId());
		event.setBody(tripStats);
		event.addToHeader(TlEvent.TYPE, TripMetaData.TRIP_SUBMITTED);
		apputils.getHandler("tripSubmitHandler").setState(event);

	}
	
	/**
	 * @param tripMedia
	 */
	private void fireCommentEvent(Long userId, Long tripId) {
		TlEvent event = getEvent(userId, tripId, TripMetaData.COMMENTS);
		apputils.getHandler("commentHandler").setState(event);
	}
	
	private TlEvent getEvent(Long userId, Long tripId, TripMetaData type){
		
		TlEvent event = new TlEvent();
		event.setUser(userId);
		event.setVerb(type.toString());
		event.setActionDetails(tripId+"");
		event.addToHeader(TlEvent.TYPE, type);
		
		return event;
	}
	
	@Override
	@Transactional
	public String addSharesOfTrip(Long tripId, Long userId){
		
				TripShareDb newObj = new TripShareDb();
				newObj.setTripId(tripId);
				newObj.setUserId(userId);
				newObj.setStatus("A");
				newObj.setUpdateDate(new Date());
				newObj.setTimeStamp(System.currentTimeMillis() / 1000L);
				
				TripUserDb tripUser = tripDAO.getUserIdByTripId(tripId);
				newObj.setTripUserId(tripUser.getUserId());
				
				tripDAO.updateTripShare(newObj);
				
				try {
					gcmService.sendTripShareNotification(tripId.toString(), userId.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error while sharing trip - {} by user - {} . Exception is - {}", tripId, userId, e.getStackTrace());
				}
				return "Updated";
	}
	
	
	@Override
	@Transactional
	public List<TripCommentDTO> getCommentsOnTrip(Long tripId){
		
		List<Object[]> tripCommentsList = tripDAO.getCommentsOnTrip(tripId);
		List<TripCommentDTO> tripComments = new ArrayList<TripCommentDTO>(tripCommentsList.size());
		if(!CollectionUtils.isEmpty(tripCommentsList)) {
			
			for(Object[] oArr : tripCommentsList) {
				
				TripCommentDTO obj = new TripCommentDTO();
				
				if(null != oArr[0]) {
					obj.setUserId(oArr[0].toString());
				}
				if(null != oArr[1]) {
					//obj.setDateTime(oArr[1].toString());
				}
				if(null != oArr[2]) {
					obj.setComment(oArr[2].toString());
				}
				if(null != oArr[3]) {
					obj.setUserName(oArr[3].toString());
				}
				if(null != oArr[4]) {
					obj.setUserFbId(oArr[4].toString());
				}
				if(null != oArr[5]) {
					obj.setTimeStamp(oArr[5].toString());
				}
				if(null != oArr[6]) {
					obj.setCommentId(oArr[6].toString());
				}
				tripComments.add(obj);
			}
			
			
			return tripComments;
		}else {
			return null;
		}
				
	}
	
	@Override
	@Transactional
	public List<TripSearchDTO> getTripsByDestCity(String toCityId) {
		Gson gson = new Gson();
		List<Object[]> objArr = tripDAO.getTripsByDestCity(toCityId);
		
		List<TripSearchDTO> list = new ArrayList<TripSearchDTO>();
		for(Object[] oArr : objArr) {
			TripSearchDTO obj = new TripSearchDTO();
			if(null != oArr[0]) {
				obj.setTripId(oArr[0].toString());
			}
			if(null != oArr[1]) {
				obj.setTripName(oArr[1].toString());
			}
			if(null != oArr[2]) {
				obj.setTripUri(oArr[2].toString());
			}
			if(null != oArr[3]) {
				obj.setUserFbId(oArr[3].toString());
			}
			if(null != oArr[4]) {
				obj.setUserId(oArr[4].toString());
			}
			if(null != oArr[5]) {
				obj.setUserName(oArr[5].toString());
			}
			Long tripLikes = tripDAO.getTripLikes(Long.parseLong(obj.getTripId()));
			if(null != tripLikes && !(tripLikes == 0)) {
				obj.setTripLikes(tripLikes.toString());
			}
			
			if(null != oArr[6]) {
				TimelineTrip tripObj = gson.fromJson(oArr[6].toString(), TimelineTrip.class);
				List<com.domain.triplived.trip.dto.SubTrip> subTrips = tripObj.getSubTrips();
	    		List<CityResponseDTO> allStops = new ArrayList<CityResponseDTO>();
	    		int subTripCounter = 0;
	    		Long startTime = null, endTime = null;
	    		
	    		for(com.domain.triplived.trip.dto.SubTrip s : subTrips) {
	    			
	    			if(subTripCounter == 0) {
	    				if(null != s.getToCityDTO().getCityType() && 
	    						s.getToCityDTO().getCityType().equalsIgnoreCase("source")) {
	    					startTime = s.getToCityDTO().getTimestamp();
	    				}
	    			}
	    			if(subTripCounter == (subTrips.size() - 1)) {
	    				if(null != s.getToCityDTO().getCityType() && 
	    						s.getToCityDTO().getCityType().equalsIgnoreCase("destination")) {
	    					endTime = s.getToCityDTO().getTimestamp();
	    				}
	    			}
	    			
	    			if(null != s.getToCityDTO()) {
	    				CityResponseDTO city = new CityResponseDTO();
	    				city.setCityName(s.getToCityDTO().getCityName());
	    				city.setType(s.getToCityDTO().getCityType());
	    				allStops.add(city);
	    			}
	    			subTripCounter++;
	    		}
	    		obj.setTripCities(allStops);
	    		
	    		if(null != startTime && null != endTime) {
					long diff = Math.round((endTime - startTime)/(double) 86400000);
					if(diff > 1) {
						obj.setTripDuration(diff + " days");
					}else {
						obj.setTripDuration("Single day trip");
					}
				}
			}
			//Adding public id
			if(null != oArr[7]) {
				obj.setTripId(oArr[7].toString());
			}
			
			list.add(obj);
		}
		return list;
	}
	
	
	
	@Override
	@Transactional
	public Boolean sendTripsForVideoGeneration(TimelineTrip trip) {

				return null;
	}
	
	@Override
	public List<TripSearchDTO> getLikedTripsOfUser(String personId) {
		List<Object[]> userTrips = tripDAO.getLikedTripsOfUser(Long.parseLong(personId));
		List<TripSearchDTO> likedTripsOfUser = convertLikedTrips(userTrips);
		
		return likedTripsOfUser;
	}
	
	private List<TripSearchDTO> convertLikedTrips(List<Object[]> trips) {
		
		Gson gson = new Gson();
		List<TripSearchDTO> tripList = new ArrayList<TripSearchDTO>();
		for(Object[] oArr : trips) {
			TripSearchDTO trip = new TripSearchDTO();
			if(null != oArr[0]) {
				trip.setOldTripId(oArr[0].toString());
				trip.setTripId(oArr[0].toString());
			}
			if(null != oArr[1]) {
				trip.setTripName(oArr[1].toString());
			}
			if(null != oArr[2]) {
				trip.setTripUri(oArr[2].toString());
			}
			if(null != oArr[3]) {
				
				TimelineTrip tripObj = gson.fromJson(oArr[3].toString(), TimelineTrip.class);
				List<com.domain.triplived.trip.dto.SubTrip> subTrips = tripObj.getSubTrips();
	    		List<CityResponseDTO> allStops = new ArrayList<CityResponseDTO>();
	    		for(com.domain.triplived.trip.dto.SubTrip s : subTrips) {
	    			if(null != s.getToCityDTO()) {
	    				CityResponseDTO city = new CityResponseDTO();
	    				city.setCityName(s.getToCityDTO().getCityName());
	    				city.setType(s.getToCityDTO().getCityType());
	    				allStops.add(city);
	    			}
	    		}
	    		trip.setTripCities(allStops);
			}
			if(null != oArr[4]) {
				trip.setUserFbId(oArr[4].toString());
			}
			if(null != oArr[5]) {
				trip.setUserId(oArr[5].toString());
			}
			if(null != oArr[6]) {
				trip.setUserName(oArr[6].toString());
			}
			if(null != oArr[7]) {
				String categories = oArr[7].toString();
				trip.setCategories(Arrays.asList(categories.split(",")));
			}
			
			try {
				TripSearchDTO obj = tripClient.getTripDetails(trip.getTripId());
				if(null != obj) {
					trip.setTripLikes(obj.getTripLikes());
					trip.setTripComments(obj.getTripComments());
					trip.setTripImages(obj.getTripImages());
				}
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Adding the public id
			if(null != oArr[8]) {
				trip.setTripId(oArr[8].toString());
			}
			
			tripList.add(trip);
		}
		return tripList;
	}

	
	@Override
	@Transactional
	public void publishVideoStatus(Long tripId, VideoStatus status) {

		TripVideoDb tripVideoDb = new TripVideoDb();
		List<TripVideoDb> details = tripVideoDao.getTripVideoDetails(tripId);
		for(TripVideoDb video : details) {
			if(video.getStatus().getVideoStatus().equals(status.getVideoStatus())) {
				tripVideoDb = video;
			}
		}
		tripVideoDb.setTripId(tripId);
		tripVideoDb.setvideoStatus(status);
		tripVideoDb.setUpdateDate(new Date());
		tripVideoDao.updateTripVideo(tripVideoDb);
	}
	
	@Override
	@Transactional
	public Boolean updateVideoServerPath(String tripId, String serverPath) {
		TripDb tripDb = tripDAO.getTrip(Long.parseLong(tripId));
		tripDb.setVideoServerPath(serverPath);
		tripDAO.updateTrip(tripDb);
		return true;
	}
	
	@Override
	@Transactional
	public Boolean updateVideoYoutubePath(String tripId, String youtubePath) {
		TripDb tripDb = tripDAO.getTrip(Long.parseLong(tripId));
		tripDb.setVideoYoutubePath(youtubePath);
		tripDb.setVideoYoutubeStatus("I");
		tripDAO.updateTrip(tripDb);
		return true;
	}
	
	@Override
	@Transactional
	public Long getTripIdByPublicId(Long publicTripId){
		TripDb trip = tripDAO.getTripByPublicId(publicTripId);
		return trip.getId();
	}
	
	@Override
	@Transactional
	public Long getPublicTripIdById(Long tripId){
		TripDb trip = tripDAO.getTrip(tripId);
		return trip.getTripPublicId();
	}
	
	@Override
	@Transactional
	@Async
	public Future<Boolean> pushOtherDetails(TimelineTrip trip) {
		  try {
		    logger.warn("Trip Push Other Details: Now running async code for trip - {}",trip.getTripId());
		    logger.warn("Trip Push Other Details: Breaking trip reviews and experiences for trip - {}",trip.getTripId());
		    
		    try {	
		    	addReviewsOfEvents(trip.getTripId());
			    logger.warn("Trip Push Other Details: Trip reviews and experiences broken for trip - " + trip.getTripId());
			}catch(Exception e) {
				logger.error("Trip Push Other Details: Exception while breaking trip for reviews and experiences for trip - {} - {}", trip.getTripId(), e);
			}
	    
		    logger.warn("Trip Push Other Details: Now going for video generation for trip - {}",trip.getTripId());
	    
		    		    
	    publishVideoStatus(Long.parseLong(trip.getTripId()), VideoStatus.valueOf(VideoStatus.TRIP_PUBLISH.toString()));
		Long userId = getUserIdByTripId(Long.parseLong(trip.getTripId()));
		PersonDb person = userService.loadUserDetails(userId.toString());
		
		String personEmail = null;
		if(!CollectionUtils.isEmpty(person.getUserAccounts())) {
			Set<UserAccountsDb> accSet = person.getUserAccounts();
			for (UserAccountsDb accObj : accSet) {
				if(null != accObj) {
					if(null != accObj.getEmail()) {
						personEmail = person.getEmail();
						break;
					}
				}
			}
			
		}
		
		try {
		Long publicId = getPublicTripIdById(Long.parseLong(trip.getTripId()));
		String personName = person.getName();
		if(StringUtils.isNotEmpty(person.getLastName())) {
			personName = person.getName() + " " + person.getLastName();
		}
		videoGenerationRequestMail.sendMail(publicId.toString(), trip.getTripName(), personName, personEmail);
		}catch(Exception e) {
			logger.error("Exception while getting public id for sending trip submit mail - {}", trip.getTripId());
		}
		
		 //publishVideoStatus(trip.getTripId(), VideoStatus.MAIL_SENT.toString());
		 publishVideoStatus(Long.parseLong(trip.getTripId()), VideoStatus.valueOf(VideoStatus.MAIL_SENT.toString()));
		
		logger.warn("Trip Push Other Details: Returning true after trip Push Other Details for trip - {}",trip.getTripId());
	  }catch(Exception e) {
		  logger.error("Trip Push Other Details: Error - {}",e);
	  }
		  
	  return new AsyncResult<Boolean>(true);
		
	}
	
	@Override
	@Transactional
	public List<TripDb> getAllTripsOfTypeTimeline() {
		List<TripDb> trips = tripDAO.getAllValidTrips();
		return trips;
	} 
	
	@Override													
	public List<TripFaqDb> getTripFaqList(Long tripId) {
		List<TripFaqDb> list = tripDAO.getTripFaqList(tripId);
		return list;
	}
	
	@Override
	public List<TripKitListDb> getTripKitList(Long tripId) {
		List<TripKitListDb> list = tripDAO.getTripKitList(tripId);
		return list;
	}
	
	@Override
	@Transactional
	public String createTripFromWebExperiences(Long tripId, TripSummaryDTO tripSummary) {
		
		//get all Web Experiences. This will be the long trip id
		List<WebExperiencesDb> experienceList = experienceDAO.getWebExperiencesByTripId(Long.parseLong(tripSummary.getTripId()));
		Collections.sort(experienceList);
		
		TimelineTrip trip = new TimelineTrip();
		trip.setTripId(tripId.toString());
		trip.setTripName("Web Trip");
		if(null != tripSummary.getCoverUri()) {
			trip.setTripCoverUri(tripSummary.getCoverUri());
		}else {
			trip.setTripCoverUri("http://www.triplived.com/static/triplived/rdTrPh/trip1.jpg");
		}
		
		/*String prevCity = null; // Required to create a new SubTrip
		com.domain.triplived.trip.dto.SubTrip prevSubTrip = null;
		*/
		com.domain.triplived.trip.dto.SubTrip sb = null;
	    ToCityDTO toCity = null;
	    
		for(WebExperiencesDb obj : experienceList) {
			
			if((null != obj.getStatus()) && (obj.getStatus().equalsIgnoreCase("I"))) {
				continue;
			}
			
			if(CollectionUtils.isNotEmpty(trip.getSubTrips())) {
				sb = trip.getSubTrips().get(trip.getSubTrips().size() - 1);
			} 
			
			//if((prevCity == null) || (prevCity.equalsIgnoreCase(obj.getCity()))) {
			//if((sb == null) || (sb != null && !sb.getToCityDTO().getCityName().equalsIgnoreCase(obj.getCity()))) {
				
				if(sb == null) {
					sb = new com.domain.triplived.trip.dto.SubTrip();
					toCity = new ToCityDTO();
					toCity.setCityName(obj.getCity());
					toCity.setCityId(obj.getCityId());
					toCity.setCityType("Destination");
					
					List<Event> events = new ArrayList<Event>();
					toCity.setEvents(events);
					
					sb.setToCityDTO(toCity);
					
					trip.getSubTrips().add(sb);
				}
				
			//}
			
			    List<Event> events = sb.getToCityDTO().getEvents();
			    
			    Event eventParent = new Event();
			    eventParent.setType(obj.getEntityType());
			    eventParent.setId(obj.getExperienceId());
			    eventParent.setTimestamp(obj.getTimeStamp());
			    eventParent.setCityId(obj.getCityId());
			    eventParent.setCityName(obj.getCity());
			    eventParent.setName(obj.getLocation());
				events.add(eventParent);
			    
				Event event = new Event();
				event.setType("memory");
				event.setId(obj.getExperienceId());
				event.setTimestamp(obj.getTimeStamp());
				event.setSid(obj.getExperienceId());
				event.setCityId(obj.getCityId());
				event.setCityName(obj.getCity());
				event.setName(obj.getLocation());
				
				List<Update> updates = new ArrayList<Update>();
				Update update = new Update();
				update.setMessage(obj.getExperience());
				update.setTime(obj.getTimeStamp());
				
				List<File> files = new ArrayList<File>();
				if(CollectionUtils.isNotEmpty(obj.getMedia())) {
					for(WebExperiencesMediaDb media : obj.getMedia()) {
						if(media.getStatus().equalsIgnoreCase("A")) {
							File file = new File();
							file.setUrl("http://triplived.com/static/" + media.getMediaPath());
							files.add(file);
						}
					}
				}
				update.setFiles(files);
				
				update.setAdditionalProperty("type", "status");
				if(StringUtils.isNotEmpty(obj.getEmotion())) {
					update.setAdditionalProperty("emotion_new", obj.getEmotion());
				}
				if(StringUtils.isNotEmpty(obj.getActivity())) {
					update.setAdditionalProperty("activity_new",obj.getActivity());
				}
				updates.add(update);
				event.setUpdates(updates);
				
				
				events.add(event);
			
		}
		
		for(com.domain.triplived.trip.dto.SubTrip sbObj : trip.getSubTrips()) {
			if(sbObj != null) {
				sbObj.getToCityDTO().setTimestamp(sbObj.getToCityDTO().getEvents().get(0).getTimestamp());
			}
		}
		
		//Save summary data
		trip.setTripName(tripSummary.getTripName());
		trip.getAdditionalProperties().put("tripSummary", tripSummary.getSummary());
		trip.getAdditionalProperties().put("given_src_name", tripSummary.getSrcCity());
		
		if(StringUtils.isNotEmpty(tripSummary.getCategory())) {
			String[] categories = tripSummary.getCategory().split(",");
		
			trip.setTripCategories(Arrays.asList(categories));
		}
		
		
		saveTimelineTripCreatedFromWeb(trip, null);
		return null;
	}
	
	@Override
	@Transactional
	public String updateTripStateFromWeb(String tripId,String status,String userId) {
		
		if(null != status && status.equalsIgnoreCase("I")) {
			TripUserDb tripUser = tripDAO.getUserIdByTripId(Long.parseLong(tripId));
			if(null != tripUser.getUserId()) {
				if(tripUser.getUserId().toString().equalsIgnoreCase(userId)) {
					TripDb trip = tripDAO.getTrip(Long.parseLong(tripId));
					if(null != trip) {
						trip.setTripState(status);
						tripDAO.updateTrip(trip);
						return "Done";
					}
				}else {
					return "Not authorized";
				}
			}
		}
		return null;
	}
}
