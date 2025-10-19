package com.example.SwipeFlight.admin_dashboard.route.allow_booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SwipeFlight.entities.route.RouteService;

@Controller
public class AdminRouteAllowBookingController {

	@Autowired
	private RouteService routeService;

	@PostMapping("/adminRoutes/ProcessRouteAllowBooking{routeNumber}")
	public String processRouteCancellation(@RequestParam("routeNumber") Long routeNumber, ModelMap model) {

		routeService.updateAllowBooking(routeNumber, true);

		return "redirect:/adminRoutes";
	}
}
