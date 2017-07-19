package com.triplived.service.user;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.connectme.domain.triplived.UserResponse;
import com.connectme.domain.triplived.dto.NotificationBarParentDTO;
import com.connectme.domain.triplived.dto.PersonDTO;
import com.connectme.domain.triplived.dto.ProfileDTO;
import com.connectme.domain.triplived.dto.TripSearchDTO;
import com.triplived.controller.profile.UserFrom;
import com.triplived.entity.PersonDb;
import com.triplived.entity.UserFollowerDb;
import com.triplived.service.profile.PersonProfile;

public interface UserService {

	UserFollowerDb getFollowerDetails(Long fromId, Long toId);

    //void followOrUnfollowPerson(UserFollowerDb obj);

	void updateFollowerDetails(Long fromId, Long toId, Boolean status);

	ProfileDTO loadUserProfile(String personId);

	PersonDb loadUserDetails(String personId);

	/**
	 * Get User Image Url 
	 *  From Google: FaceBook: Our Own Platform
	 * 
	 * @param personId
	 * @return
	 */
	public String getUserImageUrl(String personId, UserFrom preferenceUserFrom,  Map<String, String> params );
	

	List<TripSearchDTO> convertTrips(List<Object[]> trips);

	String isTripEditableByUser(String userId, String tripId)
			throws SolrServerException;

	String getUserCoverPageUrl(String personId, String accountId);
	
	/**
	 * GEt user from device ID 
	 */
	public PersonProfile getUserFromDeviceId(String deviceId);

	PersonDTO getUserDetails(String personId);

	NotificationBarParentDTO notificationBar(String userId);

	void notificationBarUpdate(String userId);

	UserResponse getUserDetailsFromSolr(String userId)
			throws SolrServerException;

	UserResponse getUserDetailsByAccountIdFromSolr(String accountId)
			throws SolrServerException;

	UserResponse getUserDetailsForAnalytics(String id)
			throws SolrServerException;

	void updateUserProfile(Long id, PersonProfile user);

	ProfileDTO loadUserProfileForWeb(String personId);

}
