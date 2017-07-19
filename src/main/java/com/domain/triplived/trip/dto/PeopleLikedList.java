package com.domain.triplived.trip.dto;

import java.util.List;

import com.triplived.controller.profile.UserFrom;

/**
 * 
 * Who all people liked a Trip / Photo / Video / Ettity
 * 
 * @author Santosh Joshi
 *
 */
public class PeopleLikedList {

	private List<PeopleLiked> list;

	public List<PeopleLiked> getList() {
		return list;
	}

	public void setList(List<PeopleLiked> list) {
		this.list = list;
	}
}
