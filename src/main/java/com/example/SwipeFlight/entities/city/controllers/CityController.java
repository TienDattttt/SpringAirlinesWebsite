package com.example.SwipeFlight.entities.city.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.entities.city.City;
import com.example.SwipeFlight.entities.city.CityService;
import com.example.SwipeFlight.entities.country.CountryService;

@Controller
public class CityController {

    // dependency injection
    @Autowired
    private CityService cityService;

    @PostMapping("/city{cityID}")
    public String viewCityPage(@RequestParam("cityID") Long cityID, ModelMap model) {
        City city = cityService.getCityByID(cityID); // retrieve the City object
        model.addAttribute("city", city);
        return "city";
    }
}
