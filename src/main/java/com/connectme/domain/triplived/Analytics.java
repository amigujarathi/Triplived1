package com.connectme.domain.triplived;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.triplived.controller.log.LogController;
import com.triplived.controller.profile.GenericProfile;
import com.triplived.service.AppUtils;

public class Analytics {
	// User and device specific details
	private Device device;
	private String deviceId;
	private boolean loggedIn;
	private String userId;
	private boolean grantedPost;
	private String session;
	private String date;
	private Long timestamp;

	// Application usage specific details
	private AppPage applicationPage;
	private String fragment;
	private String action;
	private String actionDetails;
	private String secondaryAction;
	private String secondaryActionDetails;

	private String applicationVersion;
	private String releaseType;

	// New variables
	private String userName;
	private String tripId;
	private String tripName;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isGrantedPost() {
		return grantedPost;
	}

	public void setGrantedPost(boolean grantedPost) {
		this.grantedPost = grantedPost;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public AppPage getApplicationPage() {
		return applicationPage;
	}

	public void setApplicationPage(AppPage applicationPage) {
		this.applicationPage = applicationPage;
	}

	public String getFragment() {
		return fragment;
	}

	public void setFragment(String fragment) {
		this.fragment = fragment;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionDetails() {
		return actionDetails;
	}

	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}

	public String getSecondaryAction() {
		return secondaryAction;
	}

	public void setSecondaryAction(String secondaryAction) {
		this.secondaryAction = secondaryAction;
	}

	public String getSecondaryActionDetails() {
		return secondaryActionDetails;
	}

	public void setSecondaryActionDetails(String secondaryActionDetails) {
		this.secondaryActionDetails = secondaryActionDetails;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public String getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	

	@Override
	public String toString() {
		return "Analytics [device=" + device + ", deviceId=" + deviceId
				+ ", loggedIn=" + loggedIn + ", userId=" + userId
				+ ", grantedPost=" + grantedPost + ", session=" + session
				+ ", date=" + date + ", timestamp=" + timestamp
				+ ", applicationPage=" + applicationPage + ", fragment="
				+ fragment + ", action=" + action + ", actionDetails="
				+ actionDetails + ", secondaryAction=" + secondaryAction
				+ ", secondaryActionDetails=" + secondaryActionDetails
				+ ", applicationVersion=" + applicationVersion
				+ ", releaseType=" + releaseType + ", userName=" + userName
				+ ", tripId=" + tripId + ", tripName=" + tripName + "]";
	}

	private  void CreateLogInternal(String tripId, AppPage page, String fragment, String action, String actionDetails,
			String secondaryAction, String secondaryActionDetails) {
		Analytics analytics = this;
		Date now = new Date();

		analytics.tripId = tripId;
		analytics.applicationPage = page;
		analytics.fragment = fragment;
		analytics.action = action;
		analytics.actionDetails = actionDetails;
		analytics.secondaryAction = secondaryAction;
		analytics.secondaryActionDetails = secondaryActionDetails;

		//analytics.device = new Device();
		/*analytics.deviceId = "";
		analytics.loggedIn = false ; //Availabe ine header//SharedPreferencesUtils.getInstance().getBoolean(CosConstants.IS_USER_LOGGED_IN);
		if (analytics.loggedIn) {
			GenericProfile currentPerson = GsonUtils.getInstance().deserializeJSON(
					SharedPreferencesUtils.getInstance().getString(CosConstants.USER_AS_JSON), GenericProfile.class);
			analytics.userId = currentPerson.getId();
			analytics.grantedPost = SharedPreferencesUtils.getInstance()
					.getBoolean(SharedPreferencesUtils.KEY_FB_USER_PERMISSIONS_HAS_POST);
		}

		analytics.session = */
		analytics.date = now.toString();
		analytics.timestamp = now.getTime();
		//analytics.applicationVersion = 
		//analytics.releaseType = */
 
		LogController.log(analytics);
	}


	public  void CreateLog(final String tripId, final AppPage page, final String fragment, final String action,
			final String actionDetails, final String secondaryAction, final String secondaryActionDetails) {
		
		CreateLogInternal(tripId, page, fragment, action, actionDetails, secondaryAction, secondaryActionDetails);
	}

	public  void CreateLog(final String tripId, AppPage page, String fragment, String action, String actionDetails) {
		CreateLog(tripId, page, fragment, action, actionDetails, null, null);
	}

	public  void CreateLog(final String tripId, AppPage page, String fragment, String action, String actionDetails,
			String secondaryActionDetails) {
		CreateLog(tripId, page, fragment, action, actionDetails, secondaryActionDetails, null);
	}
	
	public void setHeaderParameters(HttpServletRequest request){
		this.deviceId =  request.getHeader("UserDeviceId");
		this.loggedIn =  Boolean.parseBoolean(request.getHeader("loggedIn"));
		this.userId =  request.getHeader("userId");
		this.grantedPost =  Boolean.parseBoolean(request.getHeader("grantedPost"));
		
		this.session =  request.getSession().getId();
		this.applicationVersion =  request.getHeader("ApplicationVersion");
		this.releaseType =  request.getHeader("releaseType");
		
	}

}
