package com.triplived.dao.user;

import java.io.Serializable;

import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.UserMetaDb;

/**
 * Responsibility: All operation related to User META DATA
 * 
 * All META DATA is listed in class {@link TripMetaData}
 * 
 * @author santosh 
 *
 */
public interface UserMetaDAO extends GenericDAO<UserMetaDb, Serializable> {

	
	 /**
	  * Which meta data count to increment
	  */

	public void updateMetaDataCount(Long userId, TripMetaData metaData);

	/**
	 * returns the count of ...
	 * 
	 * @param tripId
	 * @param metaData
	 * @return
	 */
	public int getCountFor(Long userId, TripMetaData  metaData);

	/**
	 * 
	 * @param userID
	 * @param metaData
	 * @param count
	 */
	void updateMetaDataCount(Long userId, TripMetaData metaData, int count);

}