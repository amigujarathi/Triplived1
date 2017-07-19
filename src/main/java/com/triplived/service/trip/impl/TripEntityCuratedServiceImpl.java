package com.triplived.service.trip.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.HotelReviewResponse;
import com.connectme.domain.triplived.dto.AttractionCuratedContentDTO;
import com.connectme.domain.triplived.dto.HotelCuratedContentDTO;
import com.triplived.rest.client.TripEntityCrowdSourcedContent;
import com.triplived.service.trip.TripEntityCuratedService;

@Service
public class TripEntityCuratedServiceImpl implements TripEntityCuratedService{

	@Autowired
	private TripEntityCrowdSourcedContent tripEntityCuratedContent;

	/**
	 * TBD later when live version is ready
	 */
	@Override
	public List<AttractionCuratedContentDTO> getAttractionCuratedSuggestionsFromTripId(String attractionId) throws SolrServerException {
		
		/*List<AttractionReviewResponse> entityReviewList = null;
		try {
			entityReviewList = tripEntityCuratedContent.getAttractionCuratedSuggestionsFromTripId(attractionId);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertAttractionResponseDTO(entityReviewList);*/
		return null;
		
	}

	/*private List<AttractionCuratedContentDTO> convertAttractionResponseDTO(
			List<AttractionReviewResponse> entityReviewList) {
			
			List<AttractionCuratedContentDTO> listDTO = new ArrayList<AttractionCuratedContentDTO>();
			
			for(AttractionReviewResponse obj : entityReviewList) {
				AttractionCuratedContentDTO dto = new AttractionCuratedContentDTO();
					dto.setTripId(obj.getTripId());
					dto.setAttractionId(obj.getAttractionId());
					dto.setSuggestion(obj.getSuggestion());
					dto.setReview(obj.getReview());
					dto.setSource(obj.getSource());
					dto.setRank(obj.getRank());
					dto.setStatus(obj.getStatus());
				//dto.setUserFbId(obj.getUserFbId());
				listDTO.add(dto);
			}
		return listDTO;
	}
*/
	/**
	 * TBD later when live version is ready
	 */
	@Override
	public List<HotelCuratedContentDTO> getHotelCuratedSuggestionsFromTripId(String hotelId)
			throws SolrServerException {
		/*List<HotelReviewResponse> entityReviewList = null;
		entityReviewList = tripEntityCuratedContent.getHotelCuratedSuggestionsFromTripId(hotelId);
		return convertHotelResponseDTO(entityReviewList);*/
		return null;
	}

	private List<HotelCuratedContentDTO> convertHotelResponseDTO(
			List<HotelReviewResponse> entityReviewList) {
		/*List<HotelCuratedContentDTO> listDTO = new ArrayList<HotelCuratedContentDTO>();
		
		for(HotelReviewResponse obj : entityReviewList) {
			HotelCuratedContentDTO dto = new HotelCuratedContentDTO();
				dto.setTripId(obj.getTripId());
				dto.setHotelId(obj.getHotelId());
				dto.setSuggestion(obj.getSuggestion());
				dto.setReview(obj.getReview());
				dto.setSource(obj.getSource());
				dto.setRank(obj.getRank());
				dto.setStatus(obj.getStatus());
			//dto.setUserFbId(obj.getUserFbId());
			listDTO.add(dto);
		}
	return listDTO;*/
		return null;
	}

}
