package com.triplived.service.hotel;

import com.connectme.domain.triplived.dto.NewOrUpdateHotelDetailsDTO;
import com.triplived.entity.HotelDb;

public interface AddUpdateHotelService {

	HotelDb addNewHotel(NewOrUpdateHotelDetailsDTO obj);

	HotelDb updateHotel(NewOrUpdateHotelDetailsDTO obj);

	HotelDb updateHotelInDbDirectly(HotelDb obj);

	void updateHotelImagePath(String hotelId, String path, String updatedBy);
	

}
