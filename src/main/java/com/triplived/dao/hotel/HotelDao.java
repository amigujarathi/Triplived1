package com.triplived.dao.hotel;

import com.triplived.entity.HotelDb;
import com.triplived.entity.HotelImageDb;

public interface HotelDao {

	HotelDb updateHotel(HotelDb hotel);

	HotelDb getHotel(Long id);
	
	String getHotelNamebyId(String id);

	HotelImageDb getHotelImageById(String hotelId);

	void updateHotelImage(HotelImageDb hotelImage);

}
