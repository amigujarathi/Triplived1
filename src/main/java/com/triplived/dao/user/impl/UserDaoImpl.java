package com.triplived.dao.user.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.user.UserDao;
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
@Repository
@Transactional("txManager")
public class UserDaoImpl extends GenericHibernateDAO<PersonDb, Serializable> implements UserDao {

	@Override
	@Transactional(readOnly=true)
	public PersonDb getPersonBySourceId(String id, UserFrom userFrom) {
		PersonDb personDb = (PersonDb) getSession().createQuery("SELECT user FROM  com.triplived.entity.PersonDb user where id = :id and  userFrom = :userFrom")
				.setLong("id", Long.parseLong(id))
				.setString("userFrom", userFrom.getUserFrom()).
				uniqueResult();
		
		return personDb;
 
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public PersonDb getPersonByUserId(String id) {
		PersonDb personDb = (PersonDb) getSession().createQuery("SELECT user FROM  com.triplived.entity.PersonDb user where USER_ID = :id").setLong("id", Long.parseLong(id)).
				uniqueResult();
		
		return personDb;
	}
	
	
	/*@Override
	@Transactional(readOnly=true)
	public PersonDb getFbTokenByUserAndDeviceId(String userId, String deviceId) {
		PersonDb personDb = (PersonDb) getSession().createQuery("SELECT user FROM  com.triplived.entity.PersonDb user where USER_ID = :id").setLong("id", Long.parseLong(id)).
				uniqueResult();
		
		return personDb;
	}*/
	
	@Override
	@Transactional(readOnly=true)
	public List<PersonDb> getAllPersonWithMappedDevices() {
		List<PersonDb> personDb = (List<PersonDb>) getSession().createQuery("SELECT user FROM  com.triplived.entity.PersonDb user where deviceId is not null ");
		return personDb;
 
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getUserByTripId(Long tripId) {
		Query query = getSession().createSQLQuery("Select REGISTRATION_ID as regId from device_info where DEVICE_ID = "
				+ "(Select DEVICE_ID from user_dynamic where USER_ID = (Select USER_ID from user_trip where TRIP_ID = :tripId) ORDER BY UPDATED_DATE DESC limit 1) "
				+ "ORDER BY CREATED_DATE DESC limit 1;").setLong("tripId", tripId);
		Object obj = query.uniqueResult();
		if(null != obj) {
			return obj.toString();
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public DeviceDb getUserDeviceByTripId(Long tripId) {
		Query query = getSession().createSQLQuery("Select REGISTRATION_ID, APPLICATION_VERSION, APPLICATION_VERSION_STRING   from device_info where DEVICE_ID = "
				+ "(Select DEVICE_ID from user_dynamic where USER_ID = (Select USER_ID from user_trip where TRIP_ID = :tripId) ORDER BY UPDATED_DATE DESC limit 1) "
				+ "ORDER BY CREATED_DATE DESC limit 1;").setLong("tripId", tripId);
		
		Object[] obj = (Object[]) query.uniqueResult();
		if(null != obj) {
			
			DeviceDb deviceDb = new DeviceDb();
			deviceDb.setRegistrationId(obj[0].toString());
			deviceDb.setApplicationVersion(obj[1].toString());
			deviceDb.setApplicationVersionString(obj[2].toString());
			
			return deviceDb;
			
		}
		return null;
	}
	
	
	
	@Override
	@Transactional(readOnly=true)
	public String getUserDeviceByUserId(Long userId) {
		Query query = getSession().createSQLQuery("Select REGISTRATION_ID as regId from device_info where DEVICE_ID = "
				+ " (Select DEVICE_ID from user_dynamic where USER_ID = :userId ORDER BY UPDATED_DATE DESC limit 1) "
				+ " ORDER BY CREATED_DATE DESC limit 1;").setLong("userId", userId);
		Object obj = query.uniqueResult();
		if(null != obj) {
			return obj.toString();
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public DeviceDb getDeviceInfoByUserId(Long userId) {
		Query query = getSession().createSQLQuery("Select REGISTRATION_ID, APPLICATION_VERSION, APPLICATION_VERSION_STRING   "
				+ " from device_info where DEVICE_ID =  "
				+ " (Select DEVICE_ID from user_dynamic where USER_ID = :userId ORDER BY UPDATED_DATE DESC limit 1 ) "
				+ " ORDER BY CREATED_DATE DESC limit 1").setLong("userId", userId);
		
				
		Object[] obj = (Object[]) query.uniqueResult();
		if(null != obj) {
			
			DeviceDb deviceDb = new DeviceDb();
			deviceDb.setRegistrationId(obj[0].toString());
			deviceDb.setApplicationVersion(obj[1].toString());
			deviceDb.setApplicationVersionString(obj[2].toString());
			
			return deviceDb;
			
		}
		
		return null;
	}
	
	@Override
	public void followOrUnfollowPerson(UserFollowerDb obj) {
		getSession().saveOrUpdate(obj);
	}
	
	/*@Override
	public List<UserFollowerDb> getFollowersOfAPerson(Long personId) {
		List<UserFollowerDb> list = (List<UserFollowerDb>) getSession().createQuery("Select followers from com.triplived.entity.UserFollowerDb followers where"
				+ " FOLLOW_USER_ID = :personId").setLong("personId", personId).list();
		
		return list;
	}*/
	
	@Override
	public List<Object[]> getFollowersOfAPerson(Long personId) {
		String query = "Select u.NAME as name, u.ABOUT_ME as aboutMe, u.ID as fbId, uf.FOLLOWING_USER_ID as followersId from user u, user_followers uf where"
				+ " u.USER_ID = uf.FOLLOWING_USER_ID and uf.STATUS = 'A' and uf.FOLLOW_USER_ID = " + personId;
        List<Object[]> list = getSession().createSQLQuery(query).list();
		return list;
	}
	
	@Override
	public List<Object[]> getListOfPeopleFollowedByPerson(Long personId) {
		String query = "Select u.NAME as name, u.ABOUT_ME as aboutMe, u.ID as fbId, uf.FOLLOW_USER_ID as followersId from user u, user_followers uf where"
				+ " u.USER_ID = uf.FOLLOW_USER_ID and uf.STATUS = 'A' and uf.FOLLOWING_USER_ID = " + personId;
		List<Object[]> list = getSession().createSQLQuery(query).list();
		return list;
	}
	
	@Override
	public UserFollowerDb getFollowerDetails(Long fromId, Long toId) {
		UserFollowerDb followerObj = (UserFollowerDb) getSession().createQuery("Select followers from com.triplived.entity.UserFollowerDb followers where"
				+ " FOLLOWING_USER_ID = :fromId and FOLLOW_USER_ID = :toId").setLong("fromId", fromId).setLong("toId", toId).uniqueResult();
		
		return followerObj;
	}
	
	@Override
	public Object[] getUserDetailsByDeviceId(String deviceId) {
		Query query = getSession().createSQLQuery("Select u.name as name, u.LAST_NAME as lastName, u.USER_ID as userId, ut.DEVICE_ID as deviceId from user u,"
						+ " user_dynamic ut where u.USER_ID = ut.USER_ID and ut.DEVICE_ID = :deviceId ORDER BY ut.UPDATED_DATE DESC limit 1").setString("deviceId", deviceId);
		Object[] obj = (Object[]) query.uniqueResult();
		if(null != obj) {
			return obj;
		}
		return null;
	}


	@Override
	public PersonDb getUserByEmail(String emailId) {
		
		PersonDb personDb = (PersonDb) getSession().createQuery("SELECT user FROM  com.triplived.entity.PersonDb user where email = :emailId ")
				.setString("emailId", emailId).
				uniqueResult();
		
		return personDb;
	}
	
	
	@Override
	public void updateUserAdditionalInfo(UserAdditionalInfoDb obj) {
		getSession().saveOrUpdate(obj);
	}
	
	@Override
	@Transactional(readOnly=true)
	public UserAdditionalInfoDb getUserAdditionalInfo(Long userId) {
		UserAdditionalInfoDb obj = (UserAdditionalInfoDb)this.getCurrentSession().get(UserAdditionalInfoDb.class, userId);
		return obj;
 
	}


	@Override
	@Transactional(value="txManager" , readOnly = true )
	public Long getFollowFollowerCount(Long userId, TripMetaData meta){
		
		String query = " Select count(*) "
				+ " FROM user_followers "
				+ " WHERE STATUS = 'A' " ;
				if(meta == TripMetaData.FOLLOWERS){
					
					query +=" and FOLLOW_USER_ID = :userId ";
				}else{
					query +=" and FOLLOWING_USER_ID = :userId ";
				}
							
		BigInteger likes = (BigInteger) this.getCurrentSession().createSQLQuery(query).setLong("userId", userId).uniqueResult();
		if(likes != null){
			return likes.longValue();
		}
		return 0l;
	}
	
	@Override
	@Transactional(value="txManager" , readOnly = true )
	public List<Long> getAllUsers(Long min, Long max) {
		String query = "Select USER_ID from user u where STATUS = 'A'  and USER_ID  between " +min+" and "+ max;
		List<BigInteger> list = getSession().createSQLQuery(query).list();
        
		List<Long> userList = new ArrayList<Long> ();
		
		if (CollectionUtils.isNotEmpty(list)) {
			for (BigInteger oArr : list) {
				userList.add((oArr).longValue());
			}
		}
		return userList;
	}

}
