package com.triplived.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date>{

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) {

	try {
	    final SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
	    return format.parse(jp.getText());
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

}