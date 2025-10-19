package com.example.SwipeFlight.booking_stages.seat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SwipeFlight.booking_stages.passenger.PassengerListDTO;
import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.flight.FlightService;
import com.example.SwipeFlight.entities.passenger.Passenger;
import com.example.SwipeFlight.entities.seat.FlightDTO;
import com.example.SwipeFlight.entities.seat.SeatService;
import com.example.SwipeFlight.server.utils.CastUtils;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingSeatController {

	// dependency injections
	@Autowired
	private BookingSeatService bookingSeatService;
	@Autowired
	private FlightService flightService;
	@Autowired
	private SeatService seatService;

	@GetMapping("/bookingSeats")
	public String viewBookingSeatsPage(ModelMap model, RedirectAttributes redirectAttributes,
			HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: viewBookingSeatsPage "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}
		// get session attribute: booking_routeNumber
		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: viewBookingSeatsPage "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		// get session attribute: booking_passengers
		List<?> rawPassengerList = (List<?>) session.getAttribute("booking_passengers");
		if (rawPassengerList == null) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: viewBookingSeatsPage "
					+ "\nDetails: Session attribute 'booking_passengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_passengers' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}
		List<Passenger> passengerList = CastUtils.castList(Passenger.class, rawPassengerList); // cast objects in list

		// get session attribute: booking_numOfPassengers
		int numOfPassengers = (int) session.getAttribute("booking_numOfPassengers");
		if (numOfPassengers == 0) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: viewBookingSeatsPage "
					+ "\nDetails: Session attribute 'booking_numOfPassengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_numOfPassengers' is not found."); // handler
																														// in
																														// GlobalExceptionHandler.java
		}

		List<Flight> flightList = flightService.getAllFlightsInRouteNum(routeNumber);
		for (Passenger curPass : passengerList)
			for (Flight curFlight : flightList)
				bookingSeatService.deleteReservedSeatForPassAndFlight(curPass.getPassportID(), curFlight.getId());

		List<FlightDTO> flightDTOList = new ArrayList<>();
		for (Flight flight : flightList) {
			FlightDTO flightDTO = seatService.setFlightAndSeats(flight);
			flightDTOList.add(flightDTO);
		}

		PassengerListDTO dto = new PassengerListDTO();
		dto.setPassengerList(passengerList);
		model.addAttribute("passengerListDTO", dto);
		// display flightDTOList
		model.addAttribute("flightDTOList", flightDTOList);
		// display flightList
		model.addAttribute("flightList", flightList);
		// display numOfPassengers (=how many seats should be selected)
		model.addAttribute("numOfPassengers", numOfPassengers);

		String errorMessage = (String) redirectAttributes.getFlashAttributes().get("error_message");
		if (errorMessage != null) {
			model.addAttribute("error_message", errorMessage);
		}

		return "bookingSeats";
	}

	@PostMapping("/bookingSeats/ProcessBookingSeats")
	public String processBookingSeats(@RequestParam("selectedSeats") String selectedSeats,
			Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request)
			throws SessionIsInvalidException, SessionAttributeNotFoundException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: processBookingSeats "
					+ "\nDetails: Session is invalid.");
			throw new SessionIsInvalidException("Session is invalid."); // handler in GlobalExceptionHandler.java
		}
		// get session attribute: booking_passengers
		List<?> rawPassengerList = (List<?>) session.getAttribute("booking_passengers");
		if (rawPassengerList == null) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: processBookingSeats "
					+ "\nDetails: Session attribute 'booking_passengers' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_passengers' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}
		List<Passenger> passengerList = CastUtils.castList(Passenger.class, rawPassengerList); // cast objects in list

		// get session attribute: booking_routeNumber
		Long routeNumber = (Long) session.getAttribute("booking_routeNumber");
		if (routeNumber == null) {
			System.err.println("\n*** Error ***\nClass: BookingSeatController\t Method: processBookingSeats "
					+ "\nDetails: Session attribute 'booking_routeNumber' is not found.");
			throw new SessionAttributeNotFoundException("Session attribute 'booking_routeNumber' is not found."); // handler
																													// in
																													// GlobalExceptionHandler.java
		}

		// -------------------------------------------
		// actions
		// -------------------------------------------
		List<Flight> flightList = flightService.getAllFlightsInRouteNum(routeNumber);

		// make sure that user selected all seats, otherwise show error message
		String[] selectedSeatArray = selectedSeats.split(","); // get individual selected seats
		if (selectedSeatArray.length != passengerList.size() * flightList.size()) {
			redirectAttributes.addFlashAttribute("error_message",
					"Please select a seat for each passenger, on each flight");
			return "redirect:/bookingSeats";
		}

		// reserve selected seats, if not legal- display error message
		boolean isReservationLegal = bookingSeatService.reserveSelectedSeats(passengerList, flightList,
				selectedSeatArray);
		if (!isReservationLegal) {
			redirectAttributes.addFlashAttribute("error_message",
					"Some of the seats are now taken, please select again");
			return "redirect:/bookingSeats";
		}

		// display passengerList
		session.setAttribute("booking_passengers", passengerList);

		return "redirect:/bookingLuggage";
	}
}
