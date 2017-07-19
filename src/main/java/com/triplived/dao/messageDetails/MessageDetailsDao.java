package com.triplived.dao.messageDetails;

import java.util.Date;

import com.triplived.entity.MessageDetailsDb;

public interface MessageDetailsDao {

	MessageDetailsDb updateMessageDetails(MessageDetailsDb msgDetailDb);

	MessageDetailsDb getMessageDetailsByIds(String registrationId,
			String messageId);

	Boolean checkIfMessageExists(String type, String entityId, String regId,
			Date date);

}
