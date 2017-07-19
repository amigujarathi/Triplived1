package com.triplived.dao.hotel.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.hotel.HotelDao;
import com.triplived.entity.HotelDb;
import com.triplived.entity.HotelImageDb;

@Repository
@Transactional("txManager")
public class HotelDaoImpl extends GenericHibernateDAO<HotelDb, Serializable>
		implements HotelDao {

	@Override
	public HotelDb updateHotel(HotelDb hotel) {
		HotelDb htlReturn = saveOrUpdate(hotel);
		return htlReturn;
	}

	@Override
	public HotelDb getHotel(Long id) {
		return (HotelDb) getSession().get(HotelDb.class, id);
	}

	@Override
	public String getHotelNamebyId(String id) {
		String hotelName = (String) getSession()
				.createQuery(
						"Select name FROM com.triplived.entity.HotelDb hotel where HOTEL_ID =:id")
				.setLong("id", Long.parseLong(id)).uniqueResult();
		return hotelName;

	}
	
	@Override
	@Transactional(readOnly = true)
	public HotelImageDb getHotelImageById(String hotelId) {
		HotelImageDb hotelImageDb = (HotelImageDb) getSession()
				.createQuery(
						"SELECT triplived_hotel_image FROM  com.triplived.entity.HotelImageDb triplived_hotel_image where HOTEL_ID = :hotelId and STATUS = 'A'")
				.setString("hotelId", hotelId).uniqueResult();

		return hotelImageDb;

	}
	
	@Override
	@Transactional
	public void updateHotelImage(HotelImageDb hotelImage) {
		getSession().saveOrUpdate(hotelImage);
	}


}
