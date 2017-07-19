package com.connectme.domain.triplived.dto;

import java.util.List;

import com.connectme.domain.triplived.badge.Badge;

public class ProfileDTO {
	
	private PersonDTO person;
	private List<PersonDTO> followers;
	private List<PersonDTO> following;
	private List<TripSearchDTO> trips;
	
	private List<Badge> badges;
	
	public PersonDTO getPerson() {
		return person;
	}
	public void setPerson(PersonDTO person) {
		this.person = person;
	}
	public List<PersonDTO> getFollowers() {
		return followers;
	}
	public void setFollowers(List<PersonDTO> followers) {
		this.followers = followers;
	}
	public List<PersonDTO> getFollowing() {
		return following;
	}
	public void setFollowing(List<PersonDTO> following) {
		this.following = following;
	}
	public List<TripSearchDTO> getTrips() {
		return trips;
	}
	public void setTrips(List<TripSearchDTO> trips) {
		this.trips = trips;
	}

	public List<Badge> getBadges() {
		return badges;
	}
	
	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}
}
