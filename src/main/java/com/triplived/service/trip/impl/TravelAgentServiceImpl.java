package com.triplived.service.trip.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.TravelAgentDetailsDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.triplived.dao.trip.TravelAgentDao;
import com.triplived.entity.TravelAgentDetailsDb;
import com.triplived.entity.TravelPartnerTripMappingDb;
import com.triplived.rest.trip.TripClient;
import com.triplived.service.trip.TravelAgentService;

@Service
public class TravelAgentServiceImpl implements TravelAgentService{
	
	@Autowired
	private TravelAgentDao travelAgentDao;
	
	@Autowired
	private TripClient tripClient;
	
	@Override
	@Transactional
	public List<TravelAgentDetailsDTO>  getTravelAgentList() {
		List<TravelAgentDetailsDb> list = travelAgentDao.getTravelAgentList();
		List<TravelAgentDetailsDTO> dtoList = new ArrayList<TravelAgentDetailsDTO>();
		for(TravelAgentDetailsDb travel : list) {
			dtoList.add(convertTravelAgent(travel));
		}
		
		return dtoList;
	}
	
	
	@Override
	@Transactional
	public TravelAgentDetailsDTO  getTravelAgentDetail(String id) {
		TravelAgentDetailsDb travel = travelAgentDao.getTravelAgentDetail(Long.parseLong(id));
		if(null != travel) {
			TravelAgentDetailsDTO obj = convertTravelAgent(travel);
			return obj;
		}
		return null;
	}
	
	@Override
	@Transactional
	public TravelAgentDetailsDTO  getTravelAgentDetailByCustomId(String id) {
		TravelAgentDetailsDb travel = travelAgentDao.getTravelAgentDetailByCustomId(id);
		if(null != travel) {
			TravelAgentDetailsDTO obj = convertTravelAgent(travel);
			obj.setTrips(getTravelPartnerTrips(obj.getId().toString()));
			return obj;
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public List<TripSearchDTO>  getTravelPartnerTrips(String id) {
		List<TravelPartnerTripMappingDb>  travelPartnerTrips = travelAgentDao.getTravelPartnerTrips(Long.parseLong(id));
		List<TripSearchDTO> trips = new ArrayList<TripSearchDTO>();

		if(CollectionUtils.isNotEmpty(travelPartnerTrips)) {
			for(TravelPartnerTripMappingDb obj : travelPartnerTrips) {
				TripSearchDTO trip;
				try {
					trip = tripClient.getTripDetails(obj.getTripId().toString());
					trips.add(trip);
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}
			return trips;
		}
		return null;
	}
	
	
	
	private TravelAgentDetailsDTO convertTravelAgent(TravelAgentDetailsDb obj) {
		TravelAgentDetailsDTO travel = new TravelAgentDetailsDTO();
		travel.setId(obj.getId());
		travel.setName(obj.getName());
		travel.setAddress(obj.getAddress());
		travel.setPhone(obj.getPhone());
		travel.setWebsite(obj.getWebsite());
		travel.setLogo(obj.getLogo());
		travel.setCustomId(obj.getCustomId());
		return travel;
	}
	
	
}
