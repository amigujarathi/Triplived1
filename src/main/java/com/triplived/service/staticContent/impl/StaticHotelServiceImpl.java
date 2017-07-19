package com.triplived.service.staticContent.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectme.domain.triplived.HotelResponse;
import com.connectme.domain.triplived.dto.HotelResponseDTO;
import com.triplived.rest.client.StaticContent;
import com.triplived.service.staticContent.StaticHotelService;

@Service
public class StaticHotelServiceImpl implements StaticHotelService{

	
    @Autowired
	private StaticContent staticContent;
    
    @Override
	public List<HotelResponseDTO> getHotelByCity(String city) {
		try {
			List<HotelResponse> hotelDocumentList = staticContent.getHotels();
			return convertHotelDTO(hotelDocumentList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
    
    @Override
	public List<HotelResponseDTO> suggestHotelByCity(String city, String param) {
		try {
			List<HotelResponse> hotelDocumentList = staticContent.suggestHotels(city, param);
			return convertHotelDTO(hotelDocumentList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
    
    /**
     * This method is for backend portal for add/update hotels
     * @param city
     * @param param
     * @return
     */
    @Override
	public List<HotelResponseDTO> getHotelsForPortal(String city, String param) {
		try {
			List<HotelResponse> hotelDocumentList = staticContent.suggestHotels(city, param);
			return convertHotelDTOForPortal(hotelDocumentList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	private List<HotelResponseDTO> convertHotelDTO(List<HotelResponse> hotels) {
		
		List<HotelResponseDTO> listOfHotels = new ArrayList<HotelResponseDTO>();
		for(HotelResponse obj : hotels) {
			HotelResponseDTO newObj = new HotelResponseDTO();
			newObj.setId(obj.getId());
			newObj.setName(obj.getName());
			newObj.setLatitude(obj.getLatitude());
			newObj.setLongitude(obj.getLongitude());
			newObj.setAmenities(obj.getAmenities());
			newObj.setHotelImages(obj.getHotelImages());
			listOfHotels.add(newObj);
		}
		return listOfHotels;
	}
	
private List<HotelResponseDTO> convertHotelDTOForPortal(List<HotelResponse> hotels) {
		
		List<HotelResponseDTO> listOfHotels = new ArrayList<HotelResponseDTO>();
		for(HotelResponse obj : hotels) {
			HotelResponseDTO newObj = new HotelResponseDTO();
			newObj.setId(obj.getId());
			newObj.setName(obj.getName());
			newObj.setLatitude(obj.getLatitude());
			newObj.setLongitude(obj.getLongitude());
			newObj.setAmenities(obj.getAmenities());
			newObj.setHotelImages(obj.getHotelImages());
			newObj.setAddress(obj.getAddress());
			
			if(null != obj.getTaRank()) {
				newObj.setRating(obj.getTaRank());
			}
			
			if(null != obj.getRating()) {
				String rating = obj.getRating().toString();
				if(!StringUtils.isEmpty(rating)) {
					newObj.setRating(obj.getRating());
				}
			}
			
			newObj.setDescription(obj.getDescription());
			newObj.setHotelImages(obj.getHotelImages());
			listOfHotels.add(newObj);
		}
		return listOfHotels;
	}



	

}
