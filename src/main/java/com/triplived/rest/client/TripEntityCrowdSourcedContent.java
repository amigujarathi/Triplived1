package com.triplived.rest.client;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.connectme.domain.triplived.AttractionReviewResponse;
import com.connectme.domain.triplived.HotelReviewResponse;
import com.connectme.domain.triplived.dto.AttractionCrowdSourcedReviewDTO;

@Component
public class TripEntityCrowdSourcedContent {

	@Value("${attractionReviewDetails}")
	private String attractionReviewDetails;
	
	@Value("${hotelReviewDetails}")
	private String hotelReviewDetails;


	public List<AttractionReviewResponse> getAttractionCrowdSourcedDataFromId(String attractionId) throws MalformedURLException, SolrServerException {
		HttpSolrServer solr = new HttpSolrServer(attractionReviewDetails);
	    SolrQuery query = new SolrQuery();
	    String queryStr = "attractionId:"+attractionId;
	    query.setQuery(queryStr);
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    List<AttractionReviewResponse> entityList = new ArrayList<AttractionReviewResponse>();
	    
	    for (SolrDocument document : results) {
	    	AttractionReviewResponse attractionReview = new AttractionReviewResponse();
		       
		       if(document.getFieldValue("publicTripId") != null) {
		    	   attractionReview.setTripId(Long.parseLong((String) document.getFieldValue("publicTripId")));
		       }
		       if(document.getFieldValue("attractionId") != null) {
		    	   attractionReview.setAttractionId(document.getFieldValue("attractionId").toString());
		       }
		       if(document.getFieldValue("timestamp") != null) {
		    	   attractionReview.setTimestamp(document.getFieldValue("timestamp").toString());
		       }
		       if(document.getFieldValue("review") != null) {
		    	   String review = document.getFieldValue("review").toString();
		    	   attractionReview.setReview(review);
		    	   
		       }
		       if(document.getFieldValue("recommended") != null) {
		    	   String recommended = document.getFieldValue("recommended").toString();
		    	   attractionReview.setRecommendation(recommended);
		       }
		       entityList.add(attractionReview);
		    }

	    return entityList;
  }
	
	public List<HotelReviewResponse> getHotelCrowdSourcedDataFromId(String hotelId) throws MalformedURLException, SolrServerException {
		HttpSolrServer solr = new HttpSolrServer(hotelReviewDetails);
	    SolrQuery query = new SolrQuery();
	    String queryStr = "hotelId:"+hotelId;
	    query.setQuery(queryStr);
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    List<HotelReviewResponse> entityList = new ArrayList<HotelReviewResponse>();
	    
	    for (SolrDocument document : results) {
	    	   HotelReviewResponse hotelReview = new HotelReviewResponse();
		       
		       if(document.getFieldValue("publicTripId") != null) {
		    	   hotelReview.setTripId(Long.parseLong((String) document.getFieldValue("publicTripId")));
		       }
		       if(document.getFieldValue("hotelId") != null) {
		    	   hotelReview.setHotelId(document.getFieldValue("hotelId").toString());
		       }
		       if(document.getFieldValue("timestamp") != null) {
		    	   hotelReview.setTimestamp(document.getFieldValue("timestamp").toString());
		       }
		       if(document.getFieldValue("review") != null) {
		    	   String review = document.getFieldValue("review").toString();
		    	   hotelReview.setReview(review);
		       }
		       if(document.getFieldValue("recommended") != null) {
		    	   String recommended = document.getFieldValue("recommended").toString();
		    	   hotelReview.setRecommendation(recommended);
		       }
		       entityList.add(hotelReview);
		}

	    return entityList;
	}
	
	
	
	//@Scheduled(fixedRate = 120000)
    public void solrDeltaImport() {
	
		 RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<String> response6 = restTemplate.getForEntity(attractionReviewDetails+"/dataimport?command=full-import&clean=true&commit=true", String.class, new Object[]{});
		 ResponseEntity<String> response7 = restTemplate.getForEntity(hotelReviewDetails+"/dataimport?command=full-import&clean=true&commit=true", String.class, new Object[]{});
		 //System.out.println(response6.getBody());
		 //System.out.println(response7.getBody());
		 
		 
	
	}
}
