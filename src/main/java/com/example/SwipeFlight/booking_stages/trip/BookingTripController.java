package com.example.SwipeFlight.booking_stages.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.entities.airport.AirportService;
import com.example.SwipeFlight.server.utils.BinderClass;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingTripController {

	// dependency injections
	@Autowired
	private BinderClass binderClass;
	@Autowired
	private BookingTripService tripService;
	@Autowired
	private AirportService airportService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binderClass.initBinder(binder);
	}

	@GetMapping("/bookingTrip")
	public String viewBookingTrip(ModelMap model) {
		// display empty Trip form
		model.addAttribute("tripForm", new TripForm());

		// display airports
		model.addAttribute("departureAirportsList", airportService.getAllDepartureAirports());

		return "bookingTrip";
	}

	@PostMapping("/bookingTrip/ProcessBookingTrip")
	public String processBookingTrip(@ModelAttribute("tripForm") TripForm tripForm,
			ModelMap model, RedirectAttributes redirectAttributes,
			BindingResult result, HttpServletRequest request)
			throws SessionIsInvalidException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingTripController\t Method: processBookingTrip "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}

		result = tripService.validateTripForm(tripForm, result);

		redirectAttributes.addFlashAttribute("result", result);
		redirectAttributes.addFlashAttribute("tripForm", tripForm);

		// failure:
		if (result.hasErrors()) {
			return "redirect:/bookingTripRedirect";
		}

		session.setAttribute("booking_numOfPassengers", tripForm.getNumOfPassengers());

		return "redirect:/bookingTripRedirect";
	}

	@GetMapping("/bookingTripRedirect")
	public String bookingTripRedirect(Model model, RedirectAttributes redirectAttributes) {
		TripForm tripForm = null;

		if (model.asMap().containsKey("result") && model.asMap().containsKey("tripForm")) {
			// result
			BindingResult result = (BindingResult) model.asMap().get("result");
			model.addAttribute("org.springframework.validation.BindingResult.tripForm",
					model.asMap().get("result"));

			// tripForm
			tripForm = (TripForm) model.asMap().get("tripForm");
			model.addAttribute("tripForm", tripForm);

			if (!result.hasErrors())
				// redirect to next step
				return "redirect:/bookingFlight?departureAirportID=" + tripForm.getDepartureAirportID() // redirect to
																										// the next step
						+ "&arrivalAirportID=" + tripForm.getArrivalAirportID()
						+ "&departureDate=" + tripForm.getDepartureDate()
						+ "&numOfPassengers=" + tripForm.getNumOfPassengers();
		}
		// continue actions of GET request
		// --------------------------------------------
		if (tripForm == null)
			model.addAttribute("tripForm", new TripForm());
		else
			model.addAttribute("tripForm", tripForm);
		// display airports
		model.addAttribute("departureAirportsList", airportService.getAllDepartureAirports());

		return "bookingTrip"; // Post-Redirect-Get pattern

	}

}
