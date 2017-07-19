package com.triplived.service.trip;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.ExploreTagsDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;

public interface TripSearchService {

	List<EntityResponseDTO> autoCompleteForTripSearch(String param)
			throws SolrServerException;

	List<TripSearchDTO> searchTrips(String param, String type)
			throws SolrServerException;

	List<ExploreTagsDTO> getExploreTags();

	List<TripSearchDTO> searchTripsByCategory(String param, String type)
			throws SolrServerException;


}