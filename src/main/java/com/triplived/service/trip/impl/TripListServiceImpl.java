package com.triplived.service.trip.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.dto.TripBetweenCityDTO;
import com.connectme.domain.triplived.dto.TripBetweenCityRestClientDTO;
import com.connectme.domain.triplived.trip.dto.Trip;
import com.google.gson.Gson;
import com.triplived.rest.trip.TripClient;
import com.triplived.service.trip.TripListService;

@Service
public class TripListServiceImpl implements TripListService {

	@Autowired
	private TripClient tripClient;
	
	@Override
	public List<TripBetweenCityDTO> getTripBetweenCities(String source,
			String destination) {
		
		Gson gson = new Gson();
		try {
			String x = "Ayan";
			List<TripBetweenCityRestClientDTO> trips = tripClient.getTripBetweenCities(source, destination);
			
			List<TripBetweenCityDTO> tripBtnCities = new ArrayList<TripBetweenCityDTO>();
			
			for(TripBetweenCityRestClientDTO obj : trips) {
				TripBetweenCityDTO tripObj = new TripBetweenCityDTO();
				tripObj.setId(obj.getId());
				tripObj.setSourceCityCode(obj.getSourceCityCode());
				tripObj.setDestinationCityCode(obj.getDestinationCityCode());
				tripObj.setUserId(obj.getUserId());
				tripObj.setUserName(obj.getUserName());
				
				Trip trip = gson.fromJson(obj.getTripData(), Trip.class);
				tripObj.setTripData(trip);
				tripBtnCities.add(tripObj);
			}
			
			return tripBtnCities;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

		
}
