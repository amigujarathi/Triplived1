package com.triplived.service.staticContent.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.AttractionResponse;
import com.connectme.domain.triplived.dto.AttractionResponseDTO;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.PersonDb;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.staticContent.StaticAttractionService;

@Service
public class StaticAttractionServiceImpl implements StaticAttractionService{

	@Value("${attractionUrl}")
	private String attractionUrl;
   
    @Autowired
	private StaticContent staticContent;
    
    @Autowired
    private UserDao userDao;
    
	@Override
	public List<AttractionResponseDTO> suggestAttractionByCity(String city, String param) {
			try {
				List<AttractionResponse> attractionDocumentList = staticContent.suggestAttractions(city, param);
				return convertAttractionDTO(attractionDocumentList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			return null;
	}
	
	@Override
	public List<AttractionResponseDTO> getPopularAttractionsOfCity(String city) {
			try {
				List<AttractionResponse> attractionDocumentList = staticContent.getPopularAttractionsOfCity(city);
				return convertAttractionDTO(attractionDocumentList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			return null;
	}
	
	
	private List<AttractionResponseDTO> convertAttractionDTO(List<AttractionResponse> attractions) {
		
		List<AttractionResponseDTO> listOfAttractions = new ArrayList<AttractionResponseDTO>();
		for(AttractionResponse obj : attractions) {
			AttractionResponseDTO newObj = new AttractionResponseDTO();
			newObj.setId(obj.getId());
			newObj.setAttractionName(obj.getAttractionName());
			newObj.setLatitude(obj.getLatitude());
			newObj.setLongitude(obj.getLongitude());
			newObj.setDescription(obj.getDescription());
			newObj.setImages(obj.getImages());
			newObj.setPunchline(obj.getPunchLine());
			newObj.setCategories(obj.getCategories());
			newObj.setGooglePlaceId(obj.getGooglePlaceId());
			newObj.setGoogleplaceName(obj.getGoogleplaceName());
			newObj.setBestTime(obj.getBestTime());
			newObj.setReqTime(obj.getReqTime());
			newObj.setTimings(obj.getTimings());
			newObj.setAddress(obj.getAddress1());
			newObj.setPhoneNumber(obj.getPhoneNmber());
			newObj.setTicket(obj.getTicket());
			newObj.setWebSite(obj.getWebSite());
			newObj.setUpdatedDate(obj.getUpdatedDate());
			newObj.setUpdatedBy(obj.getUpdatedBy());
						
			listOfAttractions.add(newObj);
		}
		Collections.sort(listOfAttractions);
		return listOfAttractions;
	}
}
