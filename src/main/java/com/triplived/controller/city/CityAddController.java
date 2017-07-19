package com.triplived.controller.city;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.triplived.dao.attraction.CityDao;
import com.triplived.entity.CityDb;
import com.triplived.entity.CountryDb;



@Controller
@RequestMapping("/city/add")
public class CityAddController {
	
	@Autowired
	private CityDao cityDao;
	
	//Post new City in Db
	
		@RequestMapping(method= RequestMethod.GET, value="/curate/{countryCode}/{param}")
		public @ResponseBody String curateDb(@PathVariable("param") String param, 
	    		@PathVariable("countryCode") String countryCode,
	    		HttpSession session, Model model, HttpServletRequest request) {
		
			List<CityDb> citiesDbList = cityDao.getAllCities();
			Integer counter = 32345;
			int loop = 0;
			for(CityDb cityDb : citiesDbList) {
				CityDb newCity = new CityDb();
				newCity.setChtAltitude(cityDb.getChtAltitude());
				newCity.setCountry(cityDb.getCountry());
				newCity.setCityCode(cityDb.getCityCode());
				newCity.setCityCodeTl(cityDb.getCityCodeTl());
				newCity.setCityDescription(cityDb.getCityDescription());
				newCity.setCityname(cityDb.getCityname());
				newCity.setCreatedBy(cityDb.getCreatedBy());
				newCity.setCreatedDate(cityDb.getCreatedDate());
				newCity.setLatitude(cityDb.getLatitude());
				newCity.setLongitude(cityDb.getLongitude());
				newCity.setState(cityDb.getState());
				newCity.setStatus(cityDb.getStatus());
				newCity.setUpdatedBy(cityDb.getUpdatedBy());
				newCity.setUpdatedDate(cityDb.getUpdatedDate());
				String code = cityDb.getCityCode();
				String first = "tl";
				counter += 789;
				if(loop%10 == 0) {
					counter +=7890;
				}
				loop++;
				Integer x = counter;
				
				
				StringBuilder sb = new StringBuilder();
				sb.append(first);
				sb.append(x.toString());
				newCity.setCityCode(sb.toString());
				//cityDao.updateAllCity(newCity);
			}
			//return gson.toJson(cities);
			return null;
	   }	


		@RequestMapping(method= RequestMethod.GET, value="/new/{countryCode}/{cityName}/{cityCode}")
		public @ResponseBody String addNewCity(@PathVariable("cityName") String cityName, 
	    		@PathVariable("countryCode") String countryCode,
	    		@PathVariable("cityCode") String cityCode,
	    		HttpSession session, Model model, HttpServletRequest request) {
		
			
			    String first = "tln"+cityCode;
				CityDb newCity = new CityDb();
				
				CountryDb country = new CountryDb();
				country.setCountryCode("IN");
				
				newCity.setCountry(country);
				newCity.setCityCode(first);
				newCity.setCityname(cityName);
				newCity.setCreatedDate(new Date());
				//newCity.setState(cityDb.getState());
				newCity.setStatus("A");
				newCity.setUpdatedDate(new Date());
				newCity.setCityCodeTl("New");
				
				cityDao.updateAllCity(newCity);
				return "Updated";
			
	   }	

}
