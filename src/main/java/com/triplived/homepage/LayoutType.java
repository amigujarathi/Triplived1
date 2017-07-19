package com.triplived.homepage;

/**
 * 
 * LAYOUT TYPE
 * 
 * @author santosh
 *
 */
public enum LayoutType {

	FULL("full_width"),
	FULL_CAROUSEL("full_width_carousel"),
	FULL_SCROLL("full_width_scroll"),
	HALF_SCROLL("half_width_scroll");
	
	private String type;
	
	public String getType() {
		return type;
	}
	
	LayoutType(String type){
		this.type = type;
	}
}
