package com.triplived.service.trip.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.AttractionReviewResponse;
import com.connectme.domain.triplived.HotelReviewResponse;
import com.connectme.domain.triplived.dto.AttractionCrowdSourcedDTO;
import com.connectme.domain.triplived.dto.AttractionCrowdSourcedReviewDTO;
import com.connectme.domain.triplived.dto.AttractionCuratedContentDTO;
import com.connectme.domain.triplived.dto.HotelCrowdSourcedDTO;
import com.connectme.domain.triplived.dto.HotelCrowdSourcedReviewDTO;
import com.connectme.domain.triplived.dto.HotelCuratedContentDTO;
import com.triplived.rest.client.TripEntityCrowdSourcedContent;
import com.triplived.service.trip.TripEntityCrowdSourcedService;

@Service
public class TripEntityCrowdSourcedServiceImpl implements TripEntityCrowdSourcedService{

	@Autowired
	private TripEntityCrowdSourcedContent tripEntityCrowdSourcedContent;

	
	@Override
	public AttractionCrowdSourcedDTO getAttractionCrowdSourcedDataFromId(String attractionId) throws SolrServerException {
		
		List<AttractionReviewResponse> entityReviewList = null;
		try {
			entityReviewList = tripEntityCrowdSourcedContent.getAttractionCrowdSourcedDataFromId(attractionId);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertAttractionResponseDTO(entityReviewList, attractionId);
	}

	private AttractionCrowdSourcedDTO convertAttractionResponseDTO(List<AttractionReviewResponse> entityReviewList, String attractionId) {
			
			AttractionCrowdSourcedDTO finalDto = new AttractionCrowdSourcedDTO();
			List<AttractionCrowdSourcedReviewDTO> crowdSourcedReviews = new ArrayList<AttractionCrowdSourcedReviewDTO>();
			Long recom = 0l;
			Long nonRecom = 0l;
			
			for(AttractionReviewResponse obj : entityReviewList) {
				AttractionCrowdSourcedReviewDTO objt = new AttractionCrowdSourcedReviewDTO();
					if(StringUtils.isNotEmpty(obj.getReview())) {
			    		   
			    		      
			    			   String[] arr = obj.getReview().split("~~");
			    			   
			    			   //If review is not present, no point in showing it then to a user. Skip to the next one
			    			   if(StringUtils.isNotEmpty(arr[1])) {
			    				   objt.setReview(arr[1]);
			    			   }else {
			    				   continue;
			    			   }
			    			   
			    			   if(StringUtils.isNotEmpty(arr[0])) {
			    				   objt.setTimeStamp(arr[0]);
			    			   }
			    			   if(StringUtils.isNotEmpty(arr[2])) {
			    				   objt.setUserName(arr[2]);
			    			   }
			    			   if(StringUtils.isNotEmpty(arr[3])) {
			    				   objt.setUserId(Long.parseLong(arr[3]));
			    			   }
			    			   crowdSourcedReviews.add(objt);
			    		   
					}
					objt.setTripId(obj.getTripId().toString());
					if(StringUtils.isNotEmpty(obj.getRecommendation())) {
						
							if(obj.getRecommendation().equals("Y")) {
								recom++;
							}
							if(obj.getRecommendation().equals("N")) {
								nonRecom++;
							}
						
					}
			}
		
		finalDto.setNonRecommended(nonRecom);
		finalDto.setRecommended(recom);
		finalDto.setReviews(crowdSourcedReviews);
		finalDto.setId(attractionId);
		return finalDto;
	}

	
	@Override
	public HotelCrowdSourcedDTO getHotelCrowdSourcedDataFromId(String id) throws SolrServerException {
		
		List<HotelReviewResponse> entityReviewList = null;
		try {
			entityReviewList = tripEntityCrowdSourcedContent.getHotelCrowdSourcedDataFromId(id);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertHotelResponseDTO(entityReviewList, id);
	}

	private HotelCrowdSourcedDTO convertHotelResponseDTO(List<HotelReviewResponse> entityReviewList, String id) {
			
			HotelCrowdSourcedDTO finalDto = new HotelCrowdSourcedDTO();
			List<HotelCrowdSourcedReviewDTO> crowdSourcedReviews = new ArrayList<HotelCrowdSourcedReviewDTO>();
			Long recom = 0l;
			Long nonRecom = 0l;
			
			for(HotelReviewResponse obj : entityReviewList) {
				HotelCrowdSourcedReviewDTO objt = new HotelCrowdSourcedReviewDTO();	
					
				  if(StringUtils.isNotEmpty(obj.getReview())) {
			    		       
			    			   String[] arr = obj.getReview().split("~~");
			    			   
			    			   //If review is not present, no point in showing it then to a user. Skip to the next one
			    			   if(StringUtils.isNotEmpty(arr[1])) {
			    				   objt.setReview(arr[1]);
			    			   }else {
			    				   continue;
			    			   }
			    			   
			    			   if(StringUtils.isNotEmpty(arr[0])) {
			    				   objt.setTimeStamp(arr[0]);
			    			   }
			    			   if(StringUtils.isNotEmpty(arr[2])) {
			    				   objt.setUserName(arr[2]);
			    			   }
			    			   if(StringUtils.isNotEmpty(arr[3])) {
			    				   objt.setUserId(Long.parseLong(arr[3]));
			    			   }
			    			   
			    			   crowdSourcedReviews.add(objt);
			    		   
					}
				  	objt.setTripId(obj.getTripId().toString());
					if(StringUtils.isNotEmpty(obj.getRecommendation())) {
						
							if(obj.getRecommendation().equals("Y")) {
								recom++;
							}
							if(obj.getRecommendation().equals("N")) {
								nonRecom++;
							}
						
					}
			}
		
		finalDto.setNonRecommended(nonRecom);
		finalDto.setRecommended(recom);
		finalDto.setReviews(crowdSourcedReviews);
		finalDto.setId(id);
		return finalDto;
	}

}
