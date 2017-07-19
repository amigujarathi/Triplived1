package com.triplived.rest.radarSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.connectme.domain.triplived.placeDetail.AddressComponent;
import com.connectme.domain.triplived.radarSearch.Example;
import com.connectme.domain.triplived.radarSearch.Result;


//import com.connectme.domain.triplived.weatherJson.Example;

@Component
public class RadarSearchClient {
	
	@Value("${apiWeatherBaseUrl}")
	private String apiWeatherBaseUrl;
	
	@Value("${apiWeatherKey}")
	private String apiWeatherKey;
	
	
	    public String getRadarSearchInfo() {
		
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().clear();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	DateFormat df = new SimpleDateFormat("dd/MM/yy");
    	String dateStr = df.format(new Date());
    	Date dateObj = null;
    	try {
			dateObj = df.parse(dateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		
		
			String url = "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=28.489297746153845,77.08778511538463&radius=500&types=food|restaurant|cafe&key=AIzaSyDyjJQ68kuPg5-AcjjlscdquN_tygNFzbM";
			try {
			
			
			ResponseEntity<Example> response = rt.exchange (url, HttpMethod.GET, entity, Example.class);
			
			List<Result> results = response.getBody().getResults();
			BufferedWriter brw = new BufferedWriter(new FileWriter("D:\\ayan\\work\\tl\\GooglePlaces.txt"));
			for(Result r : results) {
				String placeUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+r.getPlaceId()+"&key=AIzaSyDyjJQ68kuPg5-AcjjlscdquN_tygNFzbM";
				ResponseEntity<com.connectme.domain.triplived.placeDetail.Example> response1 = rt.exchange (placeUrl, HttpMethod.GET, entity, com.connectme.domain.triplived.placeDetail.Example.class);
				
				StringBuffer sb = new StringBuffer();
				sb.append(response1.getBody().getResult().getName() + " - ");
				
				for(AddressComponent ac : response1.getBody().getResult().getAddressComponents()) {
					sb.append(" , " + ac.getLongName());
				}
				
				brw.append(sb.toString());
				brw.newLine();
			}
			brw.close();
			
			String x = "Ayan";
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		
		
    	return null;		
    	
    	
	}
		
	
}
