package com.triplived.rest.client;
import java.io.BufferedWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.connectme.domain.triplived.AttractionResponse;
import com.connectme.domain.triplived.Category;
import com.connectme.domain.triplived.CityResponse;
import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.HotelResponse;
import com.connectme.domain.triplived.UserResponse;
import com.connectme.domain.triplived.dto.NotificationDTO;
import com.triplived.entity.AttractionDb;
import com.triplived.util.TripLivedUtil;

@Component
public class StaticContent {
	
	private static final Logger logger = LoggerFactory.getLogger(StaticContent.class);
	
	@Value("${attractionUrl}")
	private String attractionUrl;
	
	@Value("${hotelUrl}")
	private String hotelUrl;
	
	@Value("${cityUrl}")
	private String cityUrl;
	
	@Value("${countryUrl}")
	private String countryUrl;
	
	@Value("${userUrl}")
	private String userUrl;
	
	@Value("${stateUrl}")
	private String stateUrl;
	
	@Value("${tripUrl}")
	private String tripUrl;

	@Value("${group_replace_cities}")
	private String group_replace_cities;
	
	@Value("${profileType}")
	private String profileType;

	  
	@SuppressWarnings("unchecked")
	public List<HotelResponse> getHotels() throws MalformedURLException, SolrServerException {
		    HttpSolrServer solr = new HttpSolrServer(hotelUrl);

		    SolrQuery query = new SolrQuery();
		    query.setQuery("*:*");
		    QueryResponse response = solr.query(query);
		    SolrDocumentList results = response.getResults();
		    List<HotelResponse> hotelList = new ArrayList<HotelResponse>();
		    
		    for (SolrDocument document : results) {
		       HotelResponse hotel = new HotelResponse();
		       
		       if(document.getFieldValue("htl_name") != null) {
		    	   hotel.setName(document.getFieldValue("htl_name").toString());
		       }
		       if(document.getFieldValue("latitude") != null) {
		    	   hotel.setLatitude(document.getFieldValue("latitude").toString());
		       }
		       if(document.getFieldValue("longitude") != null) {
		    	   hotel.setLongitude(document.getFieldValue("longitude").toString());
		       }
		       if(document.getFieldValue("id") != null) {
		    	   hotel.setId(document.getFieldValue("id").toString());
		       }
		       if(document.getFieldValue("htl_address") != null) {
		    	   hotel.setAddress(document.getFieldValue("htl_address").toString());
		       }
		       if(document.getFieldValue("htl_locality") != null) {
		    	   hotel.setLocality(document.getFieldValue("htl_locality").toString());
		       }
		       if(document.getFieldValue("htl_street") != null) {
		    	   hotel.setStreet(document.getFieldValue("htl_street").toString());
		       }
		       if(document.getFieldValue("htl_image_src") != null) {
		    	   List<String> images = (List<String>)document.getFieldValue("htl_image_src");
		    	   hotel.setHotelImages(images);
		       }
		       if(document.getFieldValue("htl_amenity_name") != null) {
		    	   List<String> amenities = (List<String>)document.getFieldValue("htl_amenity_name");
		    	   hotel.setAmenities(amenities);
		       }
		       hotelList.add(hotel);
		    }
		    
		    return hotelList;
		  }
	  
	  public List<HotelResponse> suggestHotels(String cityCode, String param) throws MalformedURLException, SolrServerException {
		  
		    List<String> groupReplaceCitiesList = Arrays.asList(group_replace_cities.split(","));
		    HttpSolrServer solr = new HttpSolrServer(hotelUrl);

		    SolrQuery query = new SolrQuery();
		  
		    String[] params = param.split(" ");
		    String queryInitial = "((htl_name:\""+param+"\"^10 OR ";
		    String queryEnd = ")AND htl_city_code:"+cityCode+")";
		    
		    if(groupReplaceCitiesList.contains(cityCode)) {
		    	queryEnd = ")AND htl_city_group:"+cityCode+")";
		    }
		    
		    int counter = 0;
		    StringBuffer dynamicQuery = new StringBuffer();
		    dynamicQuery.append("(");
		    for(String s : params) {
		    	counter++;
		    	dynamicQuery.append("htl_name:"+s);
		    	if(counter != params.length) {
		    		dynamicQuery.append(" AND ");
		    	}
		    }
		    dynamicQuery.append(")");
		    
		    String queryStr = queryInitial + dynamicQuery.toString() + queryEnd;
		    query.setQuery(queryStr);
		    query.setRows(1000);
		    //query.setFields("htl_name,id");
		    QueryResponse response = solr.query(query);
		    SolrDocumentList results = response.getResults();
		    List<HotelResponse> hotelList = new ArrayList<HotelResponse>();
		    
		    for (SolrDocument document : results) {
			       HotelResponse hotel = new HotelResponse();
			       if(document.getFieldValue("htl_name") != null) {
			    	   hotel.setName(document.getFieldValue("htl_name").toString());
			       }
			       if(document.getFieldValue("id") != null) {
			    	   hotel.setId(document.getFieldValue("id").toString());
			       }
			       if(document.getFieldValue("htl_latitude") != null) {
			    	   hotel.setLatitude(document.getFieldValue("htl_latitude").toString());
			       }
			       if(document.getFieldValue("htl_longitude") != null) {
			    	   hotel.setLongitude(document.getFieldValue("htl_longitude").toString());
			       }
			       if(document.getFieldValue("htl_street") != null) {
			    	   hotel.setAddress(document.getFieldValue("htl_street").toString());
			       }
			       if(document.getFieldValue("htl_description") != null) {
			    	   hotel.setDescription(document.getFieldValue("htl_description").toString());
			       }
			       if(document.getFieldValue("htl_rating") != null) {
			    	   hotel.setRating(document.getFieldValue("htl_rating").toString());
			       }
			       if(document.getFieldValue("htl_ta_hotel_rank") != null) {
			    	   hotel.setTaRank(document.getFieldValue("htl_ta_hotel_rank").toString());
			       }
			       if(document.getFieldValue("htl_image_src") != null) {
			    	   String images =  document.getFieldValue("htl_image_src").toString();
			    	   if(null != images) {
			    		   List<String> imageSrc = (List<String>) document.getFieldValue("htl_image_src");
			    		   hotel.setHotelImages(imageSrc);
			    	   }
			    	   
			       }
			       hotelList.add(hotel);
		    }
		    return hotelList;
	  }

	  
	  public AttractionResponse matchAttractionByNameAndCity(String fullAttractionName, String cityCode, AttractionDb a, String d, BufferedWriter brw) throws MalformedURLException, SolrServerException, Exception {
		  HttpSolrServer solr = new HttpSolrServer(attractionUrl);
		  AttractionResponse attraction = null;
		  
		  String[] names = fullAttractionName.split(" ");
		  for(int i=0;i<names.length;i++) {
			  String attractionName = makeString(names, i, names.length -1);
			  SolrQuery query = new SolrQuery();
			  String queryString = "((attraction_name:\"" + attractionName + "\") AND (city_code:" + cityCode + "))";
			  query.setQuery(queryString);
			  QueryResponse response = solr.query(query);
			  SolrDocumentList results = response.getResults();
			  
			  	if(!CollectionUtils.isEmpty(results) && results.size() == 1) {
			    	SolrDocument document = results.get(0);
			    	attraction = new AttractionResponse();
				       if(document.getFieldValue("attraction_name") != null) {
				    	   attraction.setAttractionName(document.getFieldValue("attraction_name").toString());
				       }
				       if(document.getFieldValue("id") != null) {
				    	   attraction.setId(document.getFieldValue("id").toString());
				       }
				    break;   
			  	}      
		  }  	
		  return attraction;
	  }
	  
	  private String makeString(String[] names, int begin, int last) {
		  StringBuffer x = new StringBuffer();
		  for(int i = begin; i<=last; i++){
			  x.append(names[i] + " ");
		  }
		  return x.toString();	  
	  }
	  
	  public List<AttractionResponse> suggestAttractions(String cityCode, String param) throws MalformedURLException, SolrServerException {
		  
		    List<AttractionResponse> attractionList = new ArrayList<AttractionResponse>();
		    if(StringUtils.isNotEmpty(cityCode)) {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    String[] params = param.split(" ");
				    String queryInitial = "((attraction_name:\""+param+"\"^10 OR ";
				    String queryEnd = ")AND city_code:"+cityCode+")";
				    
				    int counter = 0;
				    StringBuffer dynamicQuery = new StringBuffer();
				    dynamicQuery.append("(");
				    for(String s : params) {
				    	counter++;
				    	dynamicQuery.append("attraction_name:"+s);
				    	if(counter != params.length) {
				    		dynamicQuery.append(" AND ");
				    	}
				    }
				    dynamicQuery.append(")");
				    
				    String queryStr = queryInitial + dynamicQuery.toString() + queryEnd;
				    query.setQuery(queryStr);
				    query.setRows(1000);
				    query.setFields("attraction_name, id, latitude, longitude, description, attraction_punchline, attraction_image_src, category_names, google_place_id, google_place_name, address1, timings, reqTime, bestTime, ticket, website, phone, updatedDate, updatedBy");
				    query.setSortField("attraction_name", ORDER.asc);
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    for (SolrDocument document : results) {
				    	AttractionResponse attraction = new AttractionResponse();
					       if(document.getFieldValue("attraction_name") != null) {
					    	   attraction.setAttractionName(document.getFieldValue("attraction_name").toString());
					       }
					       if(document.getFieldValue("id") != null) {
					    	   attraction.setId(document.getFieldValue("id").toString());
					       }
					       if(document.getFieldValue("description") != null) {
					    	   attraction.setDescription(document.getFieldValue("description").toString());
					       }
					       
					       if(document.getFieldValue("google_place_id") != null) {
					    	   attraction.setGooglePlaceId(document.getFieldValue("google_place_id").toString());
					       }
					       
					       if(document.getFieldValue("google_place_name") != null) {
					    	   attraction.setGoogleplaceName(document.getFieldValue("google_place_name").toString());
					       }
					       
					       if(document.getFieldValue("address1") != null) {
					    	   attraction.setAddress1(document.getFieldValue("address1").toString());
					       }
					       if(document.getFieldValue("timings") != null) {
					    	   attraction.setTimings(document.getFieldValue("timings").toString());
					       }
					       if(document.getFieldValue("reqTime") != null) {
					    	   attraction.setReqTime(document.getFieldValue("reqTime").toString());
					       }
					       if(document.getFieldValue("bestTime") != null) {
					    	   attraction.setBestTime(document.getFieldValue("bestTime").toString());
					       }
					       if(document.getFieldValue("phone") != null) {
					    	   attraction.setPhoneNmber(document.getFieldValue("phone").toString());
					       }
					       if(document.getFieldValue("ticket") != null) {
					    	   attraction.setTicket(document.getFieldValue("ticket").toString());
					       }
					       if(document.getFieldValue("website") != null) {
					    	   attraction.setWebSite(document.getFieldValue("website").toString());
					       }
					       
					       
					       if(document.getFieldValue("attraction_punchline") != null) {
					    	   attraction.setPunchLine(document.getFieldValue("attraction_punchline").toString());
					       }
					       
					       if(document.getFieldValue("updatedDate") != null) {
					    	   attraction.setUpdatedDate(document.getFieldValue("updatedDate").toString());
					       }
					       
					       if(document.getFieldValue("updatedBy") != null) {
					    	   attraction.setUpdatedBy(document.getFieldValue("updatedBy").toString());
					       }
					       
					       if(document.getFieldValue("latitude") != null) {
					    	   attraction.setLatitude(Double.parseDouble((document.getFieldValue("latitude")).toString()));
					       }else{
					    	   
					    	   attraction.setLatitude(0.0);
					       }

					       if(document.getFieldValue("longitude") != null) {
					    	   attraction.setLongitude(Double.parseDouble((document.getFieldValue("longitude")).toString()));
					       }else{
					    	   attraction.setLongitude(0.0);
					       }

					       if(document.getFieldValue("attraction_image_src") != null) {
					    	   @SuppressWarnings("unchecked")
							List<String> images = (List<String>)document.getFieldValue("attraction_image_src");
					    	   attraction.setImages(images);
					       }
					       
					       if(document.getFieldValue("category_names") != null) {
					    	   @SuppressWarnings("unchecked")
					    	   List<String> category_names = (List<String>)document.getFieldValue("category_names");
					    	   for (String categoryString : category_names) {
								
					    		   String[] cat = categoryString.split("____");
					    		   Category category = new Category();
					    		   category.setCategorySeq(cat[0]);
					    		   category.setName(cat[1]);
					    		   
					    		   attraction.getCategories().add(category);
					    	   }
					    	   
					       }
					       
					       attractionList.add(attraction);
				    }
		    }
		    
		    
		    return attractionList;
	  }
	  
	  
	  
	  public List<AttractionResponse> getPopularAttractionsOfCity(String cityCode) throws MalformedURLException, SolrServerException {
		  
		    List<AttractionResponse> attractionList = new ArrayList<AttractionResponse>();
		    if(StringUtils.isNotEmpty(cityCode)) {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    String queryInitial = "(city_code:"+cityCode+")";
				    
				    String queryStr = queryInitial;
				    query.setQuery(queryStr);
				    query.setRows(1000);
				    query.setFields("attraction_name, id");
				    query.setSortField("attraction_name", ORDER.asc);
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    for (SolrDocument document : results) {
				    	AttractionResponse attraction = new AttractionResponse();
					       if(document.getFieldValue("attraction_name") != null) {
					    	   attraction.setAttractionName(document.getFieldValue("attraction_name").toString());
					       }
					       if(document.getFieldValue("id") != null) {
					    	   attraction.setId(document.getFieldValue("id").toString());
					       }					       
					       attractionList.add(attraction);
				    }
		    }
		    
		    
		    return attractionList;
	  }

	  
	  /**
	   * This method is only valid for attractions.
	   * @param attractionId
	   * @return
	   */
	  public NotificationDTO getPunchlineForGCMNotificationsForAttractions(String attractionId) {
		  
		  NotificationDTO notification = new NotificationDTO();
		  try {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    query.setQuery("id:"+attractionId);
				    query.setRows(1000);
				    query.setFields("attraction_name, id, description, attraction_punchline, city_code");
				    
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    if(null != results && !results.isEmpty()) {
					    for (SolrDocument document : results) {
					    	   if(document.getFieldValue("attraction_punchline") != null) {
					    		   notification.setPunchline(document.getFieldValue("attraction_punchline").toString());
						       }
					    	   if(document.getFieldValue("attraction_name") != null) {
					    		   notification.setName(document.getFieldValue("attraction_name").toString());
						       }
					    	   if(document.getFieldValue("id") != null) {
					    		   notification.setId(document.getFieldValue("id").toString());
						       }
						       
						}
				    }
		    
		    return notification;
		  }catch(Exception e) {
			  
		  }
		  
		  return notification;
	  }
	  
	  /**
	   * TODO need to change punchline, currently serving description.
	   * @param cityId
	   * @return
	   */
	  public NotificationDTO getPunchlineForGCMNotificationsForCity(String cityId) {
		  
		  NotificationDTO notification = new NotificationDTO();
		  try {
		    	 	HttpSolrServer solr = new HttpSolrServer(cityUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    query.setQuery("id:"+cityId);
				    query.setRows(1000);
				    query.setFields("id, description, city_name");
				    
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    
				    for (SolrDocument document : results) {
				    	   if(document.getFieldValue("description") != null) {
				    		   notification.setPunchline(document.getFieldValue("description").toString());
					       }
				    	   if(document.getFieldValue("city_name") != null) {
				    		   notification.setName(document.getFieldValue("city_name").toString());
					       }
				    	   notification.setId(cityId);
				    	   notification.setType("city");
					}
		    
		    
		   
		    return notification;
		  }catch(Exception e) {
			  
		  }
		  
		  return notification;
	  }
	  
	  public List<EntityResponse> suggestAttractionEntityByCoordinates(Double lat, Double lng) throws MalformedURLException, SolrServerException {
		  //http://104.238.103.233:8983/solr/attraction/select?q=*%3A*&start=0&rows=50&fq={!geofilt%20pt=26.911385,75.74377%20sfield=latlng%20d=1}
		    
		  List<EntityResponse> entityList = new ArrayList<EntityResponse>();
		    if((null != lat) && (null != lng)) {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    query.setQuery("*:*");
				    query.addFilterQuery("{!geofilt}");
				    query.set("pt",lat+","+lng);
				    query.set("sfield","latlng");
				    query.set("d",1);
				    query.addSortField("geodist()", ORDER.asc);
				    query.setRows(1000);
				    query.setFields("attraction_name, id, latitude, longitude, description, attraction_punchline, attraction_image_src, city_code, _dist_:geodist()");
				    
				    //query.addFilterQuery("!geofilt", "pt:26.911385,75.74377", "sfield:latlng", "d:1");
				    
				    //query.setSortField("attraction_name", ORDER.asc);
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
					       if(document.getFieldValue("_dist_") != null) {
					    	   entity.setDistanceFromCoordinates(Double.parseDouble(document.getFieldValue("_dist_").toString()));
					       }
					       entityList.add(entity);
					}
		    }
		    
		   
		    return entityList;
		  
	  }
	  
	  
	  
	  public EntityResponse getAttractionEntityByGcode(String gCode) throws MalformedURLException, SolrServerException {
		  //http://104.238.103.233:8983/solr/attraction/select?q=*%3A*&start=0&rows=50&fq={!geofilt%20pt=26.911385,75.74377%20sfield=latlng%20d=1}
		    
		  List<EntityResponse> entityList = new ArrayList<EntityResponse>();
		    if(null != gCode) {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    String queryStr = "google_place_id:"+gCode;
				    query.setQuery(queryStr);
				    query.setRows(100);
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
					       if(document.getFieldValue("category_groups") != null) {
					    	   List<String> categoryIcons = (List<String>) document.getFieldValue("category_groups");
					    	   entity.setCategory_group_icons(categoryIcons);
					       }
					       if(document.getFieldValue("category_name") != null) {
					    	   String category =  document.getFieldValue("category_name").toString();
					    	   entity.setCategory(category);
					       }
					       entityList.add(entity);
					}
		    }
		    
		    if(!CollectionUtils.isEmpty(entityList)) {
		    	return entityList.get(0);	
		    }else {
		    	return null;
		    }
		    
		  
	  }
	  
	  
	  public List<EntityResponse> suggestHotelEntityByCoordinates(Double lat, Double lng, String d) throws MalformedURLException, SolrServerException {
		  //http://104.238.103.233:8983/solr/attraction/select?q=*%3A*&start=0&rows=50&fq={!geofilt%20pt=26.911385,75.74377%20sfield=latlng%20d=1}
		    
		  List<EntityResponse> entityList = new ArrayList<EntityResponse>();
		    if((null != lat) && (null != lng)) {
		    	 	HttpSolrServer solr = new HttpSolrServer(hotelUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    query.setQuery("*:*");
				    query.addFilterQuery("{!geofilt}");
				    query.set("pt",lat+","+lng);
				    query.set("sfield","latlng");
				    query.set("d",d.toString());
				    query.addSortField("geodist()", ORDER.asc);
				    query.setRows(1000);
				    query.setFields("htl_name, id, htl_latitude, htl_longitude, htl_street, htl_image_src, htl_locality, city_code, _dist_:geodist()");
				  
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    for (SolrDocument document : results) {
				    	EntityResponse entity = new EntityResponse();
					       
					       if(document.getFieldValue("htl_name") != null) {
					    	   entity.setName(document.getFieldValue("htl_name").toString());
					       }
					       if(document.getFieldValue("latitude") != null) {
					    	   entity.setLatitude(document.getFieldValue("latitude").toString());
					       }
					       if(document.getFieldValue("longitude") != null) {
					    	   entity.setLongitude(document.getFieldValue("longitude").toString());
					       }
					       if(document.getFieldValue("id") != null) {
					    	   entity.setId(document.getFieldValue("id").toString());
					       }
					       if(document.getFieldValue("htl_street") != null) {
					    	   entity.setAddress(document.getFieldValue("htl_street").toString());
					       }
					       if(document.getFieldValue("htl_locality") != null) {
					    	   entity.setLocality(document.getFieldValue("htl_locality").toString());
					       }
					       if(document.getFieldValue("htl_street") != null) {
					    	   entity.setStreet(document.getFieldValue("htl_street").toString());
					       }
					       if(document.getFieldValue("htl_image_src") != null) {
					    	   List<String> images = (List<String>)document.getFieldValue("htl_image_src");
					    	   entity.setImageSrc(images);
					       }
					       if(document.getFieldValue("_dist_") != null) {
					    	   entity.setDistanceFromCoordinates(TripLivedUtil.trimValue(Double.parseDouble(document.getFieldValue("_dist_").toString())));
					       }
					       entityList.add(entity);
					}
		    }
		    
		   
		    return entityList;
		  
	  }
	  
	  
	  public List<EntityResponse> suggestAllEntityByCoordinates(Double lat, Double lng, Double d) throws MalformedURLException, SolrServerException {
		  //http://104.238.103.233:8983/solr/attraction/select?q=*%3A*&start=0&rows=500&fq={!geofilt}&pt=28.5062465,77.0707054&sfield=latlng&d=10&sort=geodist%28%29%20asc
		    
		  List<EntityResponse> entityList = new ArrayList<EntityResponse>();
		    if((null != lat) && (null != lng)) {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    /*String queryInitial = "*.*";
				    String dynamicQuery = "&fq={!geofilt}&pt=" + lat + "," + lng + "&sfield=latlng&d="+d+"&sort=geodist() asc";
				    
				    String queryStr = queryInitial + dynamicQuery;*/
				   
				    query.setQuery("*:*");
				    query.addFilterQuery("{!geofilt}");
				    query.set("pt",lat+","+lng);
				    query.set("sfield","latlng");
				    query.set("d",d.toString());
				    query.addSortField("geodist()", ORDER.asc);
				    query.setRows(1000);
				    query.setFields("attraction_name, id, latitude, longitude, description, attraction_punchline, attraction_image_src, city_code, _dist_:geodist()");
				    
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    for (SolrDocument document : results) {
				    	EntityResponse entity = new EntityResponse();
				    	entity.setType("attraction");
					       if(document.getFieldValue("attraction_name") != null) {
					    	   entity.setName(document.getFieldValue("attraction_name").toString());
					       }
					       if(document.getFieldValue("id") != null) {
					    	   entity.setId(document.getFieldValue("id").toString());
					       }
					       if(document.getFieldValue("city_code") != null) {
					    	   entity.setCityCode(document.getFieldValue("city_code").toString());
					       }
					       if(document.getFieldValue("_dist_") != null) {
					    	   entity.setDistanceFromCoordinates(Double.parseDouble(document.getFieldValue("_dist_").toString()));
					       }
					       
					       entityList.add(entity);
					}
				    
				    
				    solr = new HttpSolrServer(hotelUrl);
				    query = new SolrQuery();
				    
				    query.setQuery("*:*");
				    query.addFilterQuery("{!geofilt}");
				    query.set("pt",lat+","+lng);
				    query.set("sfield","latlng");
				    query.set("d",d.toString());
				    query.addSortField("geodist()", ORDER.asc);
				    query.setRows(1000);
				    query.setFields("htl_name, id, htl_latitude, htl_longitude, htl_street, htl_image_src, htl_locality, city_code, _dist_:geodist()");
				  
				    
				    response = solr.query(query);
				    results = response.getResults();
				    
				    for (SolrDocument document : results) {
				    	EntityResponse entity = new EntityResponse();
				    	entity.setType("hotel");   
					       if(document.getFieldValue("htl_name") != null) {
					    	   entity.setName(document.getFieldValue("htl_name").toString());
					       }
					       if(document.getFieldValue("latitude") != null) {
					    	   entity.setLatitude(document.getFieldValue("latitude").toString());
					       }
					       if(document.getFieldValue("longitude") != null) {
					    	   entity.setLongitude(document.getFieldValue("longitude").toString());
					       }
					       if(document.getFieldValue("id") != null) {
					    	   entity.setId(document.getFieldValue("id").toString());
					       }
					       if(document.getFieldValue("htl_street") != null) {
					    	   entity.setAddress(document.getFieldValue("htl_street").toString());
					       }
					       if(document.getFieldValue("htl_locality") != null) {
					    	   entity.setLocality(document.getFieldValue("htl_locality").toString());
					       }
					       if(document.getFieldValue("htl_street") != null) {
					    	   entity.setStreet(document.getFieldValue("htl_street").toString());
					       }
					       if(document.getFieldValue("city_code") != null) {
					    	   entity.setCityCode(document.getFieldValue("city_code").toString());
					       }
					       if(document.getFieldValue("htl_image_src") != null) {
					    	   List<String> images = (List<String>)document.getFieldValue("htl_image_src");
					    	   entity.setImageSrc(images);
					       }
					       if(document.getFieldValue("_dist_") != null) {
					    	   entity.setDistanceFromCoordinates(Double.parseDouble(document.getFieldValue("_dist_").toString()));
					       }
					       entityList.add(entity);
					}
		    }
		    
		    Collections.sort(entityList);
		    return entityList;
		  
	  }  


	  /**
	   * Pass city name as param, ex. Delhi
	   * @param param
	   * @return
	   * @throws MalformedURLException
	   * @throws SolrServerException
	   */
	public List<CityResponse> suggestCities(String param) throws MalformedURLException, SolrServerException {
		    HttpSolrServer solr = new HttpSolrServer(cityUrl);
		    SolrQuery query = new SolrQuery();
		    //url=((city_name:\pune^10 OR (city_name))
		  
		    String[] params = param.split(" ");
		    String queryInitial = "((city_name:\""+param+"\"^10 OR ";
		    String queryEnd = ")"+")";
		    
		    int counter = 0;
		    StringBuffer dynamicQuery = new StringBuffer();
		    dynamicQuery.append("(");
		    for(String s : params) {
		    	counter++;
		    	dynamicQuery.append("city_name:"+s);
		    	if(counter != params.length) {
		    		dynamicQuery.append(" AND ");
		    	}
		    }
		    dynamicQuery.append(")");
		    
		    String queryStr = queryInitial + dynamicQuery.toString() + queryEnd;
		    query.setQuery(queryStr);
		    query.setFields("city_name,id,description,currentTemperature,city_image,countryId,country_name");
		    query.setRows(20);
		    QueryResponse response = solr.query(query);
		    SolrDocumentList results = response.getResults();
		    List<CityResponse> cityList = new ArrayList<CityResponse>();
		    
		    for (SolrDocument document : results) {
		    	CityResponse city = new CityResponse();
			       if(document.getFieldValue("city_name") != null) {
			    	   city.setCityName(document.getFieldValue("city_name").toString());
			       }
			       if(document.getFieldValue("id") != null) {
			    	   city.setId(document.getFieldValue("id").toString());
			       }
			       if(document.getFieldValue("description") != null) {
			    	   city.setDescription(document.getFieldValue("description").toString());
			       }
			       if(document.getFieldValue("currentTemperature") != null) {
			    	   city.setTemperature(document.getFieldValue("currentTemperature").toString());
			       }
			       if(document.getFieldValue("city_image") != null) {
			    	   city.setLandingImagePath(document.getFieldValue("city_image").toString());
			       }
			       if(document.getFieldValue("countryId") != null) {
			    	   city.setCityCountry(document.getFieldValue("countryId").toString());
			       }
			       if(document.getFieldValue("country_name") != null) {
			    	   city.setCityCountryName(document.getFieldValue("country_name").toString());
			       }
			       cityList.add(city);
		    }
		    return cityList;
	  }
	
	public List<CityResponse> getCityFromCode(String param) throws MalformedURLException, SolrServerException {
	    HttpSolrServer solr = new HttpSolrServer(cityUrl);
	    SolrQuery query = new SolrQuery();
	   
	   
	    String queryStr = "id:"+param;
	    query.setQuery(queryStr);
	    //query.setFields("city_name");
	    
	    query.setRows(10);
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    List<CityResponse> cityList = new ArrayList<CityResponse>();
	    
	    for (SolrDocument document : results) {
	    	CityResponse city = new CityResponse();
		       if(document.getFieldValue("city_name") != null) {
		    	   city.setCityName(document.getFieldValue("city_name").toString());
		       }
		       if(document.getFieldValue("description") != null) {
		    	   city.setDescription(document.getFieldValue("description").toString());
		       }
		       if(document.getFieldValue("city_image") != null) {
		    	   city.setLandingImagePath(document.getFieldValue("city_image").toString());
		       }
		       cityList.add(city);
	    }
	    return cityList;
  }
	
	
	public EntityResponse getAttractionEntityByCode(String attractionCode) throws MalformedURLException, SolrServerException {
		  //http://104.238.103.233:8983/solr/attraction/select?q=*%3A*&start=0&rows=50&fq={!geofilt%20pt=26.911385,75.74377%20sfield=latlng%20d=1}
		    
		  List<EntityResponse> entityList = new ArrayList<EntityResponse>();
		    if(null != attractionCode) {
		    	 	HttpSolrServer solr = new HttpSolrServer(attractionUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    String queryStr = "id:"+ '"' + attractionCode + '"';
				    query.setQuery(queryStr);
				    query.setRows(100);
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
					       if(document.getFieldValue("category_groups") != null) {
					    	   List<String> categoryIcons = (List<String>) document.getFieldValue("category_groups");
					    	   entity.setCategory_group_icons(categoryIcons);
					       }
					       if(document.getFieldValue("category_name") != null) {
					    	   String category =  document.getFieldValue("category_name").toString();
					    	   entity.setCategory(category);
					       }
					       if(document.getFieldValue("reqTime") != null) {
					    	   String reqTime =  document.getFieldValue("reqTime").toString();
					    	   entity.setReqTime(reqTime);
					       }
					       if(document.getFieldValue("bestTime") != null) {
					    	   String bestTime =  document.getFieldValue("bestTime").toString();
					    	   entity.setBestTime(bestTime);
					       }
					       if(document.getFieldValue("timings") != null) {
					    	   String timings =  document.getFieldValue("timings").toString();
					    	   entity.setTimings(timings);
					       }
					       if(document.getFieldValue("description") != null) {
					    	   String description =  document.getFieldValue("description").toString();
					    	   entity.setDescription(description);
					       }
					       
					       if(null != document.getFieldValue("latitude")) {
								entity.setLatitude(document.getFieldValue("latitude").toString());
							}
							if(null != document.getFieldValue("longitude")) {
								entity.setLongitude(document.getFieldValue("longitude").toString());
							}
					       if(document.getFieldValue("attraction_image_src") != null) {
					    	   List<String> images =  (List<String>) document.getFieldValue("attraction_image_src");
					    	   List<String> imgs = new ArrayList<String>(images.size());
					    	   for(String img : images) {
					    		   String str = img.split("___")[0];
					    		   imgs.add(str);
					    	   }
					    	   entity.setImageSrc(imgs);
					       }
					       entityList.add(entity);
					}
		    }
		    
		    if(!CollectionUtils.isEmpty(entityList)) {
		    	return entityList.get(0);	
		    }else {
		    	return null;
		    }
		    
		  
	  }	
	

	public HotelResponse getHotelEntityByCode(String hotelCode) throws MalformedURLException, SolrServerException {
		    
		  List<HotelResponse> entityList = new ArrayList<HotelResponse>();
		    if(null != hotelCode) {
		    	 	HttpSolrServer solr = new HttpSolrServer(hotelUrl);
				    SolrQuery query = new SolrQuery();
				  
				    
				    String queryStr = "id:"+hotelCode;
				    query.setQuery(queryStr);
				    query.setRows(10);
				    QueryResponse response = solr.query(query);
				    SolrDocumentList results = response.getResults();
				    
				    for (SolrDocument document : results) {
				    	HotelResponse entity = new HotelResponse();
					       if(document.getFieldValue("htl_name") != null) {
					    	   entity.setName(document.getFieldValue("htl_name").toString());
					       }
					       if(document.getFieldValue("id") != null) {
					    	   entity.setId(document.getFieldValue("id").toString());
					       }
					       if(document.getFieldValue("htl_city_code") != null) {
					    	   entity.setCityCode(document.getFieldValue("htl_city_code").toString());
					       }
					       if(document.getFieldValue("htl_street") != null) {
					    	   String street =  document.getFieldValue("htl_street").toString();
					    	   entity.setAddress(street);
					       }
					       if(document.getFieldValue("htl_image_src") != null) {
					    	   String images =  document.getFieldValue("htl_image_src").toString();
					    	   if(null != images) {
					    		   List<String> imageSrc = (List<String>) document.getFieldValue("htl_image_src");
					    		   entity.setHotelImages(imageSrc);
					    	   }
					    	   
					       }
					       
					       if(null != document.getFieldValue("htl_ta_hotel_rank")) {
								entity.setRating(document.getFieldValue("htl_ta_hotel_rank").toString());
							}
							
							if(null != document.getFieldValue("htl_rating")) {
								entity.setRating(document.getFieldValue("htl_rating").toString());
							}
							
							if(null != document.getFieldValue("htl_description")) {
								entity.setDescription(document.getFieldValue("htl_description").toString());
							}
							if(null != document.getFieldValue("htl_latitude")) {
								entity.setLatitude(document.getFieldValue("htl_latitude").toString());
							}
							if(null != document.getFieldValue("htl_longitude")) {
								entity.setLongitude(document.getFieldValue("htl_longitude").toString());
							}
							if(document.getFieldValue("htl_amenity_name") != null) {
						    	   List<String> amenities = (List<String>) document.getFieldValue("htl_amenity_name");
						    	   entity.setAmenities(amenities);
						       }
					       
					       entityList.add(entity);
					}
		    }
		    
		    if(!CollectionUtils.isEmpty(entityList)) {
		    	return entityList.get(0);	
		    }else {
		    	return null;
		    }
		    
		  
	  }	
	
	
	
	public List<EntityResponse> getEntityListFromTripId(Long tripId) throws MalformedURLException, SolrServerException {
		HttpSolrServer solr = new HttpSolrServer(tripUrl);
	    SolrQuery query = new SolrQuery();
	   
	    String queryStr = "id:"+tripId;
	    query.setQuery(queryStr); 
	    query.setRows(10);
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    List<EntityResponse> entityList = new ArrayList<EntityResponse>();
	    
	    for (SolrDocument document : results) {
	    	EntityResponse entityDetails = new EntityResponse();
		       if(document.getFieldValue("entity_details_all") != null) {
		    	   entityDetails.setName(document.getFieldValue("entity_details_all").toString());
		       }
		       entityList.add(entityDetails);
	    }
	    return entityList;
  }
	
	
	public UserResponse getUserDetails(String userId) throws SolrServerException {
		HttpSolrServer solrUser = new HttpSolrServer(userUrl);
	    SolrQuery queryUser = new SolrQuery();
		  
	    String queryStrUser = "user_id:"+'"'+userId+'"';
	    queryUser.setQuery(queryStrUser);
	    queryUser.setRows(5);
	    queryUser.setFields("name,last_name,user_id,role,trip_id,account_id");
	    QueryResponse responseUser = solrUser.query(queryUser);
	    SolrDocumentList resultsUser = responseUser.getResults();
	    
	    
	    if(null != resultsUser) {
	    	SolrDocument document = resultsUser.get(0);
	    	UserResponse entity = new UserResponse();
		       if(document.getFieldValue("name") != null && document.getFieldValue("last_name") != null) {
		    	   entity.setName(document.getFieldValue("name").toString() + " " +document.getFieldValue("last_name").toString());
		       }
		       if(document.getFieldValue("user_id") != null) {
		    	   entity.setId(document.getFieldValue("user_id").toString());
		       }
		       if(document.getFieldValue("account_id") != null) {
		    	   entity.setAccountId(document.getFieldValue("account_id").toString());
		       }
		       if(document.getFieldValue("role") != null) {
		    	   entity.setRole(document.getFieldValue("role").toString());
		       }
		       if(document.getFieldValue("trip_id") != null) {
		    	   List<String> trips = (List<String>) document.getFieldValue("trip_id");
		    	   entity.setTripIds(trips);
		    	}
		       return entity;
	    }
	    
	    return null;	
	    
	}
	
	
	public UserResponse getUserDetailsByAccountId(String accountId) throws SolrServerException {
		HttpSolrServer solrUser = new HttpSolrServer(userUrl);
	    SolrQuery queryUser = new SolrQuery();
		  
	    String queryStrUser = "account_id:"+'"'+accountId+'"';
	    queryUser.setQuery(queryStrUser);
	    queryUser.setRows(5);
	    queryUser.setFields("name,last_name,user_id,role,trip_id,account_id");
	    QueryResponse responseUser = solrUser.query(queryUser);
	    SolrDocumentList resultsUser = responseUser.getResults();
	    
	    
	    if(null != resultsUser) {
	    	SolrDocument document = resultsUser.get(0);
	    	UserResponse entity = new UserResponse();
		       if(document.getFieldValue("name") != null && document.getFieldValue("last_name") != null) {
		    	   entity.setName(document.getFieldValue("name").toString() + " " +document.getFieldValue("last_name").toString());
		       }
		       if(document.getFieldValue("user_id") != null) {
		    	   entity.setId(document.getFieldValue("user_id").toString());
		       }
		       if(document.getFieldValue("account_id") != null) {
		    	   entity.setAccountId(document.getFieldValue("account_id").toString());
		       }
		       if(document.getFieldValue("role") != null) {
		    	   entity.setRole(document.getFieldValue("role").toString());
		       }
		       if(document.getFieldValue("trip_id") != null) {
		    	   List<String> trips = (List<String>) document.getFieldValue("trip_id");
		    	   entity.setTripIds(trips);
		    	}
		       return entity;
	    }
	    
	    return null;	
	    
	}
	
	
	public UserResponse getUserDetailsForAnalytics(String userOrAccountId) throws SolrServerException {
		HttpSolrServer solrUser = new HttpSolrServer(userUrl);
	    SolrQuery queryUser = new SolrQuery();
		
	    String queryStrUserId = "user_id:"+'"'+userOrAccountId+'"';
	    queryUser.setQuery(queryStrUserId);
	    queryUser.setRows(5);
	    queryUser.setFields("name,last_name,user_id,role,trip_id,account_id");
	    QueryResponse responseUser = solrUser.query(queryUser);
	    SolrDocumentList resultsUser = responseUser.getResults();
	    
	    if(CollectionUtils.isEmpty(resultsUser)) {
	    	String queryStrUserAccountId = "account_id:"+'"'+userOrAccountId+'"';
		    queryUser.setQuery(queryStrUserAccountId);
		    queryUser.setRows(5);
		    queryUser.setFields("name,last_name,user_id,role,trip_id,account_id");
		    responseUser = solrUser.query(queryUser);
		    resultsUser = responseUser.getResults();
	    }
	    
	    if(CollectionUtils.isEmpty(resultsUser)) {
	    	String queryStrUserDomainId = "id:"+userOrAccountId;
		    queryUser.setQuery(queryStrUserDomainId);
		    queryUser.setRows(5);
		    queryUser.setFields("name,last_name,user_id,role,trip_id,account_id");
		    responseUser = solrUser.query(queryUser);
		    resultsUser = responseUser.getResults();
	    }
	    
	    
	    if(!CollectionUtils.isEmpty(resultsUser)) {
	    	SolrDocument document = resultsUser.get(0);
	    	UserResponse entity = new UserResponse();
		       if(document.getFieldValue("name") != null){
		    	   if(document.getFieldValue("last_name") != null) {
		    		   entity.setName(document.getFieldValue("name").toString() + " " +document.getFieldValue("last_name").toString());
		    	   }else {
		    		   entity.setName(document.getFieldValue("name").toString());
		    	   }
		       }
		       if(document.getFieldValue("user_id") != null) {
		    	   entity.setId(document.getFieldValue("user_id").toString());
		       }
		       if(document.getFieldValue("account_id") != null) {
		    	   entity.setAccountId(document.getFieldValue("account_id").toString());
		       }
		       if(document.getFieldValue("role") != null) {
		    	   entity.setRole(document.getFieldValue("role").toString());
		       }
		       if(document.getFieldValue("trip_id") != null) {
		    	   List<String> trips = (List<String>) document.getFieldValue("trip_id");
		    	   entity.setTripIds(trips);
		    	}
		       return entity;
	    }
	    
	    return null;	
	    
	}
	
	
	@Scheduled(fixedRate = 120000)
    public void solrDeltaImport() {
	
		if(StringUtils.isNotEmpty(profileType) && "prod".equalsIgnoreCase(profileType)) {
		 RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<String> response = restTemplate.getForEntity(attractionUrl+"/dataimport?command=delta-import&clean=false&commit=true", String.class, new Object[]{});
		 
		 ResponseEntity<String> response2 = restTemplate.getForEntity(cityUrl+"/dataimport?command=delta-import&clean=false&commit=true", String.class, new Object[]{});
		 
		 //ResponseEntity<String> response3 = restTemplate.getForEntity(countryUrl+"/dataimport?command=delta-import&clean=false&commit=true", String.class, new Object[]{});
		 
		 //ResponseEntity<String> response4 = restTemplate.getForEntity(stateUrl+"/dataimport?command=delta-import&clean=false&commit=true", String.class, new Object[]{});
		 
		 ResponseEntity<String> response5 = restTemplate.getForEntity(userUrl+"/dataimport?command=full-import&clean=true&commit=true", String.class, new Object[]{});
		 
		 ResponseEntity<String> response6 = restTemplate.getForEntity(tripUrl+"/dataimport?command=full-import&clean=true&commit=true", String.class, new Object[]{});
		 
		 ResponseEntity<String> response7 = restTemplate.getForEntity(hotelUrl+"/dataimport?command=delta-import&clean=false&commit=true", String.class, new Object[]{});
		 
		/* System.out.println(response.getStatusCode());
		 System.out.println(response2.getStatusCode());
		 //System.out.println(response3.getBody());
		 //System.out.println(response4.getBody());
		 System.out.println(response5.getStatusCode());
		 System.out.println(response6.getStatusCode());
		 System.out.println(response7.getStatusCode());*/
		 logger.warn("Full/Delta import run successfully");
		}
		 
	
	}
	
	

}
