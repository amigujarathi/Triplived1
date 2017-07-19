package com.triplived.service.trip.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.VideoStatus;
import com.connectme.domain.triplived.dto.TripVideoDTO;
import com.connectme.domain.triplived.dto.TripVideoPathDTO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.dao.trip.TripVideoDao;
import com.triplived.entity.TripDb;
import com.triplived.entity.TripVideoDb;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.trip.TripEntityDisplayService;
import com.triplived.service.trip.TripVideoService;

@Service
public class TripEntityDisplayServiceImpl implements TripEntityDisplayService {

	@Autowired
	StaticContent sc;
	
	@Override
	public List<EntityResponse> getEntityDetailsFromId(Long tripId) {
		
		try {
			List<EntityResponse> entityResponse = sc.getEntityListFromTripId(tripId);
			return entityResponse;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
