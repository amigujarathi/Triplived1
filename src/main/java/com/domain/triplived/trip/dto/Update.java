package com.domain.triplived.trip.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
"message",
"emotion",
"time",
"files"
})
public class Update {

@JsonProperty("message")
private String message;
@JsonProperty("emotion")
private Integer emotion;
@JsonProperty("time")
private Long time;
@JsonProperty("files")
private List<File> files = new ArrayList<File>();
@JsonProperty("additionalProperties")
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The message
*/
@JsonProperty("message")
public String getMessage() {
return message;
}

/**
* 
* @param message
* The message
*/
@JsonProperty("message")
public void setMessage(String message) {
this.message = message;
}

/**
* 
* @return
* The emotion
*/
@JsonProperty("emotion")
public Integer getEmotion() {
return emotion;
}

/**
* 
* @param emotion
* The emotion
*/
@JsonProperty("emotion")
public void setEmotion(Integer emotion) {
this.emotion = emotion;
}

/**
* 
* @return
* The time
*/
@JsonProperty("time")
public Long getTime() {
return time;
}

/**
* 
* @param time
* The time
*/
@JsonProperty("time")
public void setTime(Long time) {
this.time = time;
}

/**
* 
* @return
* The files
*/
@JsonProperty("files")
public List<File> getFiles() {
return files;
}

/**
* 
* @param files
* The files
*/
@JsonProperty("files")
public void setFiles(List<File> files) {
this.files = files;
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
