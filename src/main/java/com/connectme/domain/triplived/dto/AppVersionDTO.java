package com.connectme.domain.triplived.dto;

import java.util.HashSet;
import java.util.Set;

public class AppVersionDTO {
	
	private String currentVersion;
	private String minVersion;
	private String syncEnabled;
	private String isSearchByIdAllowed;
    private Set<String> itemsAdded = new HashSet<>();
     
	
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	public String getMinVersion() {
		return minVersion;
	}
	public void setMinVersion(String minVersion) {
		this.minVersion = minVersion;
	}
	public String getSyncEnabled() {
		return syncEnabled;
	}
	public void setSyncEnabled(String syncEnabled) {
		this.syncEnabled = syncEnabled;
	}
	public String getIsSearchByIdAllowed() {
		return isSearchByIdAllowed;
	}
	public void setIsSearchByIdAllowed(String isSearchByIdAllowed) {
		this.isSearchByIdAllowed = isSearchByIdAllowed;
	}
	public Set<String> getItemsAdded() {
		return itemsAdded;
	}
	public void setItemsAdded(Set<String> itemsAdded) {
		this.itemsAdded = itemsAdded;
	}
	
	
	

}
