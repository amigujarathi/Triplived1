package com.triplived.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author santosh
 *
 */
public class Card {

	@Expose(serialize = false, deserialize = false)
	private String title;
	private String image;
	private List<Info> info = new ArrayList<>();
	
	private String id;
	private String type;
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Info> getInfo() {
		return info;
	}
	public void setInfo(List<Info> info) {
		this.info = info;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	public void addInfo(Info information) {
		info.add(information);
	}
	@Override
	public String toString() {
		return "Card [title=" + title + ", image=" + image + ", info=" + info + ", id=" + id + ", type=" + type
				+ ", additionalProperties=" + additionalProperties + "]";
	}
	
	
	
}
