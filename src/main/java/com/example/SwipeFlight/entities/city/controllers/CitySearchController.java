package com.example.SwipeFlight.entities.city.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SwipeFlight.entities.city.City;
import com.example.SwipeFlight.entities.city.CityService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CitySearchController {

	@Autowired // dependency injection
	private CityService cityService;

	@GetMapping("/home{selectedCityFromSuggestions}")
	public String viewHomePage(
			@RequestParam(name = "selectedCityFromSuggestions", required = false) String selectedCityFromSuggestions,
			ModelMap model, HttpServletRequest request) {

		if (selectedCityFromSuggestions == null) // page will consist all the cities by default
			selectedCityFromSuggestions = "";
		List<City> citiesList = cityService.getCitiesByKeyboard(selectedCityFromSuggestions);
		model.addAttribute("citiesList", citiesList);
		model.addAttribute("citySearch", selectedCityFromSuggestions);

		return "home";
	}

	@GetMapping("/citySearch/fetchSuggestionsForCitySearch")
	@ResponseBody
	public List<String> fetchSuggestionsForCitySearch(HttpServletRequest request) {
		// term = the string that the user entered
		return cityService.getCityNamesByKeyboard(request.getParameter("term"));
	}
}
