package com.example.SwipeFlight.booking_stages.passenger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.entities.passenger.Passenger;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingPassengerController {

	@Autowired // dependency injection
	private BookingPassengerService bookingPassengerService;

	@GetMapping("/bookingPassengers")
	public String viewBookingPassengers(Model model, HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerController\t Method: viewBookingPassengers "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid.");
		}

		int numOfPassengers;
		if (session.getAttribute("booking_numOfPassengers") == null) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerController\t Method: viewBookingPassengers "
					+ "\nDetails: Session attribute 'booking_numOfPassengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_numOfPassengers' is not found.");
		} else
			numOfPassengers = (int) session.getAttribute("booking_numOfPassengers");

		List<Passenger> passengerList = new ArrayList<>();
		for (int i = 0; i < numOfPassengers; i++) {
			passengerList.add(new Passenger());
		}

		PassengerListDTO dto = new PassengerListDTO();
		dto.setPassengerList(passengerList);
		model.addAttribute("passengerListDTO", dto);

		return "bookingPassengers";
	}

	@PostMapping("/bookingPassengers/ProcessBookingPassengers")
	public String processBookingPassengers(PassengerListDTO passengerListDTO,
			BindingResult result, Model model,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerController\t Method: processBookingPassengers "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}

		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerController\t Method: processBookingPassengers "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		// -------------------------------------------
		// actions
		// -------------------------------------------
		// get passengerList from dto
		List<Passenger> passengerList = passengerListDTO.getPassengerList();
		// validate list of passengers
		result = bookingPassengerService.validatePassengers(passengerList, routeNumber, result);

		redirectAttributes.addFlashAttribute("result", result);
		redirectAttributes.addFlashAttribute("passengerListDTO", passengerListDTO);

		// failure:
		if (result.hasErrors()) {
			return "redirect:/bookingPassengersRedirect";
		}

		session.setAttribute("booking_passengers", passengerList);

		return "redirect:/bookingPassengersRedirect";
	}

	@GetMapping("/bookingPassengersRedirect")
	public String BookingPassengersRedirect(Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {
		PassengerListDTO passengerListDTO = null;

		// if previous POST request saved the necessary elements
		if (model.asMap().containsKey("result") && model.asMap().containsKey("passengerListDTO")) {
			// result
			BindingResult result = (BindingResult) model.asMap().get("result");
			model.addAttribute("org.springframework.validation.BindingResult.passengerListDTO",
					model.asMap().get("result"));

			// passengerList
			passengerListDTO = (PassengerListDTO) model.asMap().get("passengerListDTO");
			model.addAttribute("passengerListDTO", passengerListDTO);

			if (!result.hasErrors())
				return "redirect:/bookingSeats"; // redirect to the next step
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerController\t Method: BookingPassengersRedirect "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid.");
		}

		int numOfPassengers;
		if (session.getAttribute("booking_numOfPassengers") == null) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerController\t Method: BookingPassengersRedirect "
					+ "\nDetails: Session attribute 'booking_numOfPassengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_numOfPassengers' is not found."); // handler
																														// in
																														// GlobalExceptionHandler.java
		} else
			numOfPassengers = (int) session.getAttribute("booking_numOfPassengers");

		if (passengerListDTO == null) {

			List<Passenger> passengerList = new ArrayList<>();
			for (int i = 0; i < numOfPassengers; i++) {
				passengerList.add(new Passenger());
			}

			PassengerListDTO dto = new PassengerListDTO();
			dto.setPassengerList(passengerList);
			model.addAttribute("passengerListDTO", dto);
		} else {
			model.addAttribute("passengerListDTO", passengerListDTO);
		}

		return "bookingPassengers";
	}
}
