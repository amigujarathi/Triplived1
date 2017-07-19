package com.triplived.util;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;

public class TripLivedUtil {

	 static final double _eQuatorialEarthRadius = 6378.1370D;
	 static final double _d2r = (Math.PI / 180D);
	 static final DecimalFormat df = new DecimalFormat("###");
	 static final DecimalFormat dfOne = new DecimalFormat("###.#");
	    
	public static double HaversineInMetres(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;

        Double dist =  (1000D * d);
        return dist;
    }
	
	public static double trimValue(double value) {
		return Double.parseDouble(df.format(value));
	}
	
	public static double trimValueToOneDecimal(double value) {
		return Double.parseDouble(dfOne.format(value));
	}
	
	 public static int CountDays(Date prev, Date next) {
	        if ( prev == null || next == null ) {
	            return -1;
	        }

	        TimeZone tz = TimeZone.getDefault();
	        int offset = tz.getRawOffset();

	        long day1 = (prev.getTime() + offset) / (1000 * 60 * 60 * 24 );
	        long day2 = (next.getTime() + offset) / (1000 * 60 * 60 * 24 );
	        int dayDiff = (int)(day2 - day1) + 1;
	        return dayDiff;
	 }
	 
	 public static String generatePublicTripId(Long tripId) throws Exception{
			
			String ipAddressFactor = InetAddress.getLocalHost().getHostAddress().split("\\.")[3];
			Long ts = System.currentTimeMillis();
			int beginLength = ts.toString().length() - 5;
			int endLength = ts.toString().length() - 1;
			String currentTimeFactor = ts.toString().substring(beginLength, endLength);
			
			//String char1 = RandomStringUtils.randomAlphabetic(1);
			//String char2 = RandomStringUtils.randomAlphabetic(1);
			Integer lengthOfIpAddress = ipAddressFactor.length();
			
			String randomString = currentTimeFactor + lengthOfIpAddress + ipAddressFactor + tripId.toString(); 
			
			return randomString;
	}
	
	 public static Long generateId(Long id) throws Exception{
		 return Long.parseLong(generatePublicTripId(id));
	 }
	 
	public static Boolean isPublicIdEnabled(HttpServletRequest request) { 
	 Integer appVersion = Integer.parseInt(request.getHeader("ApplicationVersion"));
	 if(null == appVersion) {
	    	return false;
	 }
	 if(null != appVersion && (appVersion < Constants.APP_VERSION)) {
	    	return false;
	 }
	 return true;
	 	
	}
	
	/**
	 * Check if the request is above the minimum version for storing trip status in Db
	 * @param request
	 * @return
	 */
	public static Boolean isTripStatusEnabled(HttpServletRequest request) { 
		 Integer appVersion = Integer.parseInt(request.getHeader("ApplicationVersion"));
		 if(null == appVersion) {
		    	return false;
		 }
		 if(null != appVersion && (appVersion < Constants.MIN_SUPPORTED_APP_VERSION_FOR_RECORDING_TRIP_STATUS)) {
		    	return false;
		 }
		 return true;
		 	
	}
	
	/**
	 * Check if the request is above the minimum version for storing trip status in Db
	 * @param request
	 * @return
	 */
	public static Boolean isAnalyticsLogsModificationEnabled(HttpServletRequest request) { 
		 Integer appVersion = Integer.parseInt(request.getHeader("ApplicationVersion"));
		 if(null == appVersion) {
		    	return false;
		 }
		 if(null != appVersion && (appVersion < Constants.MIN_SUPPORTED_APP_VERSION_FOR_MODIFYING_ANALYTICS_LOGS)) {
		    	return false;
		 }
		 return true;
	}
	
	
		
}
