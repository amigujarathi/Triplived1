package com.connectme.domain.triplived.dto;

import java.util.List;

import com.connectme.domain.triplived.badge.Badge;

/**
 * The Badge the user has earned
 * 
 * @author santosh
 *
 */
public class BadgeEarnedNotification extends NotificationDTO {

	private List<Badge> earnedBadge;
	
	public List<Badge> getEarnedBadge() {
		return earnedBadge;
	}

	public void setEarnedBadge(List<Badge> earnedBadge) {
		this.earnedBadge = earnedBadge;
	}
}
