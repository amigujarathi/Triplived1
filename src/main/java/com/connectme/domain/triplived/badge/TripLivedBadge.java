package com.connectme.domain.triplived.badge;

public enum TripLivedBadge {

	CELEBRITY("Celebrity"),
	MODERATOR("Moderator"),
	PATROL("Patrol"),
	POPULAR_TRIP("Popular Trip"),
    DISCUSSED_TRIP("Discussed Trip"),
    EDITOR("Editor"),
    INQUSITIVE("Inqusitive"),
    WANDERLUST("Wanderlust"),
    BAGPACKER("Bagpacker"),
    WORLD_WANDERER("World Wanderer"),
    NEWBIE("NewBie"),
    AUTOBIOGRAPHER("Autobiographer"),
    EPIC_TRAVELLER("Epic Traveller"),
    PROMOTPR("Promotor");
	
	private String badgeName;
	
	public String getBadgeName() {
		return badgeName;
	}
	
	TripLivedBadge(String badgeName){
		this.badgeName = badgeName;
	}
	
	
}
