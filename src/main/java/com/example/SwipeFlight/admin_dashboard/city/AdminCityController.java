package com.example.SwipeFlight.admin_dashboard.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.entities.city.City;
import com.example.SwipeFlight.entities.city.CityService;
import com.example.SwipeFlight.entities.country.CountryService;
import com.example.SwipeFlight.server.utils.FileUploadService;

@Controller
public class AdminCityController {

    @Autowired
    private CityService cityService;
    @Autowired
    private FileUploadService flightUploadService;
    @Autowired
    private CountryService countryService;

    @PostMapping("/adminCity{cityID}")
    public String viewAdminCityPage(@RequestParam("cityID") Long cityID,
            ModelMap model, RedirectAttributes redirectAttributes) {
        City city = cityService.getCityByID(cityID);
        model.addAttribute("city", city);
        return "adminCity";
    }

    @PostMapping("/adminCity/ProcessCityModification")
    public String processCityModification(@RequestParam("fileInput") MultipartFile fileInput, City city,
            ModelMap model, RedirectAttributes redirectAttributes, BindingResult result) {
        String filePath = "";

        if (fileInput != null) {

            filePath = flightUploadService.fileUpload(fileInput, "City", result);
        }

        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute("city", city);

        if (result.hasErrors()) {
            return "redirect:/adminCity/cityRedirect?cityID=" + city.getId();
        }

        cityService.updateCity(city, filePath);
        redirectAttributes.addFlashAttribute("success_message", "Thay đổi đã được áp dụng thành công.");

        return "redirect:/adminCity/cityRedirect?cityID=" + city.getId(); // Post-Redirect-Get pattern
    }

    @GetMapping("/adminCity/cityRedirect{cityID}")
    public String viewCityPageRedirect(@RequestParam("cityID") Long cityID,
            Model model, RedirectAttributes redirectAttributes) {
        if (model.asMap().containsKey("result") && model.asMap().containsKey("city")) {
            // result
            BindingResult result = (BindingResult) model.asMap().get("result");
            model.addAttribute("org.springframework.validation.BindingResult.city",
                    model.asMap().get("result"));

            // city
            City city = (City) model.asMap().get("city");
            city.setCountry(countryService.getCountryByID(cityService.getCountryIdOfCityByID(cityID)));
            city.setImgUrl(cityService.getCityByID(cityID).getImgUrl());
            model.addAttribute("city", city);
        }

        else {
            City city = cityService.getCityByID(cityID);
            model.addAttribute("city", city);
        }

        return "adminCity";
    }
}