package com.triplived.service.experience.impl;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.WebExperienceDTO;
import com.connectme.domain.triplived.dto.WebImageDTO;
import com.triplived.dao.experience.ExperienceDAO;
import com.triplived.entity.WebExperiencesDb;
import com.triplived.entity.WebExperiencesMediaDb;
import com.triplived.service.experience.ExperienceService;

@Service
public class ExperienceServiceImpl implements ExperienceService {
	
	@Autowired
	private ExperienceDAO experienceDao;
	
	@Override
	@Transactional
	public WebExperienceDTO createOrUpdateExperience(String experienceId,String tripId,String location,String emotion,
			String timestamp, String description, String entityType, String cityId,
			String cityName, String orderNo, String day, String userId, String activity) {
		
		try {
			// Check if experience exists
			WebExperiencesDb obj = experienceDao
					.getWebExperienceById(experienceId);
			if (null == obj) {
				obj = new WebExperiencesDb();
				obj.setExperienceId(experienceId);
				obj.setCreatedDate(new Date());
			}
			obj.setTripId(Long.parseLong(tripId));
			obj.setLocation(location);
			obj.setEmotion(emotion);
			obj.setActivity(activity);
			obj.setTimeStamp(Long.parseLong(timestamp));
			obj.setExperience(description);
			obj.setEntityType(entityType);
			obj.setCity(cityName);
			obj.setCityId(cityId);
			obj.setUserId(Long.parseLong(userId));

			obj.setUpdateDate(new Date());
			experienceDao.updateWebExperience(obj);

			List<WebExperiencesDb> experiences = experienceDao.getWebExperiencesByTripId(Long.parseLong(tripId));
			Collections.sort(experiences);
			Long previousTs = experiences.get(0).getTimeStamp();
			Long currentDay = 1l;

			for (WebExperiencesDb objFrom : experiences) {

				int diffDays = Days.daysBetween(new LocalDate(previousTs),
						new LocalDate(objFrom.getTimeStamp())).getDays();
				currentDay += diffDays;
				previousTs = objFrom.getTimeStamp();

				if (objFrom.getExperienceId().equalsIgnoreCase(experienceId)) {
					WebExperienceDTO objTo = new WebExperienceDTO();
					objTo.setExperienceId(objFrom.getExperienceId());
					objTo.setExperience(objFrom.getExperience());
					objTo.setTripId(objFrom.getTripId());
					objTo.setEmotion(objFrom.getEmotion());
					objTo.setActivity(objFrom.getActivity());
					objTo.setEntityType(objFrom.getEntityType());
					objTo.setLocation(objFrom.getLocation());
					objTo.setDay(currentDay);
					objTo.setOrderNo(objFrom.getOrderNo());
					objTo.setTimeStamp(objFrom.getTimeStamp());
					objTo.setCreatedDate(objFrom.getCreatedDate());
					objTo.setUpdateDate(objFrom.getUpdateDate());

					// create heading
					String heading = objTo.getLocation();
					if (StringUtils.isNotEmpty(objTo.getEmotion())) {
						heading = "Was feeling " + objTo.getEmotion();
						if (StringUtils.isNotEmpty(objTo.getActivity())) {
							heading = "Was feeling " + objTo.getEmotion()
									+ " while " + objTo.getActivity();
						}
					} else {
						if (StringUtils.isNotEmpty(objTo.getActivity())) {
							heading = objTo.getActivity();
						}
					}
					objTo.setHeading(heading);

					if (null != objTo.getExperience()) {
						if (objTo.getExperience().length() > 125) {
							objTo.setShortDescription(objTo.getExperience()
									.substring(0, 125));
						} else {
							objTo.setShortDescription(objTo.getExperience());
						}
					}

					Set<WebImageDTO> images = new HashSet<WebImageDTO>();
					if (CollectionUtils.isNotEmpty(objFrom.getMedia())) {
						for (WebExperiencesMediaDb media : objFrom.getMedia()) {
							if (media.getStatus().equalsIgnoreCase("A")) {
								WebImageDTO file = new WebImageDTO();
								file.setUrl(media.getMediaPath());
								file.setName(media.getMediaId());
								file.setMedium(media.getMedium());
								file.setSmall(media.getSmall());
								images.add(file);
							}
						}
					}
					objTo.setMedia(images);

					DateFormatSymbols dfs = new DateFormatSymbols(
							Locale.getDefault());
					String weekdays[] = dfs.getWeekdays();
					String months[] = dfs.getMonths();
					String[] am_pm = { "AM", "PM" };

					Calendar cal = Calendar.getInstance(TimeZone
							.getTimeZone("IST"));
					cal.setTimeInMillis(objTo.getTimeStamp());
					int weekDayNo = cal.get(Calendar.DAY_OF_WEEK);
					int monthNumber = cal.get(Calendar.MONTH);
					int date = cal.get(Calendar.DATE);

					String cityTime = cal.get(Calendar.HOUR_OF_DAY) + ":"
							+ cal.get(Calendar.MINUTE) + " "
							+ am_pm[cal.get(Calendar.AM_PM)];
					String readableDate = weekdays[weekDayNo] + ", "
							+ months[monthNumber] + " " + date + " , "
							+ cityTime;
					objTo.setReadableDate(readableDate);

					return objTo;
				}
			}
		 return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	@Transactional
	public WebExperienceDTO createOrUpdateTransportExperience(String experienceId, String sourceExperienceId,
			String destinationExperienceId, String transportType, String tripId, String description, String userId, String ts) {
		
		try {
		//Check if experience exists
		WebExperiencesDb obj = experienceDao.getWebExperienceById(experienceId);
		if(null == obj) {
			obj = new WebExperiencesDb();
			obj.setExperienceId(experienceId);
			obj.setCreatedDate(new Date());
		}
		obj.setTripId(Long.parseLong(tripId));
		obj.setExperience(description);
		obj.setEntityType("TRANSPORT");
		obj.setTransportType(transportType);
		obj.setTimeStamp(Long.parseLong(ts));
		obj.setUserId(Long.parseLong(userId));
		obj.setSourceExperienceId(sourceExperienceId);
		obj.setDestExperienceId(destinationExperienceId);
		
		
		obj.setUpdateDate(new Date());
		experienceDao.updateWebExperience(obj);
		
		List<WebExperiencesDb> experiences = experienceDao.getWebExperiencesByTripId(Long.parseLong(tripId));
		Collections.sort(experiences);
		Long previousTs = experiences.get(0).getTimeStamp();
		Long currentDay = 1l;
		
		for(WebExperiencesDb objFrom : experiences) {
			
		  int diffDays =  Days.daysBetween(new LocalDate(previousTs) , new LocalDate(objFrom.getTimeStamp()) ).getDays(); 
		  currentDay += diffDays;
		  previousTs = objFrom.getTimeStamp();
			
		  if(objFrom.getExperienceId().equalsIgnoreCase(experienceId)) {
			//WebExperiencesDb objFrom = experienceDao.getWebExperienceById(experienceId);
			WebExperienceDTO objTo = new WebExperienceDTO();
		    objTo.setExperienceId(objFrom.getExperienceId());
		    objTo.setExperience(objFrom.getExperience());
		    objTo.setTripId(objFrom.getTripId());
		    objTo.setEntityType(objFrom.getEntityType());
		    objTo.setCreatedDate(objFrom.getCreatedDate());
		    objTo.setUpdateDate(objFrom.getUpdateDate());
		    objTo.setTransportType(objFrom.getTransportType());
		    objTo.setUserId(objFrom.getUserId());
		    objTo.setTimeStamp(objFrom.getTimeStamp());
		    objTo.setSourceExperienceId(objFrom.getSourceExperienceId());
		    objTo.setDestExperienceId(objFrom.getDestExperienceId());
		    objTo.setDay(currentDay);
		    
		    if(null != objTo.getExperience()) {
		    	if(objTo.getExperience().length() > 125) {
		    		objTo.setShortDescription(objTo.getExperience().substring(0, 125));
		    	}else {
		    		objTo.setShortDescription(objTo.getExperience());
		    	}
		    }
		    
		    Set<WebImageDTO> images = new HashSet<WebImageDTO>();
		    if(CollectionUtils.isNotEmpty(objFrom.getMedia())) {
			    for(WebExperiencesMediaDb media : objFrom.getMedia()) {
			    	if(media.getStatus().equalsIgnoreCase("A")) {
			    		WebImageDTO file = new WebImageDTO();
				    	file.setUrl(media.getMediaPath());
				    	file.setName(media.getMediaId());
				    	file.setMedium(media.getMedium());
				    	file.setSmall(media.getSmall());
				    	images.add(file);
			    	}
			    }
		    }
		    objTo.setMedia(images);
		    
		    DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
			String weekdays[] = dfs.getWeekdays();
			String months[] = dfs.getMonths();
			String[] am_pm = {"AM","PM"};
			
		    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		    cal.setTimeInMillis(objTo.getTimeStamp());
			int weekDayNo = cal.get(Calendar.DAY_OF_WEEK);
			int monthNumber = cal.get(Calendar.MONTH);
			int date = cal.get(Calendar.DATE);
			
			String cityTime =  cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " " + am_pm[cal.get(Calendar.AM_PM)];
			String readableDate = weekdays[weekDayNo] + ", " + months[monthNumber] + " " + date + " , " +  cityTime;
			objTo.setReadableDate(readableDate);
			return objTo;
		  }
		}
		
	    return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	
	@Override
	@Transactional
	public List<WebExperienceDTO> getWebExperiencesByTripId(String tripId){
		//get all Web Experiences of a trip
		List<WebExperiencesDb> experienceList = experienceDao.getWebExperiencesByTripId(Long.parseLong(tripId));
		Collections.sort(experienceList);
		
		List<WebExperienceDTO> experiences = new ArrayList<WebExperienceDTO>();
		
		if(CollectionUtils.isNotEmpty(experienceList)) {
			for(WebExperiencesDb objFrom : experienceList) {
				
				if((null != objFrom.getStatus()) && (objFrom.getStatus().equalsIgnoreCase("I"))) {
					continue;
				}
			    WebExperienceDTO objTo = new WebExperienceDTO();
			    objTo.setExperienceId(objFrom.getExperienceId());
			    objTo.setExperience(objFrom.getExperience());
			    objTo.setTripId(objFrom.getTripId());
			    objTo.setEmotion(objFrom.getEmotion());
			    objTo.setActivity(objFrom.getActivity());
			    objTo.setEntityType(objFrom.getEntityType());
			    objTo.setLocation(objFrom.getLocation());
			    objTo.setDay(objFrom.getDay());
			    objTo.setOrderNo(objFrom.getOrderNo());
			    objTo.setTimeStamp(objFrom.getTimeStamp());
			    objTo.setCreatedDate(objFrom.getCreatedDate());
			    objTo.setUpdateDate(objFrom.getUpdateDate());
			    objTo.setTransportType(objFrom.getTransportType());
			    objTo.setUserId(objFrom.getUserId());
			    objTo.setSourceExperienceId(objFrom.getSourceExperienceId());
			    objTo.setDestExperienceId(objFrom.getDestExperienceId());
			    
			    Set<WebImageDTO> images = new HashSet<WebImageDTO>();
			    if(CollectionUtils.isNotEmpty(objFrom.getMedia())) {
				    for(WebExperiencesMediaDb media : objFrom.getMedia()) {
				     if(media.getStatus().equalsIgnoreCase("A")) {
				    	WebImageDTO file = new WebImageDTO();
				    	file.setUrl(media.getMediaPath());
				    	file.setName(media.getMediaId());
				    	images.add(file);
				     }
				    }
			    }
			    
			    if(null != objTo.getExperience()) {
			    	if(objTo.getExperience().length() > 125) {
			    		objTo.setShortDescription(objTo.getExperience().substring(0, 125));
			    	}else {
			    		objTo.setShortDescription(objTo.getExperience());
			    	}
			    }
			    
			    if(!objTo.getEntityType().equalsIgnoreCase("TRANSPORT")) {
				    String heading = objTo.getLocation();
					if (StringUtils.isNotEmpty(objTo.getEmotion())) {
						heading = "Was feeling " + objTo.getEmotion();
						if (StringUtils.isNotEmpty(objTo.getActivity())) {
							heading = "Was feeling " + objTo.getEmotion()
									+ " while " + objTo.getActivity();
						}
					} else {
						if (StringUtils.isNotEmpty(objTo.getActivity())) {
							heading = objTo.getActivity();
						}
					}
					objTo.setHeading(heading);
			    }
			    objTo.setMedia(images);
			    
			    DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
				String weekdays[] = dfs.getWeekdays();
				String months[] = dfs.getMonths();
				String[] am_pm = {"AM","PM"};
				
			    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
			    cal.setTimeInMillis(objTo.getTimeStamp());
				int weekDayNo = cal.get(Calendar.DAY_OF_WEEK);
				int monthNumber = cal.get(Calendar.MONTH);
				int date = cal.get(Calendar.DATE);
				
				String cityTime =  cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + " " + am_pm[cal.get(Calendar.AM_PM)];
				String readableDate = weekdays[weekDayNo] + ", " + months[monthNumber] + " " + date + " , " +  cityTime;
				objTo.setReadableDate(readableDate);
				
			    experiences.add(objTo);
			}
		}
		
		
		return experiences;
	}
	
	@Override
	@Transactional
	public String addWebExperienceMedia(String mediaId, String path, String experienceId, Boolean smallVer, Boolean mediumVer){
		
	    /*String result = null;
	    if (path != null) {
	        result = "";
	        if (path.lastIndexOf('/') != -1) {
	            result = path.substring(path.lastIndexOf('/'));
	            if (result.startsWith("/")) {
	                result = result.substring(1);
	            }
	        }
	    }*/
	    //String mediaId = result.split("\\.")[0];
	    String mediaPath = path;
	    //String experienceId = mediaId.split("-")[1] + "-" + mediaId.split("-")[2];
	    String tripId = experienceId.split("-")[0];
	    String mediaType = "image";
	    Date createdDate = new Date();
	    String caption = "Image";
	    
	   
	    
	    WebExperiencesMediaDb obj = new WebExperiencesMediaDb();
	    obj.setMediaId(mediaId);
	    obj.setMediaPath(mediaPath);
	    obj.setTripId(Long.parseLong(tripId));
	    obj.setCreatedDate(createdDate);
	    obj.setMediaType(mediaType);
	    obj.setCaption(caption);
	    obj.setStatus("A");
	    obj.setSmall(smallVer);
	    obj.setMedium(mediumVer);
	    
	    /*//Checks if the media status was set to I by the time media was uploaded and this call came
	    WebExperiencesMediaDb media = experienceDao.getWebExperiencesMediaById(mediaId);
		if(null != media && media.getStatus().equalsIgnoreCase("I")) {
			return "This media is inactive. Hence, cannot update";
		}*/
		
	    WebExperiencesDb exp = experienceDao.getWebExperienceById(experienceId);
	    if(null == exp) {
	    	exp = new WebExperiencesDb();
	    	exp.setExperienceId(experienceId);
	    	exp.setCreatedDate(new Date());
		    experienceDao.updateWebExperience(exp);
		}
	    
	    obj.setWebExperiencesDb(exp);
	    
	    
	    experienceDao.updateWebExperienceMedia(obj);
	    
	    return "Done";
		
	}
	
	@Override
	@Transactional
	public String updateWebExperienceMedia(String mediaId, String path, String experienceId, String status, String id){
		
		/*String result = null;
	    if (path != null) {
	        result = "";
	        if (path.lastIndexOf('/') != -1) {
	            result = path.substring(path.lastIndexOf('/'));
	            if (result.startsWith("/")) {
	                result = result.substring(1);
	            }
	        }
	    }*/
	    //String mediaId = result.split("\\.")[0];
	    /*String mediaPath = path;
	    String experienceId = mediaId.split("-")[1] + "-" + mediaId.split("-")[2];
	    String tripId = experienceId.split("-")[0];
	    */
	    
		WebExperiencesMediaDb media = experienceDao.getWebExperiencesMediaById(mediaId);
		if(null != media) {
			media.setStatus(status);
			experienceDao.updateWebExperienceMedia(media);
		}
		return "true";
		
	}
	
	@Override
	@Transactional
	public  String updateExperienceStatus(String experienceId, String tripId, String status) {
		
		WebExperiencesDb obj = experienceDao.getWebExperienceById(experienceId);
		if(null != obj) {
			obj.setStatus("I");
			experienceDao.updateWebExperience(obj);
			return "true";
		}
		return "false";
		
	}

}
