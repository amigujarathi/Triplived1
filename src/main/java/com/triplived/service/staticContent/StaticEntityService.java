package com.triplived.service.staticContent;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.dto.AttractionDetailsDTO;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.HotelDetailsDTO;

public interface StaticEntityService {

	public List<EntityResponse> getAttractionEntityByCoordinates(String lat, String lng);

	List<EntityResponse> getHotelEntityByCoordinates(String lat, String lng,
			String d);

	List<EntityResponse> getAllEntityByCoordinates(String lat, String lng,
			String d);

	List<EntityResponseDTO> getRestaurantEntityByCoordinates(String deviceId, String lat,
			String lng, String d);

	List<EntityResponseDTO> getAttractionExternalEntityByCoordinates(String deviceId,
			String lat, String lng, String d);

	List<EntityResponseDTO> getHotelExternalEntityByCoordinates(String deviceId, String lat,
			String lng, String d);

	List<EntityResponseDTO> orderListByDistance(String deviceId,
			List<EntityResponseDTO> attractions,
			List<EntityResponseDTO> restaurants,
			List<EntityResponseDTO> hotels, String lat, String lng);

	
	List<EntityResponseDTO> getAttractionExternalByCityCodeForMapping(
			String cityCode);

	List<EntityResponseDTO> getAttractionExternalEntityByCoordinatesForTimeline(String deviceId,
			String lat, String lng, String accuracyDist);

	List<EntityResponseDTO> getAttractionExternalEntityByCoordinatesForTriviaNotifications(
			String deviceId,String lat, String lng, String accuracyDist);

	AttractionDetailsDTO getAttractionDetails(String code)
			throws MalformedURLException, SolrServerException;

	HotelDetailsDTO getHotelDetails(String code) throws MalformedURLException,
			SolrServerException;
	
}
