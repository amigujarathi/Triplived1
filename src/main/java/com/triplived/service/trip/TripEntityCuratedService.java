package com.triplived.service.trip;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.connectme.domain.triplived.dto.AttractionCuratedContentDTO;
import com.connectme.domain.triplived.dto.HotelCuratedContentDTO;

public interface TripEntityCuratedService {

	List<AttractionCuratedContentDTO> getAttractionCuratedSuggestionsFromTripId(String attractionId)
			throws SolrServerException;

	List<HotelCuratedContentDTO> getHotelCuratedSuggestionsFromTripId(String hotelId)
			throws SolrServerException;
}