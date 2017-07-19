package com.triplived.service.trip;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.connectme.domain.triplived.dto.AttractionCrowdSourcedDTO;
import com.connectme.domain.triplived.dto.HotelCrowdSourcedDTO;
import com.connectme.domain.triplived.dto.HotelCuratedContentDTO;

public interface TripEntityCrowdSourcedService {

	AttractionCrowdSourcedDTO getAttractionCrowdSourcedDataFromId(String attractionId)
			throws SolrServerException;

	HotelCrowdSourcedDTO getHotelCrowdSourcedDataFromId(String id)
			throws SolrServerException;
}