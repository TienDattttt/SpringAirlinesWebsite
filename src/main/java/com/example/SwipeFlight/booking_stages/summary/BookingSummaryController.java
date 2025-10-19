package com.example.SwipeFlight.booking_stages.summary;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SwipeFlight.auth.user.User;
import com.example.SwipeFlight.booking_stages.passenger.BookingPassengerService;
import com.example.SwipeFlight.booking_stages.passenger.PassengerListDTO;
import com.example.SwipeFlight.booking_stages.seat.BookingSeatService;
import com.example.SwipeFlight.entities.booking.BookingService;
import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.flight.FlightService;
import com.example.SwipeFlight.entities.luggage.LuggageService;
import com.example.SwipeFlight.entities.passenger.Passenger;
import com.example.SwipeFlight.entities.route.Route;
import com.example.SwipeFlight.entities.route.RouteService;
import com.example.SwipeFlight.server.utils.CastUtils;
import com.example.SwipeFlight.server.utils.custom_exceptions.RequestAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingSummaryController {

	// dependency injections
	@Autowired
	private BookingSummaryService bookingSummaryService;
	@Autowired
	private BookingPassengerService bookingPassengerService;
	@Autowired
	private BookingSeatService bookingSeatService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private LuggageService luggageService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private FlightService flightService;

	@GetMapping("/bookingSummary")
	public String viewBookingSummary(ModelMap model, HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: viewBookingSummary "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}
		// get session attribute: booking_routeNumber
		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: viewBookingSummary "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		// get session attribute: booking_passengers
		List<?> rawPassengerList = (List<?>) session.getAttribute("booking_passengers");
		if (rawPassengerList == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: viewBookingSummary "
					+ "\nDetails: Session attribute 'booking_passengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_passengers' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}
		List<Passenger> passengerList = CastUtils.castList(Passenger.class, rawPassengerList); // cast objects in list

		PassengerListDTO dto = new PassengerListDTO();
		dto.setPassengerList(passengerList);
		model.addAttribute("passengerListDTO", dto);

		// display route
		Route route = (Route) routeService.getRouteByRouteNumber(routeNumber);
		model.addAttribute("route", route);

		for (Passenger passenger : passengerList) {
			double totalLuggagePrice = bookingService.calculatePassengerTotalLuggagePrice(passenger); // so that the
																										// summary will
																										// appear for
																										// each
																										// passenger in
																										// jsp page
			// Add total luggage price for current passenger to the model
			model.addAttribute("totalLuggagePriceForPassenger_" + passenger.getPassportID(), totalLuggagePrice);
		}
		// display total tickets price for passenger
		model.addAttribute("totalTicketPriceForPassenger",
				bookingService.calculatePassengerTotalTicketPrice(routeNumber));

		// display booking total price
		model.addAttribute("totalPrice", bookingService.calculateBookingTotalPrice(passengerList, routeNumber));

		// display luggage list
		model.addAttribute("LuggageList", luggageService.getLuggageTypes());

		// display countDown for Seats step (for seats reservation)
		List<Flight> flightList = flightService.getAllFlightsInRouteNum(routeNumber);
		Timestamp seatReservationTime = bookingSeatService
				.getReservedTimeForPassAndFlight(passengerList.get(0).getPassportID(), flightList.get(0).getId());
		if (seatReservationTime != null) // if the rows are already deleted: null
			model.addAttribute("SeatsReservationTime", seatReservationTime);

		return "bookingSummary";
	}

	@PostMapping("/bookingSummary/ProcessBooking")
	public String processBooking(ModelMap model, HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: processBooking "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}
		// get session attribute: booking_routeNumber
		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: processBooking "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		// get session attribute: booking_passengers
		List<?> rawPassengerList = (List<?>) session.getAttribute("booking_passengers");
		if (rawPassengerList == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: processBooking "
					+ "\nDetails: Session attribute 'booking_passengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_passengers' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}
		List<Passenger> passengerList = CastUtils.castList(Passenger.class, rawPassengerList); // cast objects in list

		User sessionUser = (User) request.getAttribute("sessionUser");
		if (sessionUser == null) {
			System.err.println("\n*** Error ***\nClass: BookingSummaryController\t Method: processBooking "
					+ "\nDetails: Request attribute 'sessionUser' is not found.");
			throw new RequestAttributeNotFoundException("request attribute 'sessionUser' is not found."); // handler in
																											// GlobalExceptionHandler.java
		}

		double totalPrice = bookingService.calculateBookingTotalPrice(passengerList, routeNumber);
		Long bookingID = bookingSummaryService.insertIntoBooking(passengerList, routeNumber, totalPrice,
				sessionUser.getId());

		for (Passenger passenger : passengerList)
			bookingPassengerService.insertPassenger(passenger, bookingID);

		// clean reserved entries table
		List<Flight> flightList = flightService.getAllFlightsInRouteNum(routeNumber);
		for (Passenger curPass : passengerList)
			for (Flight curFlight : flightList)
				// delete entries in reserved seats
				bookingSeatService.deleteReservedSeatForPassAndFlight(curPass.getPassportID(), curFlight.getId());

		// clear booking session attributes
		clearSessionAttributes(session);

		return "redirect:/bookingSuccess?bookingID=" + bookingID;
	}

	@GetMapping("/bookingSuccess{bookingID}")
	public String viewBookingSuccess(@RequestParam("bookingID") Long bookingID, ModelMap model) {

		model.addAttribute("bookingID", bookingID);
		return "bookingSuccess";
	}

	public void clearSessionAttributes(HttpSession session)
			throws SessionIsInvalidException {
		if (session == null)
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java

		session.removeAttribute("booking_numOfPassengers");
		session.removeAttribute("booking_passengers");
		session.removeAttribute("booking_routeNumber");
	}
}
