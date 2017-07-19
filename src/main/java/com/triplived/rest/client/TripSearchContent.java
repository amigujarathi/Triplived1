package com.triplived.rest.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.dto.CityResponseDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;

@Component
public class TripSearchContent {
	
	@Value("${attractionUrl}")
	private String attractionUrl;
	
	@Value("${cityUrl}")
	private String cityUrl;
	
	@Value("${countryUrl}")
	private String countryUrl;
	
	@Value("${userUrl}")
	private String userUrl;
	
	@Value("${tripUrl}")
	private String tripUrl;
	
	@Value("${stateUrl}")
	private String stateUrl;
	
	public List<EntityResponse> autoCompleteForTripSearch(String param) throws SolrServerException {
		
		List<EntityResponse> entityList = new ArrayList<EntityResponse>();
	    if(null != param) {
	    	
	    	
	    	   HttpSolrServer solrCountry = new HttpSolrServer(countryUrl);
			    SolrQuery queryCountry = new SolrQuery();
			  
			    String queryStrCountry = "country_name:"+'"'+param+'"';
			    queryCountry.setQuery(queryStrCountry);
			    queryCountry.setRows(5);
			    queryCountry.setFields("country_name");
			    QueryResponse responseCountry = solrCountry.query(queryCountry);
			    SolrDocumentList resultsCountry = responseCountry.getResults();
			    
			    for (SolrDocument document : resultsCountry) {
			    	EntityResponse entity = new EntityResponse();
				       if(document.getFieldValue("country_name") != null) {
				    	   entity.setCountryName(document.getFieldValue("country_name").toString());
				       }
				       entity.setType("country");
				       entityList.add(entity);
				}
			    
			    

			    HttpSolrServer solrState = new HttpSolrServer(stateUrl);
			    SolrQuery queryState = new SolrQuery();
			  
			    String queryStrState = "state_name:"+param;
			    queryState.setQuery(queryStrState);
			    queryState.setRows(5);
			    queryState.setFields("state_name,country_name");
			    QueryResponse responseState = solrState.query(queryState);
			    SolrDocumentList resultsState = responseState.getResults();
			    
			    for (SolrDocument document : resultsState) {
			    	EntityResponse entity = new EntityResponse();
				       if(document.getFieldValue("state_name") != null) {
				    	   String stateName = document.getFieldValue("state_name").toString();
				    	   if(stateName.equalsIgnoreCase("Goa")) {
				    		   continue;
				    	   }
				    	   entity.setState(stateName);
				       }
				       if(document.getFieldValue("country_name") != null) {
				    	   entity.setCountryName(document.getFieldValue("country_name").toString());
				       }
				       entity.setType("state");
				       entityList.add(entity);
				}
			    
			  
			    HttpSolrServer solrCity = new HttpSolrServer(cityUrl);
			    SolrQuery queryCity = new SolrQuery();
			  
			    String queryStrCity = "city_name:" + '"'+param+'"';
			    queryCity.setQuery(queryStrCity);
			    queryCity.setRows(10);
			    queryCity.set("defType", "dismax");
			    queryCity.set("bq","country_name:India^5.0");
			    queryCity.set("qf", "city_name");
			    queryCity.setFields("city_name,id,state,country_name");
			    QueryResponse responseCity = solrCity.query(queryCity);
			    SolrDocumentList resultsCity = responseCity.getResults();
			    
			    for (SolrDocument document : resultsCity) {
			    	EntityResponse entity = new EntityResponse();
				       if(document.getFieldValue("id") != null) {
				    	   entity.setId(document.getFieldValue("id").toString());
				       }
				       if(document.getFieldValue("city_name") != null) {
				    	   entity.setCityName(document.getFieldValue("city_name").toString());
				       }
				       if(document.getFieldValue("state") != null) {
				    	   entity.setState(document.getFieldValue("state").toString());
				       }
				       if(document.getFieldValue("country_name") != null) {
				    	   entity.setCountryName(document.getFieldValue("country_name").toString());
				       }
				       entity.setType("city");
				       entityList.add(entity);
				}
			    
	    	 	
			    HttpSolrServer solr = new HttpSolrServer(attractionUrl);
			    SolrQuery query = new SolrQuery();
			  
			    String queryStr = "attraction_name:"+ '"'+param+'"';
			    query.setQuery(queryStr);
			    query.setRows(10);
			    query.set("defType", "dismax");
			    query.set("bq","country_name:India^5.0");
			    query.set("qf", "attraction_name");
			    query.setFields("attraction_name,id,city_code,city_name,state,country_name");
			    QueryResponse response = solr.query(query);
			    SolrDocumentList results = response.getResults();
			    
			    for (SolrDocument document : results) {
			    	EntityResponse entity = new EntityResponse();
				       if(document.getFieldValue("attraction_name") != null) {
				    	   entity.setName(document.getFieldValue("attraction_name").toString());
				       }
				       if(document.getFieldValue("id") != null) {
				    	   entity.setId(document.getFieldValue("id").toString());
				       }
				       if(document.getFieldValue("city_code") != null) {
				    	   entity.setCityCode(document.getFieldValue("city_code").toString());
				       }
				       if(document.getFieldValue("city_name") != null) {
				    	   entity.setCityName(document.getFieldValue("city_name").toString());
				       }
				       if(document.getFieldValue("state") != null) {
				    	   entity.setState(document.getFieldValue("state").toString());
				       }
				       if(document.getFieldValue("country_name") != null) {
				    	   entity.setCountryName(document.getFieldValue("country_name").toString());
				       }
				       entity.setType("attraction");
				       entityList.add(entity);
				}
			    
			    
			    	    
			    HttpSolrServer solrUser = new HttpSolrServer(userUrl);
			    SolrQuery queryUser = new SolrQuery();
				  
			    String queryStrUser = "full_name:"+'"'+param+'"';
			    queryUser.setQuery(queryStrUser);
			    queryUser.setRows(10);
			    queryUser.setFields("id,name,last_name,user_id");
			    QueryResponse responseUser = solrUser.query(queryUser);
			    SolrDocumentList resultsUser = responseUser.getResults();
			    
			    for (SolrDocument document : resultsUser) {
			    	EntityResponse entity = new EntityResponse();
				       if(document.getFieldValue("name") != null) {
				    	   String name = document.getFieldValue("name").toString();
				    	   if(document.getFieldValue("last_name") != null) {
				    		   entity.setName(name + " " +document.getFieldValue("last_name").toString());   
				    	   }else {
				    		   entity.setName(name);
				    	   }
				       }
				       if(document.getFieldValue("user_id") != null) {
				    	   entity.setId(document.getFieldValue("user_id").toString());
				       }
				       if(document.getFieldValue("id") != null) {
				    	   //entity.setUserFbId(document.getFieldValue("id").toString());
				       }
				       entity.setType("User");
				       //Commenting user search for now
				       entityList.add(entity);
				}

	    }
	    
	    return entityList;
	
	}
	
	
	public List<TripSearchDTO> searchTrips(String param, String type) throws SolrServerException {
		
		List<TripSearchDTO> tripList = new ArrayList<TripSearchDTO>();
		
	    HttpSolrServer solr = new HttpSolrServer(tripUrl);
		SolrQuery query = new SolrQuery();
		  
	    String queryStr = "entity_details:"+'"'+param+'"';
	    
	    if(null != type && type.equalsIgnoreCase("city")) {
	    	queryStr = "city_details:"+'"'+param+'"';
	    }
	    if(null != type && type.equalsIgnoreCase("country")) {
	    	queryStr = "city_details:"+'"'+param+'"';
	    }
	    
	    query.setQuery(queryStr);
	    query.setRows(100);
	    query.setFields("id,trip_name,trip_cover_uri,userName,userLastName,userId,entity_details,trip_category,public_id,likes,from_city_details,city_details,comments_tl,images_nos,trip_searchable");
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    
	    for (SolrDocument document : results) {
	    	   TripSearchDTO trip = new TripSearchDTO();
	    	   if(document.getFieldValue("trip_searchable") != null) {
		    	   if(document.getFieldValue("trip_searchable").toString().equals("I")) {
		    		   continue;
		    	   }
		       }
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
		    	   //ADITI_TO_DO - get all the attractions here. This needs to be done only in cases where search type is not attraction
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
		       tripList.add(trip);
		}

	    return tripList;
	}
	
	
	
    public List<TripSearchDTO> searchTripsByCategory(String param, String type) throws SolrServerException {
		
		List<TripSearchDTO> tripList = new ArrayList<TripSearchDTO>();
		
	    HttpSolrServer solr = new HttpSolrServer(tripUrl);
		SolrQuery query = new SolrQuery();
		  
	    String queryStr = "trip_category:"+'"'+param+'"';
	    query.setQuery(queryStr);
	    query.setRows(100);
	    query.setFields("id,trip_name,trip_cover_uri,userFbId,userId,userName,userLastName,entity_details,trip_category,public_id,likes,from_city_details,city_details,comments_tl,images_nos");
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    
	    for (SolrDocument document : results) {
	    	   TripSearchDTO trip = new TripSearchDTO();
		       if(document.getFieldValue("userName") != null) {
		    	   if(document.getFieldValue("userLastName") != null ) {
		    		   trip.setUserName(document.getFieldValue("userName").toString() + " " +document.getFieldValue("userLastName").toString());	   
		    	   }else {
		    		   trip.setUserName(document.getFieldValue("userName").toString());
		    	   }
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
		       tripList.add(trip);
		}

	    return tripList;
	}


}
