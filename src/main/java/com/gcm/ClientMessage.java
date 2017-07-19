package com.gcm;

import java.util.Map;

public class ClientMessage {

	private ClientMessageType type;// what to do woth message // open timeline //open attration page
	
	private String title;
	private String body;
	private String imageUrl;
	private String pageUrl;
	
	private Map<String,String> notificationActionMap;
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public ClientMessageType getType() {
		return type;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setType(ClientMessageType type) {
		this.type = type;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Map<String, String> getNotificationActionMap() {
		return notificationActionMap;
	}
	public void setNotificationActionMap(Map<String, String> notificationActionMap) {
		this.notificationActionMap = notificationActionMap;
	}
	
	
	
	
}
