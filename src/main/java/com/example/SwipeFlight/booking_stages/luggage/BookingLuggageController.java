package com.example.SwipeFlight.booking_stages.luggage;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SwipeFlight.booking_stages.passenger.PassengerListDTO;
import com.example.SwipeFlight.booking_stages.seat.BookingSeatService;
import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.flight.FlightService;
import com.example.SwipeFlight.entities.luggage.LuggageService;
import com.example.SwipeFlight.entities.passenger.Passenger;
import com.example.SwipeFlight.server.utils.CastUtils;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingLuggageController {

	// dependency injections
	@Autowired
	private BookingLuggageService bookingLuggageService;
	@Autowired
	private LuggageService luggageService;
	@Autowired
	private FlightService flightService;
	@Autowired
	private BookingSeatService bookingSeatService;

	@GetMapping("/bookingLuggage")
	public String viewBookingLuggagePage(Model model, HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingLuggageController\t Method: viewBookingLuggagePage "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}

		List<?> rawPassengerList = (List<?>) session.getAttribute("booking_passengers");
		if (rawPassengerList == null) {
			System.err.println("\n*** Error ***\nClass: BookingLuggageController\t Method: viewBookingLuggagePage "
					+ "\nDetails: Session attribute 'booking_passengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_passengers' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}
		List<Passenger> passengerList = CastUtils.castList(Passenger.class, rawPassengerList); // cast objects in list

		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingLuggageController\t Method: viewBookingLuggagePage "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		PassengerListDTO dto = new PassengerListDTO();
		dto.setPassengerList(passengerList);
		model.addAttribute("passengerListDTO", dto);

		model.addAttribute("LuggageList", luggageService.getLuggageTypes());

		List<Flight> flightList = flightService.getAllFlightsInRouteNum(routeNumber);
		Timestamp seatReservationTime = bookingSeatService
				.getReservedTimeForPassAndFlight(passengerList.get(0).getPassportID(), flightList.get(0).getId());
		if (seatReservationTime != null)
			model.addAttribute("SeatsReservationTime", seatReservationTime);

		return "bookingLuggage";
	}

	@PostMapping("/bookingLuggage/ProcessBookingLuggage")
	public String processBookingLuggage(@ModelAttribute("passengerListDTO") PassengerListDTO passengerListDTO,
			HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingLuggageController\t Method: processBookingLuggage "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}
		// get session attribute: booking_passengers
		List<?> rawPassengerList = (List<?>) session.getAttribute("booking_passengers");
		if (rawPassengerList == null) {
			System.err.println("\n*** Error ***\nClass: BookingLuggageController\t Method: processBookingLuggage "
					+ "\nDetails: Session attribute 'booking_passengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_passengers' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}
		List<Passenger> passengerList = CastUtils.castList(Passenger.class, rawPassengerList); // cast objects in list

		// get session attribute: booking_routeNumber
		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingLuggageController\t Method: processBookingLuggage "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		List<Flight> flightList = flightService.getAllFlightsInRouteNum(routeNumber);

		for (Passenger passenger : passengerList) {

			String[] luggageIds = request.getParameterValues("passenger_" + passenger.getPassportID() + "_luggageIds");
			bookingLuggageService.setPassengersLuggage(passenger, flightList, luggageIds);
		}

		session.setAttribute("booking_passengers", passengerList);

		return "redirect:/bookingSummary";
	}
}
