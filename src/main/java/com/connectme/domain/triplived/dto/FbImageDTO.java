package com.connectme.domain.triplived.dto;

	import java.util.HashMap;
	import java.util.Map;
	import javax.annotation.Generated;
	import com.fasterxml.jackson.annotation.JsonAnyGetter;
	import com.fasterxml.jackson.annotation.JsonAnySetter;
	import com.fasterxml.jackson.annotation.JsonIgnore;
	import com.fasterxml.jackson.annotation.JsonInclude;
	import com.fasterxml.jackson.annotation.JsonProperty;
	import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Generated("org.jsonschema2pojo")
	@JsonPropertyOrder({
	"imagePath",
	"imageDesc"
	})
	public class FbImageDTO {

	@JsonProperty("imagePath")
	private String imagePath;
	@JsonProperty("imageDesc")
	private String imageDesc;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The imagePath
	*/
	@JsonProperty("imagePath")
	public String getImagePath() {
	return imagePath;
	}

	/**
	* 
	* @param imagePath
	* The imagePath
	*/
	@JsonProperty("imagePath")
	public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
	}

	/**
	* 
	* @return
	* The imageDesc
	*/
	@JsonProperty("imageDesc")
	public String getImageDesc() {
	return imageDesc;
	}

	/**
	* 
	* @param imageDesc
	* The imageDesc
	*/
	@JsonProperty("imageDesc")
	public void setImageDesc(String imageDesc) {
	this.imageDesc = imageDesc;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

	
}
