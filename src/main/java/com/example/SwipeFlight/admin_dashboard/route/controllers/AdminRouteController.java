package com.example.SwipeFlight.admin_dashboard.route.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.entities.airport.AirportService;
import com.example.SwipeFlight.entities.route.RouteService;

@Controller
public class AdminRouteController {

	@Autowired // dependency injection
	private RouteService routeService;

	@Autowired // dependency injection
	private AirportService airportService;

	@GetMapping("/adminRoutes")
	public String viewAdminRoutesPage(@RequestParam(value = "routeNumber", required = false) Long routeNumber,
			@RequestParam(value = "departureAirportID", required = false) Long departureAirportID,
			@RequestParam(value = "arrivalAirportID", required = false) Long arrivalAirportID,
			@RequestParam(value = "departureDate", required = false, defaultValue = "1970-01-01") Date departureDate,
			@RequestParam(value = "arrivalDate", required = false, defaultValue = "1970-01-01") Date arrivalDate,
			Model model, RedirectAttributes redirectAttributes) {
		// display routes for filters
		model.addAttribute("routesList", routeService.getRoutesByFilters(routeNumber, departureAirportID,
				arrivalAirportID, departureDate, arrivalDate));

		// display airports
		model.addAttribute("airportsList", airportService.getAllDepartureAirports());

		String add_flight_success_message = (String) redirectAttributes.getFlashAttributes()
				.get("add_flight_success_message");
		if (add_flight_success_message != null) {
			model.addAttribute("add_flight_success_message", add_flight_success_message);
		}

		return "adminRoutes";
	}

}
