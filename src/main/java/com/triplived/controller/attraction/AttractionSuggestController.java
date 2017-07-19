package com.triplived.controller.attraction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.AttractionResponseDTO;
import com.google.gson.Gson;
import com.triplived.service.staticContent.StaticAttractionService;

@Controller
@RequestMapping("/attractionSuggest")
public class AttractionSuggestController {
	
	@Autowired
	private StaticAttractionService staticAttractionService;
	 
	@RequestMapping(method= RequestMethod.GET, value="/{city}/{param}")
   public @ResponseBody String getAttractionsByCity(@PathVariable("city") String city,@PathVariable("param") String param) {
	
		List<AttractionResponseDTO> attractions = staticAttractionService.suggestAttractionByCity(city, param);
		Gson gson = new Gson();
		return gson.toJson(attractions);
   }
	
	
	

}
