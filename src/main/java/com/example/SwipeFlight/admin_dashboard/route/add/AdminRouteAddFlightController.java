package com.example.SwipeFlight.admin_dashboard.route.add;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.flight.FlightService;
import com.example.SwipeFlight.entities.route.Route;
import com.example.SwipeFlight.entities.route.RouteService;

@Controller
public class AdminRouteAddFlightController {

	// dependency injections
	@Autowired
	private AdminRouteAddFlightService adminRouteAddFlightService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private FlightService flightService;

	@GetMapping("/adminRouteAddFlight")
	public String viewAdminAddToRoutePage(Route route, ModelMap model) {

		route = routeService.getRouteByRouteNumber(route.getRouteNumber());

		List<Flight> suggestedFlights = adminRouteAddFlightService.getSuggestedFlightsToAdd(route);
		if (!suggestedFlights.isEmpty())
			model.addAttribute("suggestedFlights", suggestedFlights);

		return "adminRouteAddFlight";
	}

	@PostMapping("/adminRouteAddFlight/ProcessAddFlightToRoute")
	public String processAddFlightToRoute(Route route, ModelMap model, BindingResult result) {
		result = adminRouteAddFlightService.validateAddFlightToRoute(route, result);
		if (!result.hasErrors()) {
			// add the selected flight to the route
			adminRouteAddFlightService.addFlightToRoute(route);
			model.addAttribute("success_message", "The flight was added to the route successfully.");
		}

		// display the route with its flights
		route = routeService.getRouteByRouteNumber(route.getRouteNumber());
		model.addAttribute("flightsInRouteList", adminRouteAddFlightService.getSuggestedFlightsToAdd(route));

		return "adminRouteAddFlight";
	}

	@GetMapping("/adminRouteAddFlight/FetchFlightsInRouteNum{routeNumber}")
	@ResponseBody
	public List<Flight> fetchFlightsInRouteNum(@RequestParam("routeNumber") Long routeNumber) {
		List<Flight> flights = flightService.getAllFlightsInRouteNum(routeNumber);
		return flights;
	}
}
