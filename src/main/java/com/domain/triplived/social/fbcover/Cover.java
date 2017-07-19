package com.domain.triplived.social.fbcover;

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
@JsonPropertyOrder({ "id", "offset_y", "source" })
public class Cover {

	@JsonProperty("id")
	private String id;
	@JsonProperty("offset_y")
	private Integer offsetY;
	@JsonProperty("source")
	private String source;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The id
	 */
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The offsetY
	 */
	@JsonProperty("offset_y")
	public Integer getOffsetY() {
		return offsetY;
	}

	/**
	 * 
	 * @param offsetY
	 *            The offset_y
	 */
	@JsonProperty("offset_y")
	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * 
	 * @return The source
	 */
	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	/**
	 * 
	 * @param source
	 *            The source
	 */
	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
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
