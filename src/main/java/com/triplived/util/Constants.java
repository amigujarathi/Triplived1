package com.triplived.util;

/**
 * 
 * @author Santosh Joshi
 *
 */
public final class Constants {

	private Constants() {
		throw new IllegalStateException("Cannot be instantiated");
	}
	
	public final static String LOGIN_USER = "logged_in_user" ;
	public final static String IMAGE_PATH = "image_path";
	
	public final static String ATTRACTION_DISTANCE = "200";
	public final static String ATTRACTION_DISTANCE_FOR_CITY = "1000";
	public final static String HOTEL_DISTANCE = "200";
	public final static String HOTEL_DISTANCE_FOR_CITY = "1000";
	public final static String RESTAURANT_DISTANCE = "50";
	public final static String RESTAURANT_DISTANCE_FOR_CITY = "200";
	public final static Integer APP_VERSION = 12;
	public final static Integer MIN_SUPPORTED_APP_VERSION = 10;
	public final static Integer MIN_SUPPORTED_APP_VERSION_FOR_EDITING_RIGHTS = 13;
	public final static Integer MIN_SUPPORTED_APP_VERSION_FOR_RECORDING_TRIP_STATUS = 19;
	public final static Integer MIN_SUPPORTED_APP_VERSION_FOR_MODIFYING_ANALYTICS_LOGS = 19;
	public final static Integer MIN_SUPPORTED_APP_VERSION_FOR_SENDING_WEBLINK = 20;
	
	public final static String TRIP_RECORDING_STATE = "RECORDING" ;
	public final static String TRIP_EDITING_STATE = "EDITING" ;
	public final static String TRIP_FINALIZED_STATE = "FINALIZED" ;
	public final static String TRIP_DELETED_STATE = "DELETED" ;

}
