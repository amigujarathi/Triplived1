package com.triplived.rest.weatherClient;

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

import com.connectme.domain.triplived.openWeather.Example;
import com.triplived.dao.attraction.CityDao;
import com.triplived.dao.attraction.CityWeatherDao;
import com.triplived.entity.CityDb;
import com.triplived.entity.CityWeatherDb;

//import com.connectme.domain.triplived.weatherJson.Example;

@Component
public class WeatherClient {
	
	@Value("${apiWeatherBaseUrl}")
	private String apiWeatherBaseUrl;
	
	@Value("${apiWeatherKey}")
	private String apiWeatherKey;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private CityWeatherDao cityWeatherDao;
	
		
	    public String getWeatherInfo() {
		
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
    	
		List<CityDb> cities = cityDao.getCitiesWithCoordinates();
		List<CityWeatherDb> weatherCityList = cityWeatherDao.getAllCities();
		
		Map<String, CityWeatherDb> cityWeatherMap = new HashMap<String, CityWeatherDb>();
		
		for(CityWeatherDb obj : weatherCityList) {
			cityWeatherMap.put(obj.getCityCode(), obj);
		}
		
		int index = 0;
		List<CityWeatherDb> periodicList = new ArrayList<CityWeatherDb>();
		
		for(CityDb city : cities) {
			index++;
			BigDecimal lat = city.getLatitude();
			BigDecimal longitude = city.getLongitude();
			String url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+longitude+"&units=metric";
			try {
			
			CityWeatherDb obj = cityWeatherMap.get(city.getCityCode());
			
			if(!(df.format(obj.getUpdateDate()).equals(df.format(dateObj)))) {
			ResponseEntity<Example> response = rt.exchange (url, HttpMethod.GET, entity, Example.class);
				
			if(response != null) {
				if(null != response.getBody().getMain()) {
					if(null != response.getBody().getMain().getTemp()) {
							obj.setTempCurrent(BigDecimal.valueOf(response.getBody().getMain().getTemp()));
							obj.setUpdateDate(dateObj);
							
							periodicList.add(obj);
							if(index % 10 == 0) {
								updateWeatherPeriodically(periodicList);
								periodicList.clear();
							}
							
						}
						
					}
				}
			  }
			}catch(Exception e) {
				System.out.println("Exception for city at index - " + city.getCityname() + "," + index);
				e.printStackTrace();
			}
		}
		if(periodicList.size() > 0) {
			updateWeatherPeriodically(periodicList);
		}
    	//String url = apiWeatherBaseUrl + "key=" + apiWeatherKey + "&q=18.975,72.8258&format=json&tp=24";
		//String url = "http://api.worldweatheronline.com/free/v2/weather.ashx?key=938c738ea94e27496f4392c26f8ac&q=18.975,72.8258&format=json&tp=24";
		//String url = "http://api.openweathermap.org/data/2.5/weather?lat=28.6139&lon=77.2090&units=metric";
    	
    	
    	//ResponseEntity<Example> response = rt.exchange (url, HttpMethod.GET, entity, Example.class);
    	
    	
    	return null;		
    	
    	
	}

	
	    public String getCityWeatherInfo(String lat, String lng) {
			
			RestTemplate rt = new RestTemplate();
			rt.getMessageConverters().clear();
			rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	        
			HttpHeaders headers = new HttpHeaders();
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    		String url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&units=metric";
    		
    		try{
				ResponseEntity<Example> response = rt.exchange (url, HttpMethod.GET, entity, Example.class);
				if(response != null) {
					return response.getBody().getMain().getTemp().toString();	
				}
    		}catch(Exception e) {
    			return null;
    		}
			return null;
	}
	    
	@Transactional(readOnly=false)	
	private void updateWeatherPeriodically(List<CityWeatherDb> list) {
		
		for(CityWeatherDb obj : list) {
			cityWeatherDao.updateCity(obj);
			System.out.println("Temperature updated for city: " + obj.getCityCode());
		}
	}
	    
	    
	    /*
	     * This method is for populating lat/long of cities. These cities were fetched from Expedia data.
	     */
	    public void populateCityLatLong() {
	    
	    	BufferedWriter brw = null;
	    	try {
	    	BufferedReader br = null;
	    	
	    	    int indexVal = 5;
	    	    
	    	    for(int i = 1; i <=5; i++) {
				String sCurrentLine;
	 
				br = new BufferedReader(new FileReader("D:\\ayan\\work\\tl\\CityCoordinatesList\\Source"+i+".txt"));
				brw = new BufferedWriter(new FileWriter("D:\\ayan\\work\\tl\\CityCoordinatesStatus.txt"));
				
				Map<String, String> mp = new HashMap<String, String>();	
				brw.newLine();
				
			
				while ((sCurrentLine = br.readLine()) != null) {
					String[] arr = sCurrentLine.split("\\|");
					mp.put(arr[0],arr[1]);
				}
	 
				br.close();
			
			int index = 0;
			Iterator it = mp.entrySet().iterator();
			while(it.hasNext()) {
				
				index++;
				Map.Entry pair = (Map.Entry)it.next();
			    String key = pair.getKey().toString();
			    String value = pair.getValue().toString();
			    
			    CityDb city = cityDao.getCityByName(key.trim());
			    if(city == null) {
			    		System.out.println("Could not find city : " + key + " for index: " + index);
			    	
						brw.write("Could not find city : " + key + " : for index: " + index);
						brw.newLine();
			    }
			    else {
			    	if(value != null) {
				    	String[] coordinates = value.split(";");
				    	
				    	city.setLatitude(new BigDecimal(coordinates[0]));
				    	city.setLongitude(new BigDecimal(coordinates[1]));
				    	
				    	cityDao.updateCity(city);
				    	
							brw.write("Successfully updated coordinates for city : " + city.getCityname());
							System.out.println("Successfully updated coordinates for city : " + city.getCityname());
							brw.newLine();
			    	}else {
			    		System.out.println("Coordinates are null for city : " + city + " for index: " + index);
							brw.write("Coordinates are null for city : " + city.getCityname() + " : for index: " + index);
							brw.newLine();
			
			    	}
			    }
			}
	    	
				brw.close();
	    		String x1 = "Ayan";
	    		System.out.println("Done for file: " + i);
	    	}
	    	
	    	} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					brw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }

}
