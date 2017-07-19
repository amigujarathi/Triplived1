package com.connectme.domain.triplived.event.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.AbstractCommand;
import com.triplived.dao.user.UserMetaDAO;
import com.triplived.entity.TripMetaData;

/**
 * 
 * @author santosh
 *
 */
public class UserMetaUpdateDBCommand extends AbstractCommand{

private static final Logger logger = LoggerFactory.getLogger(UserMetaUpdateDBCommand.class );
	
	@Autowired
	private UserMetaDAO userMetaDao;
	
	public void setUserMetaDao(UserMetaDAO userMetaDao) {
		this.userMetaDao = userMetaDao;
	}
	 
	@Override
	public void execute(TlEvent event) {
		init(this, event);

		TripMetaData headerType  = (TripMetaData) event.getHeader().get(TlEvent.TYPE);
		Long userId = event.getUser();
		
		if(headerType == TripMetaData.FOLLOWING || headerType == TripMetaData.UNFOLLOWED){
			
			Long otherUserId = Long.parseLong(event.getActionDetails());
			 
			int count = 1;
			
			if(headerType == TripMetaData.UNFOLLOWED ){
				count = -1;
			}
			userMetaDao.updateMetaDataCount(userId, TripMetaData.FOLLOWING, count);
			userMetaDao.updateMetaDataCount(otherUserId, TripMetaData.FOLLOWERS, count);
			
			//CURRENTLY NOT DOING ANYTHING
			event.addToHeader(TlEvent.MOVE_FORWARD, false);
			
			logger.warn("Update follow follower count for user {} to user {}", event.getActionDetails(), userId, otherUserId);
		}
		
		if(headerType == TripMetaData.PROFILE){
			userMetaDao.updateMetaDataCount(userId, TripMetaData.PROFILE);
			event.addToHeader(TlEvent.MOVE_FORWARD, true);
		}
	}
}
