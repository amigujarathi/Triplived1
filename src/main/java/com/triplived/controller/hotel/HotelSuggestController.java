package com.triplived.controller.hotel;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.HotelResponseDTO;
import com.google.gson.Gson;
import com.triplived.service.staticContent.StaticHotelService;

@Controller
@RequestMapping("/hotelSuggest")
public class HotelSuggestController {
	
	@Autowired
	private StaticHotelService staticHotelService;
	 
	@RequestMapping(method= RequestMethod.GET, value="/{city}/{param}")
   public @ResponseBody String getHotelsByCity(@PathVariable("city") String cityCode,@PathVariable("param") String param, HttpSession session, Model model, HttpServletRequest request) {
	
		List<HotelResponseDTO> hotels = staticHotelService.suggestHotelByCity(cityCode, param);
		
		if(!CollectionUtils.isEmpty(hotels)) {
			Gson gson = new Gson();
			return gson.toJson(hotels);
		}
		return null;
		
   }
	
	/**
	 * This method is for backend portal for add/update hotels
	 * @param cityCode
	 * @param param
	 * @param session
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET, value="/portal/{city}/{param}")
	   public @ResponseBody String getHotelsByCityForPortal(@PathVariable("city") String cityCode,@PathVariable("param") String param, HttpSession session, Model model, HttpServletRequest request) {
		
			List<HotelResponseDTO> hotels = staticHotelService.getHotelsForPortal(cityCode, param);
			Collections.sort(hotels);
			
			if(!CollectionUtils.isEmpty(hotels)) {
				Gson gson = new Gson();
				return gson.toJson(hotels);
			}
			return null;
			
	   }

}
