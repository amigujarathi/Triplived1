package com.triplived.controller.trip;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.controller.home.HomeController;
import com.connectme.domain.triplived.dto.TripCategoryDTO;
import com.google.gson.Gson;
import com.triplived.entity.TripCategoryDb;
import com.triplived.service.trip.TripCategoryService;

@Controller
@RequestMapping("/displayTripCategory")
public class TripCategoryController {

	@Autowired
	private TripCategoryService tripCategoryService;

	/**
	 * This API is invoked, when the user tries to search for trip categories.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)	
	public @ResponseBody String getTripCategory() {
		List<TripCategoryDTO> categories = tripCategoryService.getTripCategories();
		System.out.println(categories);
		Gson gson = new Gson();
		return gson.toJson(categories);
	}
}