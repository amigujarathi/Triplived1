package com.connectme.fwk.config.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.connectme.domain.triplived.event.command.Command;
import com.connectme.domain.triplived.event.command.badge.BadgeEarnedNotificationCommand;
import com.connectme.domain.triplived.event.command.metaupdate.TripMetaUpdateDBCommand;
import com.connectme.domain.triplived.event.command.tripcomment.ComputeCommentsBadge;
import com.connectme.domain.triplived.event.command.tripcomment.ComputeTripBadges;
import com.connectme.domain.triplived.event.command.triplike.ComputeLikesBadge;
import com.connectme.domain.triplived.event.command.user.ComputeUserBadge;
import com.connectme.domain.triplived.event.command.user.UserMetaUpdateDBCommand;
import com.connectme.domain.triplived.event.listener.Handler;
import com.connectme.domain.triplived.event.listener.log.EventLogHandler;
import com.connectme.domain.triplived.event.listener.trip.EventHandler;

/**
 * 
 * @author santosh
 *
 */
@Configuration
public class HandlerConfig {

	
	@Bean(name={"userProfileHandler"})
	public Handler userProfileHandler() {
		
		Handler handler = new Handler();
		handler.addToHandler(profileHandler());
		handler.addToHandler(eventLogHandler());
		
		return handler;
	}
	
	@Bean(name={"tripSubmitHandler"})
	public Handler tripSubmitHandler() {
		
		Handler handler = new Handler();
		handler.addToHandler(submitHandler());
		handler.addToHandler(eventLogHandler());
		
		return handler;
	}
	
	@Bean(name={"commentHandler"})
	public Handler commentHandler() {
		
		Handler handler = new Handler();
		handler.addToHandler(tripCommentHandler());
		handler.addToHandler(eventLogHandler());
		
		return handler;
	}
	
	@Bean(name={"likeHandler"})
	public Handler likeHandler() {
		
		Handler handler = new Handler();
		handler.addToHandler(tripLikeHandler());
		handler.addToHandler(eventLogHandler());
		
		return handler;
	}
	
	@Bean
	public EventLogHandler eventLogHandler(){
		
		EventLogHandler eventLogHandler = new EventLogHandler();
		return eventLogHandler;
	}
	
	@Bean
	public EventHandler profileHandler() {
		
		EventHandler userProfileHandler = new EventHandler();

		userProfileHandler.addCommand(userMetaUpdateDBCommand());
		userProfileHandler.addCommand(computeUserBadgesEarned());
		userProfileHandler.addCommand(badgeEarnedNotificationCommand());
		
		return userProfileHandler;
	}
	
	/**
	 * Like Handler
	 * 	
	 * @return
	 */
	@Bean
	public EventHandler tripLikeHandler(){
		
		EventHandler tripCommentHandler = new EventHandler();

		tripCommentHandler.addCommand(tripMetaUpdateDBCommand());
		tripCommentHandler.addCommand(computeLikeBadgeEarned());
		tripCommentHandler.addCommand(badgeEarnedNotificationCommand());
		
		return tripCommentHandler;
	}

	
	@Bean
	public EventHandler submitHandler(){
		
		EventHandler tripSubmitHandler = new EventHandler();

		tripSubmitHandler.addCommand(tripMetaUpdateDBCommand());
		tripSubmitHandler.addCommand(computeTripBadgeEarned());
		tripSubmitHandler.addCommand(badgeEarnedNotificationCommand());
		
		return tripSubmitHandler;
	}
	
	/**
	 * Comment Handler
	 * 
	 * @return
	 */
	@Bean
	public EventHandler tripCommentHandler(){
		
		EventHandler tripCommentHandler = new EventHandler();

		tripCommentHandler.addCommand(tripMetaUpdateDBCommand());
		tripCommentHandler.addCommand(computeCommentsBadgeEarned());
		tripCommentHandler.addCommand(badgeEarnedNotificationCommand());
		
		return tripCommentHandler;
	}
	
	@Bean
	public Command computeUserBadgesEarned() {
		ComputeUserBadge computeUserBadge = new ComputeUserBadge();
		return computeUserBadge;
	}

	
	@Bean
	public Command tripMetaUpdateDBCommand() {
		
		TripMetaUpdateDBCommand tripMetaUpdateDBCommand = new TripMetaUpdateDBCommand();
		return tripMetaUpdateDBCommand;
	}
	
	@Bean
	public Command userMetaUpdateDBCommand() {
		
		UserMetaUpdateDBCommand userMetaUpdateDBCommand = new UserMetaUpdateDBCommand();
		return userMetaUpdateDBCommand;
	}
	
	@Bean
	public Command computeTripBadgeEarned(){
		
		ComputeTripBadges computeTripBadge = new ComputeTripBadges();
		return computeTripBadge;
	} 
	
	@Bean
	public Command computeCommentsBadgeEarned(){
		
		ComputeCommentsBadge computeBadgeEarned = new ComputeCommentsBadge();
		return computeBadgeEarned;
	} 
	
	@Bean
	public Command computeLikeBadgeEarned(){
		
		ComputeLikesBadge computeBadgeEarned = new ComputeLikesBadge();
		return computeBadgeEarned;
	} 
	
	
	@Bean
	public Command badgeEarnedNotificationCommand() {
		
		BadgeEarnedNotificationCommand badgeEarnedNotificationCommand = new BadgeEarnedNotificationCommand();
		return badgeEarnedNotificationCommand;
	}
}

