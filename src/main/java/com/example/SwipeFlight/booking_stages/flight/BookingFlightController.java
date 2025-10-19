package com.example.SwipeFlight.booking_stages.flight;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingFlightController {

	@Autowired // dependency injection
	private BookingFlightService bookingFlightService;

	@GetMapping("/bookingFlight")
	public String viewBookingFlightPage(@RequestParam("departureAirportID") Long departureAirportID, // from previous
																										// step
			@RequestParam("arrivalAirportID") Long arrivalAirportID,
			@RequestParam("departureDate") Date departureDate,
			@RequestParam("numOfPassengers") int numOfPassengers,
			ModelMap model) {

		model.addAttribute("routesList",
				bookingFlightService.getRoutesForFlightRequest(departureAirportID, arrivalAirportID,
						numOfPassengers, departureDate));
		return "bookingFlight";
	}

	@PostMapping("/bookingFlight/ProcessBookingFlight{routeNumber}")
	public String processBookingFlight(@RequestParam("routeNumber") Long routeNumber,
			ModelMap model, HttpServletRequest request)
			throws SessionIsInvalidException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingFlightController\t Method: processBookingFlight "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid.");
		}

		session.setAttribute("booking_routeNumber", routeNumber);

		return "redirect:/bookingPassengers";
	}

}
