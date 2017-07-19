package com.triplived.service.trip;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.connectme.domain.triplived.dto.TravelAgentDetailsDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;

public interface TravelAgentService {

	List<TravelAgentDetailsDTO> getTravelAgentList();

	TravelAgentDetailsDTO getTravelAgentDetail(String id);

	TravelAgentDetailsDTO getTravelAgentDetailByCustomId(String id);

	List<TripSearchDTO> getTravelPartnerTrips(String id) throws SolrServerException;

}
