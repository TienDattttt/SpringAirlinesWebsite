package com.example.SwipeFlight.admin_dashboard.route.cancel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRouteCancelController {

	// dependency injections
	@Autowired
	private AdminRouteCancelService adminRouteCancelService;

	@PostMapping("/adminRoutes/ProcessRouteCancellation{routeNumber}")
	public String processRouteCancellation(@RequestParam("routeNumber") Long routeNumber, ModelMap model) {

		adminRouteCancelService.cancelRoute(routeNumber);

		return "redirect:/adminRoutes";
	}

}
