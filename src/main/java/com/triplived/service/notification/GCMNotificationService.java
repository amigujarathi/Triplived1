package com.triplived.service.notification;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.dto.NotificationDTO;
import com.gcm.ClientMessageType;

public interface GCMNotificationService {


	Boolean sendAttractionSpecificNotifications(String deviceId,
			NotificationDTO notification) throws IOException, Exception;


	Boolean sendCitySpecificNotifications(String deviceId, NotificationDTO notification)
			throws IOException, Exception;


	NotificationDTO getPunchlineForGCMNotificationsForCity(String deviceId, String cityId)
			throws IOException, Exception;


	NotificationDTO getPunchlineForGCMNotificationsForAttractions(String deviceId,
			String attractionId) throws IOException, Exception;


	void sendTriviaNotifications(String deviceId, String lat, String lng,
			String accuracy) throws IOException, Exception;


	Boolean  sendRestaurantSpecificNotifications(String deviceId,
			NotificationDTO notification) throws IOException, Exception;


	Boolean sendHotelSpecificNotifications(String deviceId, NotificationDTO notification)
			throws IOException, Exception;


	Boolean sendUnknowEntityNotifications(String deviceId,
			NotificationDTO notification) throws IOException, Exception;


	void updateMessageStatus(String deviceId, String messageId, String status);


	Boolean notifyVideoGeneration(String tripId, String ytId)
			throws IOException;


	void sendTripLikeNotification(String tripId, String fromUserId)
			throws IOException;

/*
	void sendTripCommentNotification(String tripId, String fromUserId)
			throws IOException;
*/

	void sendTripShareNotification(String tripId, String fromUserId)
			throws IOException;


	void sendTrendingTripNotifications(String tripId, String message)
			throws IOException, Exception;


	void sendTripCommentNotification(String tripId, String fromUserId,
			Set<Long> usersToSendNotification) throws IOException;


	Boolean sendUserFollowNotification(String fromUserId, String toUserId) throws IOException;

	/**
	 * 
	 * Sends Notification on Trip MEdia Like
	 * 
	 * @param tripId
	 * @param fromUserId
	 * @param mediaId
	 * @param path
	 * @throws IOException
	 */
	 
	void sendTripMediaLikeNotification(Long tripId, Long fromUserId, String mediaId, String path, ClientMessageType type) throws IOException;


	/**
	 * Sends Notification on Trip Comment addition
	 * @param tripId
	 * @param userId
	 * @param mediaId
	 */
	void sendTripMediaCommentNotification(Long tripId, Long userId, String mediaId);

	/**
	 * Badge earned
	 * 
	 * @param tripId
	 * @param tId
	 * @return
	 * @throws IOException
	 */
	Boolean notifyOnBadgeEarned(Long userId, List<Badge> badges) throws IOException;


	Boolean sendTripReminderNotification(String tripId) throws IOException;


	Boolean sendWebLinkAsNotification(String tripId, String webUrl,
			String imageUrl, String title, String message) throws IOException;


}
