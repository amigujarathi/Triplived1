package com.triplived.service.hotel.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectme.domain.triplived.dto.NewOrUpdateHotelDetailsDTO;
import com.triplived.dao.hotel.HotelDao;
import com.triplived.entity.CityImageDb;
import com.triplived.entity.HotelDb;
import com.triplived.entity.HotelImageDb;
import com.triplived.service.hotel.AddUpdateHotelService;

@Service
public class AddUpdateHotelServiceImpl implements AddUpdateHotelService{

	@Autowired
	private HotelDao hotelDao;
	
	@Transactional
	@Override
	public HotelDb addNewHotel(NewOrUpdateHotelDetailsDTO obj) {
		HotelDb hotel = new HotelDb();
		hotel.setCityCode(obj.getCityId());
		hotel.setDescription(obj.getDescription());
		hotel.setLatitude(obj.getLatitude());
		hotel.setLongitude(obj.getLongitude());
		hotel.setName(obj.getName());
		if(null != obj.getRating()) {
			hotel.setStarRating(obj.getRating());
		}
		hotel.setStreet(obj.getAddress());
		hotel.setCreatedDate(new Date());
		hotel.setUpdateDate(new Date());
		hotel.setTaHotelId("new");
		hotel.setCreatedBy(obj.getCreatedBy());
		hotel.setUpdatedBy(obj.getCreatedBy());
		hotel.setStatus("A");
		HotelDb returnedHtl = hotelDao.updateHotel(hotel);
		
		return returnedHtl;
	}
	
	@Transactional
	@Override
	public HotelDb updateHotel(NewOrUpdateHotelDetailsDTO obj) {
		HotelDb hotel = hotelDao.getHotel(obj.getId());
		
		hotel.setCityCode(obj.getCityId());
		hotel.setDescription(obj.getDescription());
		hotel.setLatitude(obj.getLatitude());
		hotel.setLongitude(obj.getLongitude());
		hotel.setName(obj.getName());
		if(null != obj.getRating()) {
			hotel.setStarRating(obj.getRating());
		}
		hotel.setStreet(obj.getAddress());
		//hotel.setCreatedDate(new Date());
		hotel.setUpdateDate(new Date());
		hotel.setUpdatedBy(obj.getCreatedBy());
		hotel.setStatus("A");
		hotelDao.updateHotel(hotel);
		return hotel;
	}
	
	@Transactional
	@Override
	public HotelDb updateHotelInDbDirectly(HotelDb obj) {
		HotelDb hotel = hotelDao.updateHotel(obj);
		return hotel;
	}
	
	@Override
	@Transactional
	public void updateHotelImagePath(String hotelId, String path, String updatedBy) {
		
		//Check for hotel image if it exists from before. If yes, then set status to inactive and update the new one
		HotelImageDb hotelImageObj = hotelDao.getHotelImageById(hotelId);
		if(null != hotelImageObj) {
			hotelImageObj.setImagePath(path);
			hotelImageObj.setUpdatedDate(new Date());
			hotelImageObj.setAddedBy(updatedBy);
		}else {
			hotelImageObj = new HotelImageDb();
			hotelImageObj.setHotelId(hotelId);
			hotelImageObj.setCreatedDate(new Date());
			hotelImageObj.setUpdatedDate(new Date());
			hotelImageObj.setImagePath(path);
			hotelImageObj.setStatus("A");
			hotelImageObj.setAddedBy(updatedBy);
		}
		hotelDao.updateHotelImage(hotelImageObj);
		
	}
	
}
