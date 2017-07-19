package com.triplived.dao.messageDetails.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.messageDetails.MessageDetailsDao;
import com.triplived.entity.MessageDetailsDb;

@Repository
public class MessageDetailsDaoImpl extends GenericHibernateDAO<MessageDetailsDb, Serializable> implements MessageDetailsDao {
	
	@Override
	public MessageDetailsDb updateMessageDetails(MessageDetailsDb msgDetailDb) {
		MessageDetailsDb msg = saveOrUpdate(msgDetailDb);
		return msg;
	}

	@Override
	public MessageDetailsDb getMessageDetailsByIds(String registrationId, String messageId) {
		MessageDetailsDb msgDetail = (MessageDetailsDb) getSession().createQuery("Select msg FROM com.triplived.entity.MessageDetailsDb msg "
				+ "where MESSAGE_ID =:messageId AND REG_ID =:regId").setString("regId", registrationId).setString("messageId", messageId).uniqueResult();
		return msgDetail;
	}
	
	@Override
	public Boolean checkIfMessageExists(String type, String entityId, String regId, Date date) {
		/*List<MessageDetailsDb> msgDetail = (List<MessageDetailsDb>) getSession().createQuery("Select msg from com.triplived.entity.MessageDetailsDb msg "
				+ "where ENTITY_ID =:entityId and REG_ID =:regId and MESSAGE_TYPE =:type and time_to_sec(timediff(CREATED_DATE, UPDATED_DATE))/3600 < 24").setString("regId",regId)
				.setString("type", type).setString("entityId", entityId).setDate("date", date).uniqueResult();*/
		
		List<MessageDetailsDb> msgDetail = (List<MessageDetailsDb>) getSession().createQuery("Select msg from com.triplived.entity.MessageDetailsDb msg "
				+ "where ENTITY_ID =:entityId and REG_ID =:regId and MESSAGE_TYPE =:type ORDER BY UPDATED_DATE DESC").setString("regId",regId)
				.setString("type", type).setString("entityId", entityId).list();
		
		if(!CollectionUtils.isEmpty(msgDetail)) {
			MessageDetailsDb obj = msgDetail.get(0);
			Date d = obj.getUpdatedDate();
			
			long secs = (date.getTime() - d.getTime())/1000;
			if(secs/3600 < 24) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
}
