package com.triplived.service.trip.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.EntityResponse;
import com.connectme.domain.triplived.dto.EntityResponseDTO;
import com.connectme.domain.triplived.dto.ExploreTagsDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.triplived.dao.trip.TripDAO;
import com.triplived.entity.ExploreTagsDb;
import com.triplived.rest.client.TripSearchContent;
import com.triplived.service.trip.TripSearchService;

@Service
public class TripSearchServiceImpl implements TripSearchService{

	@Autowired
	private TripSearchContent tripSearchContent;
	
	@Autowired
	private TripDAO tripDao;
	
	@Override
	public List<EntityResponseDTO> autoCompleteForTripSearch(String param) throws SolrServerException {
		
		List<EntityResponse> entityList = tripSearchContent.autoCompleteForTripSearch(param);
		return convertEntityResponseDTO(entityList);
		
	}
	
	@Override
	public List<TripSearchDTO> searchTrips(String param, String type) throws SolrServerException {
		List<TripSearchDTO> trips = tripSearchContent.searchTrips(param, type);
			
		//ADITI_TO_DO - write code here to get all the attractions for the searched trips and populate the filter
		//object with those attractions. Then, the same needs to be done for attraction categories. The DTO for 
		//both of these have already been created.
		
		return trips;
	}
	
	@Override
	public List<TripSearchDTO> searchTripsByCategory(String param, String type) throws SolrServerException {
		List<TripSearchDTO> trips = tripSearchContent.searchTripsByCategory(param, type);
			
		//ADITI_TO_DO - write code here to get all the attractions for the searched trips and populate the filter
		//object with those attractions. Then, the same needs to be done for attraction categories. The DTO for 
		//both of these have already been created.
		
		return trips;
	}
	
	
    private List<EntityResponseDTO> convertEntityResponseDTO(List<EntityResponse> list) {
		
		List<EntityResponseDTO> listDTO = new ArrayList<EntityResponseDTO>();
		
		for(EntityResponse obj : list) {
			EntityResponseDTO dto = new EntityResponseDTO();
			dto.setId(obj.getId());
			dto.setCityCode(obj.getCityCode());
			dto.setAddress(obj.getAddress());
			dto.setName(obj.getName());
			dto.setType(obj.getType());
			dto.setLocality(obj.getLocality());
			dto.setLatitude(obj.getLatitude());
			dto.setLongitude(obj.getLongitude());
			dto.setImageSrc(obj.getImageSrc());
			dto.setStreet(obj.getStreet());
			dto.setDistanceFromCoordinates(obj.getDistanceFromCoordinates());
			dto.setCategory(obj.getCategory());
			dto.setFoundAtQueryDistance(obj.getFoundAtQueryDistance());
			dto.setAttractionId(obj.getAttractionId());
			dto.setCategoryGroupIcons(obj.getCategory_group_icons());
			dto.setCityName(obj.getCityName());
			dto.setCountryName(obj.getCountryName());
			dto.setState(obj.getState());
			dto.setMiscMap(obj.getMiscMap());
			//dto.setUserFbId(obj.getUserFbId());
			listDTO.add(dto);
		}
		
		return listDTO;
	}
    
    @Override
    public List<ExploreTagsDTO> getExploreTags() {
    	List<ExploreTagsDb> list = tripDao.getExploreTags();
    	List<ExploreTagsDTO> listDto = new ArrayList<ExploreTagsDTO>();
    	for(ExploreTagsDb obj : list) {
    		ExploreTagsDTO dto = new ExploreTagsDTO();
    		dto.setId(obj.getId());
    		dto.setImage(obj.getImage());
    		dto.setText(obj.getText());
    		dto.setType(obj.getType());
    		listDto.add(dto);
    	}
    	return listDto;
    }

}
