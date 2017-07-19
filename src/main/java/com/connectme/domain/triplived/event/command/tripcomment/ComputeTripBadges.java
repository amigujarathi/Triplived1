package com.connectme.domain.triplived.event.command.tripcomment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.connectme.domain.triplived.badge.Badge;
import com.connectme.domain.triplived.badge.BadgeType.UserBadgeType;
import com.connectme.domain.triplived.badge.TripLivedBadge;
import com.connectme.domain.triplived.event.TlEvent;
import com.connectme.domain.triplived.event.command.AbstractCommand;
import com.triplived.dao.user.UserMetaDAO;
import com.triplived.entity.TripMetaData;
import com.triplived.service.badge.BadgeService;

/**
 * Badges to compute
 * 
 * Wanderlust_CRETERION=15
 * Epic\ Traveller_CRETERION=5
 * 
 * @author santosh
 *
 */
public class ComputeTripBadges extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(ComputeTripBadges.class );
	
	@Autowired
	private UserMetaDAO userMetaDao;
	
	@Autowired 
	private BadgeService badgeService;
	
	@Autowired 
	private Environment environment;
	
	
	@Override
	public void execute(TlEvent event) {
		init(this, event);
		
		Long userId = event.getUser();
		
		//Get Data from database and compute whether Badge needs to be allocated.
		int userCitiesVisited  = userMetaDao.getCountFor(userId, TripMetaData.CITIES);
		int userTripSubmitedCount  = userMetaDao.getCountFor(userId, TripMetaData.TRIP_SUBMITTED);
		
		int creterionWanderLustTrip = environment.getProperty(TripLivedBadge.WANDERLUST.getBadgeName()+"_CRETERION", Integer.class , 0);
		int creterionTripsSubmitted = environment.getProperty(TripLivedBadge.EPIC_TRAVELLER.getBadgeName()+"_CRETERION", Integer.class , 0);

		//get All badges acquired by user
		Set<String> userBadges = getUserbadges(userId);
		Set<Badge> badges = new HashSet<Badge>();
		
		//WANDERLUST BADGE
		if(userCitiesVisited >= creterionWanderLustTrip && !userBadges.contains(TripLivedBadge.WANDERLUST.getBadgeName())){
			//only once for any trip
			Badge badge = new Badge();
			badge.setName(TripLivedBadge.WANDERLUST.getBadgeName());
			badge.setBadgeType(UserBadgeType.BRONZE);
			badges.add(badge);
			logger.warn("Awarding badge to user {} badge is {} ", userId, badge.getName() );
			//badgeService.allocateBadge(badge, userId);
			//send notification
			//event.addToHeader(TlEvent.MOVE_FORWARD, true);
		}
		
		//WANDERLUST BADGE
		if(!userBadges.contains(TripLivedBadge.NEWBIE.getBadgeName())){
			//only once for any trip
			Badge badge = new Badge();
			badge.setName(TripLivedBadge.NEWBIE.getBadgeName());
			badge.setBadgeType(UserBadgeType.BRONZE);
			badges.add(badge);
			logger.warn("Awarding badge to user {} badge is {} ", userId, badge.getName() );
			//badgeService.allocateBadge(badge, userId);
			//send notification
			//event.addToHeader(TlEvent.MOVE_FORWARD, true);
		}
		
		//EPIC TRAVELLER CRETERION
		if(userTripSubmitedCount == creterionTripsSubmitted && !userBadges.contains(TripLivedBadge.EPIC_TRAVELLER.getBadgeName())) {
			
			Badge badge = new Badge();
			badge.setName(TripLivedBadge.EPIC_TRAVELLER.getBadgeName());
			badge.setBadgeType(UserBadgeType.SILVER);
			badges.add(badge);
			logger.warn("Awarding badge to user {} badge is {} ", userId, badge.getName() );
			//event.addToHeader(TlEvent.MOVE_FORWARD, true);
		}
		
		if(badges.size() > 0 ){
			badgeService.allocateBadge(badges, userId);
			event.setUser(userId);
			event.setBody(badges);
			event.addToHeader(TlEvent.MOVE_FORWARD, true);
		}
	}
	
	private Set<String> getUserbadges(Long userId) {
		 List<Badge> badges = badgeService.getUserBadges(userId);
		 Set<String> userBadges = new HashSet<>();
		 
		 for (Badge badge : badges) { 
			 userBadges.add(badge.getName()) ;
		}
		 
		return userBadges;
	}

	@Override
	public boolean executeCurrent(TlEvent event) {
		// TODO Auto-generated method stub
		if(event.getHeader().containsKey(TlEvent.MOVE_FORWARD)){
			boolean move = (Boolean) event.getHeader(TlEvent.MOVE_FORWARD);
			if(move){
				return true;
			}
		}
		
		return false;
	}
	 
}
