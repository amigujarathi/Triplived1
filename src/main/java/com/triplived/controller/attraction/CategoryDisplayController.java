package com.triplived.controller.attraction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connectme.domain.triplived.dto.CategoryResponseDTO;
import com.google.gson.Gson;
import com.triplived.service.staticContent.StaticCategoryService;


@Controller
@RequestMapping("/categoryDisplay")
public class CategoryDisplayController {
	
	@Autowired
	private StaticCategoryService staticCategoryService;
	
	
	@RequestMapping(method= RequestMethod.GET)
	public @ResponseBody String getCategoryList(){
		
		List<CategoryResponseDTO> categories = staticCategoryService.displayCategory();
		Gson gson = new Gson();
		return gson.toJson(categories);
		
	}

}
