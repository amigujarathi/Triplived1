package com.connectme.domain.triplived.event.command.badge;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.AbstractCommand;
import com.triplived.service.notification.GCMNotificationService;

/**
 * 
 * @author santosh
 *
 */
public class BadgeEarnedNotificationCommand extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(BadgeEarnedNotificationCommand.class);
	
	@Autowired
	private GCMNotificationService gcmService;
	 
	@Override
	public void execute(TlEvent event) {

		@SuppressWarnings("unchecked")
		Set<Badge> badges = (Set<Badge>) event.getBody();
		if(CollectionUtils.isNotEmpty(badges)){
			logger.warn("Badges earned Event fired {}  and badges are {}",  event.getUser(), badges);
			
			try {
				gcmService.notifyOnBadgeEarned(event.getUser(), new ArrayList<>(badges));
			} catch (Exception e) {
				logger.error("Problem while sending GCM message ", e);
				logger.error("Error details are {}  and badges are {}",  event.getUser(), badges);
			}
		}
	}
	
	@Override
	public boolean executeCurrent(TlEvent event) {
		//TODO
		if(event.getHeader().containsKey(TlEvent.MOVE_FORWARD)){
			boolean move = (Boolean) event.getHeader(TlEvent.MOVE_FORWARD);
			if(move){
				return true;
			}
		}
		
		return false;
	}

}
