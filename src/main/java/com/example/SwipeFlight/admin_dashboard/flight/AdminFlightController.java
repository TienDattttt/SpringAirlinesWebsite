package com.example.SwipeFlight.admin_dashboard.flight;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.entities.airport.AirportService;
import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.plane.PlaneService;
import com.example.SwipeFlight.server.utils.BinderClass;

@Controller
public class AdminFlightController {

	// dependency injections
	@Autowired
	private BinderClass binderClass;
	@Autowired
	private AdminFlightService adminFlightService;
	@Autowired
	private PlaneService planeService;
	@Autowired
	private AirportService airportService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binderClass.initBinder(binder);
	}

	@GetMapping("/adminAddFlight")
	public String viewAdminAddFlightPage(ModelMap model) {

		model.addAttribute("flight", new Flight());

		model.addAttribute("planesList", planeService.getAllPlanes());

		model.addAttribute("departureAirportsList", airportService.getAllDepartureAirports());

		return "adminAddFlight";
	}

	@PostMapping("/adminAddFlight/ProcessFlightInsertion")
	public String processFlightInsertion(Flight flight, ModelMap model,
			RedirectAttributes redirectAttributes, BindingResult result) {

		Date departureDate = flight.getDepartureDate();
		Time departureTime = flight.getDepartureTime();
		double ticketPrice = flight.getTicketPrice();
		Duration duration = flight.getDuration();

		if (departureDate == null)
			result.rejectValue("departureDate", "error.flight", "Trường này là bắt buộc.");
		if (departureTime == null)
			result.rejectValue("departureTime", "error.flight", "Trường này là bắt buộc.");
		if (ticketPrice <= 0)
			result.rejectValue("ticketPrice", "error.flight", "Giá trị không hợp lệ.");

		if (duration == null)
			result.rejectValue("duration", "error.flight", "Trường này là bắt buộc.");
		else if (duration.isZero())
			result.rejectValue("duration", "error.flight", "Vui lòng nhập thời lượng theo đúng định dạng.");

		result = adminFlightService.validateFlightInsertion(flight, result);

		redirectAttributes.addFlashAttribute("result", result);
		redirectAttributes.addFlashAttribute("flight", flight);

		if (result.hasErrors()) {
			return "redirect:/adminAddFlightRedirect";
		}

		adminFlightService.insertFlight(flight);

		return "redirect:/adminAddFlightRedirect";
	}

	@GetMapping("/adminAddFlightRedirect")
	public String adminAddFlightRedirect(Model model, RedirectAttributes redirectAttributes) {
		Flight flight = null;

		if (model.asMap().containsKey("result") && model.asMap().containsKey("flight")) {
			// result
			BindingResult result = (BindingResult) model.asMap().get("result");
			model.addAttribute("org.springframework.validation.BindingResult.flight",
					model.asMap().get("result"));

			// flight
			flight = (Flight) model.asMap().get("flight");
			model.addAttribute("flight", flight);

			if (!result.hasErrors()) {
				redirectAttributes.addFlashAttribute("add_flight_success_message",
						"Chuyến bay đã được thêm thành công.");
				return "redirect:/adminRoutes";
			}
		}

		if (flight == null)

			model.addAttribute("flight", new Flight());
		else
			model.addAttribute("flight", flight);

		model.addAttribute("planesList", planeService.getAllPlanes());

		model.addAttribute("departureAirportsList", airportService.getAllDepartureAirports());

		return "adminAddFlight";
	}
}
