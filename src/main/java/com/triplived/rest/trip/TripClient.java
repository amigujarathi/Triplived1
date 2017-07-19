package com.triplived.rest.trip;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.TrendingTripDTO;
import com.connectme.domain.triplived.dto.TripBetweenCityRestClientDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.TripTrendingDb;
import com.triplived.entity.TripTrendingExceptionDb;

@Component
public class TripClient {
	
	@Autowired
	private TripDAO tripDao;
	
	@Value("${getTripBetweenCitiesUrl}")
	private String getTripBetweenCitiesUrl;
	
	@Value("${tripUrl}")
	private String tripUrl;
	
	@Value("${profileType}")
	private String profileType;
	
	public List<TripBetweenCityRestClientDTO> getTripBetweenCities(String sourceCityCode, String destCityCode) throws MalformedURLException, SolrServerException {
	    HttpSolrServer solr = new HttpSolrServer(getTripBetweenCitiesUrl);
	    SolrQuery query = new SolrQuery();
	   
	    List<TripBetweenCityRestClientDTO> trips = new ArrayList<TripBetweenCityRestClientDTO>();
	    String queryStr = "source:"+sourceCityCode + " AND " + "destination:" + destCityCode;
	    query.setQuery(queryStr);
	    
	    query.setRows(1000);
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results1 = response.getResults();
	    
	    
	    populateResult(trips, results1);
	    
	    String queryStr2 = "-source:"+sourceCityCode + " AND " + "destination:" + destCityCode;
	    query.setQuery(queryStr);
	    
	    query.setRows(1000);
	    QueryResponse response2 = solr.query(query);
	    SolrDocumentList results2 = response.getResults();
	    populateResult(trips, results2);
	    
	    return trips;
  }
	
	private void populateResult(List<TripBetweenCityRestClientDTO> trips, SolrDocumentList results) {
		for (SolrDocument document : results) {
			TripBetweenCityRestClientDTO trip = new TripBetweenCityRestClientDTO();
		       if(document.getFieldValue("id") != null) {
		    	   trip.setId(document.getFieldValue("id").toString());
		       }
		       if(document.getFieldValue("source") != null) {
		    	   trip.setSourceCityCode(document.getFieldValue("source").toString());
		       }
		       if(document.getFieldValue("destination") != null) {
		    	   trip.setDestinationCityCode(document.getFieldValue("destination").toString());
		       }
		       if(document.getFieldValue("tripData") != null) {
		    	   trip.setTripData(document.getFieldValue("tripData").toString());
		       }
		       if(document.getFieldValue("userName") != null) {
		    	   trip.setUserName(document.getFieldValue("userName").toString());
		       }
		       trips.add(trip);
	    }
	}
	
	/**
	 * Algo to update trending trips
	 * 1 point for like, 7 for comments & 14 for sharing
	 */
	@Transactional
	@Scheduled(fixedRate = 3600000)
	public void updateTrendingTrips() {
		
	  if(StringUtils.isNotEmpty(profileType) && "prod".equalsIgnoreCase(profileType)) {
		List<TripTrendingExceptionDb> list = tripDao.getAllTrendingExceptionTrips();
		Map<Long,Long> exceptionTripIdList = new HashMap<Long, Long>();
		for(TripTrendingExceptionDb obj : list) {
				exceptionTripIdList.put(obj.getTripId(), obj.getPoints());
		}
		
		tripDao.deleteTrendingTrips();
		
		List<Object[]> tripAndLikes = tripDao.getAllTripsAndLikes();
		List<Object[]> tripAndComments = tripDao.getAllTripsAndCommentsCount();
		
		Map<Long, TripTrendingDb> pointsMap = new HashMap<Long, TripTrendingDb>();
		
		for(Object[] oArr : tripAndLikes) {
			TripTrendingDb obj = new TripTrendingDb();
			obj.setTripId(Long.parseLong(oArr[0].toString()));
			obj.setTripLikeCount(Long.parseLong(oArr[1].toString()));
			pointsMap.put(Long.parseLong(oArr[0].toString()), obj);
		}
		
		for(Object[] oArr : tripAndComments) {
			TripTrendingDb obj = new TripTrendingDb();
			if(pointsMap.containsKey(Long.parseLong(oArr[0].toString()))) {
				obj = pointsMap.get(Long.parseLong(oArr[0].toString()));
			}else {
				obj.setTripId(Long.parseLong(oArr[0].toString()));	
			}
			obj.setTripCommentCount(Long.parseLong(oArr[1].toString()));
			pointsMap.put(Long.parseLong(oArr[0].toString()), obj);
		}
		
		Iterator<Entry<Long, TripTrendingDb>> it = pointsMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        TripTrendingDb obj = (TripTrendingDb) pair.getValue();
	        Long points = calculatePoints(obj.getTripLikeCount(), obj.getTripCommentCount(), null);
	        obj.setPoints(points);
	        obj.setUpdateDate(new Date());
	        obj.setStatus("A");
	        obj.setType("General");
	        if(null != exceptionTripIdList && exceptionTripIdList.containsKey(obj.getTripId())) {
	        	//obj.setPoints(exceptionTripIdList.get(obj.getTripId()));
	        	obj.setType("Exception");
	        	tripDao.updateTrendingTrips(obj);
	        }
	        
	    }
	  }
		
	}
	
	public Long calculatePoints(Long likes, Long comments, Long shares) {
		
		Long points = 0l;
		if(null != likes) {
			points = likes;
		}
		if(null != comments) {
			points += comments * 7;
		}
		if(null != shares) {
			points += shares * 14;	
		}
		
		return points;
	}
	
	
	
	public List<TrendingTripDTO> getTrendingTrips() throws SolrServerException {
		
		List<TrendingTripDTO> tripList = new ArrayList<TrendingTripDTO>();
		List<TrendingTripDTO> tripExceptionList = new ArrayList<TrendingTripDTO>();
		
	    HttpSolrServer solr = new HttpSolrServer(tripUrl);
		SolrQuery query = new SolrQuery();
		  
	    String queryStr = "points:[* TO *]";
	    query.setQuery(queryStr);
	    query.setRows(100);
	    query.setFields("id,public_id,trip_name,trip_cover_uri,city_details,userFbId,userName,userLastName,trip_category,points,likes,from_city_details,userId,comments_tl,images_nos,trending_type,trip_summary,trip_source,web_trip_media");
	    /*
	     * userId
	     * source city
	     * Add trip likes,trip comments, trip share to trip document
	     */
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    
	    for (SolrDocument document : results) {
	    		TrendingTripDTO trip = new TrendingTripDTO();
		       if(document.getFieldValue("userLastName") != null && document.getFieldValue("userName") != null) {
		    	   trip.setUserName(document.getFieldValue("userName").toString() + " " +document.getFieldValue("userLastName").toString());
		       }
		       if(document.getFieldValue("trip_name") != null) {
		    	   trip.setTripName(document.getFieldValue("trip_name").toString());
		       }
		       if(document.getFieldValue("trip_cover_uri") != null) {
		    	   trip.setTripUri(document.getFieldValue("trip_cover_uri").toString());
		       }
		       if(document.getFieldValue("trip_summary") != null) {
		    	   trip.setTripSummary(document.getFieldValue("trip_summary").toString());
		       }
		       if(document.getFieldValue("userFbId") != null) {
		    	   trip.setUserFbId(document.getFieldValue("userFbId").toString());
		       }
		       if(document.getFieldValue("userId") != null) {
		    	   trip.setUserId(document.getFieldValue("userId").toString());
		       }
		       if(document.getFieldValue("id") != null) {
		    	   trip.setOldTripId(document.getFieldValue("id").toString());
		       }
		       if(document.getFieldValue("public_id") != null) {
		    	   trip.setTripId(document.getFieldValue("public_id").toString());
		       }
		       if(document.getFieldValue("likes") != null) {
		    	   trip.setTripLikes(document.getFieldValue("likes").toString());
		       }
		       if(document.getFieldValue("comments_tl") != null) {
		    	   trip.setTripComments(document.getFieldValue("comments_tl").toString());
		       }
		       if(document.getFieldValue("images_nos") != null) {
		    	   trip.setTripImages(document.getFieldValue("images_nos").toString());
		       }
		       if(document.getFieldValue("trip_source") != null && "web".equalsIgnoreCase(document.getFieldValue("trip_source").toString())) {
		    	   if(document.getFieldValue("web_trip_media") != null) {
		    		   trip.setTripImages(document.getFieldValue("web_trip_media").toString());
		    	   }
		       }
		       if(document.getFieldValue("points") != null) {
		    	   trip.setPoints(Long.parseLong(document.getFieldValue("points").toString()));
		       }
		       if(document.getFieldValue("trending_type") != null) {
		    	   trip.setType(document.getFieldValue("trending_type").toString());
		       }
		       if(document.getFieldValue("trip_category") != null) {
		    	   String categories = document.getFieldValue("trip_category").toString();
		    	   if(!StringUtils.isEmpty(categories)) {
		    		   trip.setCategories(Arrays.asList(categories.split(",")));
		    	   }
		       }
		       List<CityResponseDTO> allStops = new ArrayList<CityResponseDTO>();
		       if(document.getFieldValue("from_city_details") != null) {
		    	   List<String> fromCities = (List<String>)document.getFieldValue("from_city_details");
		    	   if(!CollectionUtils.isEmpty(fromCities)) {
		    		  // List<String> fromCityList = Arrays.asList(categories.split(","));
		    		  String fromCity = fromCities.get(0).split(",")[0];
		    		  CityResponseDTO fromCityDTO = new CityResponseDTO();
		    		  fromCityDTO.setCityName(fromCity);
		    		  fromCityDTO.setType("Source");
		    		  allStops.add(fromCityDTO);
		    	   }
		    	}
		       
		       if(document.getFieldValue("city_details") != null) {
		    	   List<String> toCities = (List<String>)document.getFieldValue("city_details");
		    	   if(!CollectionUtils.isEmpty(toCities)) {
		    		  for(String toCity : toCities) {
			    		  String toCityStr = toCity.split(",")[0];
			    		  CityResponseDTO toCityDTO = new CityResponseDTO();
			    		  toCityDTO.setCityName(toCityStr);
			    		  toCityDTO.setType("Destination");
			    		  allStops.add(toCityDTO);
		    		  }
		    	   }
		    	   
		       }
		       trip.setTripCities(allStops);
		       
		       if(document.getFieldValue("trip_category") != null) {
		    	   String categories = document.getFieldValue("trip_category").toString();
		    	   if(!StringUtils.isEmpty(categories)) {
		    		   trip.setCategories(Arrays.asList(categories.split(",")));
		    	   }
		    	}
		       
		       if(null != trip.getType()) {
		    	   if(trip.getType().equalsIgnoreCase("Exception")) {
		    		   tripExceptionList.add(trip);   
		    	   }else {
			    	   tripList.add(trip);   
			       }
		       }
		       
		}

	    Collections.sort(tripList);
	    Collections.sort(tripExceptionList);
	    Collections.shuffle(tripExceptionList, new Random(System.nanoTime()));
	    
	    tripExceptionList.addAll(tripList);
	    return tripExceptionList;
	}
	
	
	public TripSearchDTO getTripDetails(String tripId) throws SolrServerException {
		
	    HttpSolrServer solr = new HttpSolrServer(tripUrl);
		SolrQuery query = new SolrQuery();
		  
	    String queryStr = "id:"+tripId;
	    query.setQuery(queryStr);
	    query.setRows(100);
	    query.setFields("id,trip_name,trip_cover_uri,userFbId,userId,userName,userLastName,entity_details,trip_category,public_id,likes,from_city_details,city_details,comments_tl,images_nos");
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    if(CollectionUtils.isNotEmpty(results)) {
	    SolrDocument document = results.get(0);
	    
	    	   TripSearchDTO trip = new TripSearchDTO();
		       if(document.getFieldValue("userLastName") != null && document.getFieldValue("userName") != null) {
		    	   trip.setUserName(document.getFieldValue("userName").toString() + " " +document.getFieldValue("userLastName").toString());
		       }
		       if(document.getFieldValue("trip_name") != null) {
		    	   trip.setTripName(document.getFieldValue("trip_name").toString());
		       }
		       if(document.getFieldValue("trip_cover_uri") != null) {
		    	   trip.setTripUri(document.getFieldValue("trip_cover_uri").toString());
		       }
		       if(document.getFieldValue("userFbId") != null) {
		    	   trip.setUserFbId(document.getFieldValue("userFbId").toString());
		       }
		       if(document.getFieldValue("userId") != null) {
		    	   trip.setUserId(document.getFieldValue("userId").toString());
		       }
		       if(document.getFieldValue("id") != null) {
		    	   trip.setOldTripId(document.getFieldValue("id").toString());
		       }
		       if(document.getFieldValue("public_id") != null) {
		    	   trip.setTripId(document.getFieldValue("public_id").toString());
		       }
		       if(document.getFieldValue("likes") != null) {
		    	   trip.setTripLikes(document.getFieldValue("likes").toString());
		       }
		       if(document.getFieldValue("comments_tl") != null) {
		    	   trip.setTripComments(document.getFieldValue("comments_tl").toString());
		       }
		       if(document.getFieldValue("images_nos") != null) {
		    	   trip.setTripImages(document.getFieldValue("images_nos").toString());
		       }
		       if(document.getFieldValue("trip_category") != null) {
		    	   String categories = document.getFieldValue("trip_category").toString();
		    	   if(!StringUtils.isEmpty(categories)) {
		    		   trip.setCategories(Arrays.asList(categories.split(",")));
		    	   }
		       }
		       List<CityResponseDTO> allStops = new ArrayList<CityResponseDTO>();
		       if(document.getFieldValue("from_city_details") != null) {
		    	   List<String> fromCities = (List<String>)document.getFieldValue("from_city_details");
		    	   if(!CollectionUtils.isEmpty(fromCities)) {
		    		  // List<String> fromCityList = Arrays.asList(categories.split(","));
		    		  String fromCity = fromCities.get(0).split(",")[0];
		    		  CityResponseDTO fromCityDTO = new CityResponseDTO();
		    		  fromCityDTO.setCityName(fromCity);
		    		  fromCityDTO.setType("Source");
		    		  allStops.add(fromCityDTO);
		    	   }
		    	}
		       
		       if(document.getFieldValue("city_details") != null) {
		    	   List<String> toCities = (List<String>)document.getFieldValue("city_details");
		    	   if(!CollectionUtils.isEmpty(toCities)) {
		    		  for(String toCity : toCities) {
			    		  String toCityStr = toCity.split(",")[0];
			    		  CityResponseDTO toCityDTO = new CityResponseDTO();
			    		  toCityDTO.setCityName(toCityStr);
			    		  toCityDTO.setType("Destination");
			    		  allStops.add(toCityDTO);
		    		  }
		    	   }
		    	   
		       }
		       trip.setTripCities(allStops);
		 

	    return trip;
	   }
	    return null;
	}

	

}
