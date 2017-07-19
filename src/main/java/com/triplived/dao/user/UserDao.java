package com.triplived.dao.user;

import java.io.Serializable;
import java.util.List;

import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.common.GenericDAO;
import com.triplived.entity.DeviceDb;
import com.triplived.entity.PersonDb;
import com.triplived.entity.TripMetaData;
import com.triplived.entity.UserAdditionalInfoDb;
import com.triplived.entity.UserFollowerDb;

/**
 * 
 * @author santoshjo
 *
 */
public interface UserDao extends GenericDAO<PersonDb, Serializable> {

	public PersonDb getPersonBySourceId(String id, UserFrom userFrom);

	List<PersonDb> getAllPersonWithMappedDevices();

	PersonDb getPersonByUserId(String id);

	String getUserByTripId(Long tripId);

	List<Object[]> getFollowersOfAPerson(Long personId);

	void followOrUnfollowPerson(UserFollowerDb obj);

	List<Object[]> getListOfPeopleFollowedByPerson(Long personId);

	UserFollowerDb getFollowerDetails(Long fromId, Long toId);

	Object[] getUserDetailsByDeviceId(String deviceId);

	String getUserDeviceByUserId(Long userId);
	
	
	/**
	 * Get a user by email Id
	 * 
	 * @param emailId
	 * @return
	 */
	PersonDb getUserByEmail(String emailId);

	void updateUserAdditionalInfo(UserAdditionalInfoDb obj);

	UserAdditionalInfoDb getUserAdditionalInfo(Long userId);

	/**
	 * Get user device information ( gcmid and app version) 
	 * 
	 * @param tripId
	 * @return
	 */
	DeviceDb getUserDeviceByTripId(Long tripId);

	/**
	 * Get user device information ( gcmid and app version) 
	 * 
	 * @param userId
	 */
	DeviceDb getDeviceInfoByUserId(Long userId);

	/**
	 * Get count of follow and followers
	 * @param userId
	 * @param meta
	 * @return
	 */
	Long getFollowFollowerCount(Long userId, TripMetaData meta);
	
	/**
	 * 
	 * get All users
	 */

	List<Long> getAllUsers(Long min, Long max);
}
