package com.connectme.domain.triplived.dto;

import com.gcm.ClientMessageType;

/**
 * 
 * @author santosh
 *
 */
public class PhotoLikeNotificationDTO extends LikeNotificationDTO {

	private String photoId;
	private String url;
	private ClientMessageType clientMessageType;
	 
	public String getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	 
	public ClientMessageType getClientMessageType() {
		return clientMessageType;
	}
	
	public void setClientMessageType(ClientMessageType clientMessageType) {
		this.clientMessageType = clientMessageType;
	}
	  
}
