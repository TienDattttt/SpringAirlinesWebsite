package com.example.SwipeFlight.entities.booking;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.luggage.LuggageService;
import com.example.SwipeFlight.entities.passenger.Passenger;
import com.example.SwipeFlight.entities.route.Route;
import com.example.SwipeFlight.entities.route.RouteService;

/**
 * The class represents Service layer of Booking.
 * (interacts with Repository layer)
 */
@Service
public class BookingService {

	// dependency injections
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private RouteService routeService;
	@Autowired
	private LuggageService luggageService;

	public List<Booking> getBookingsForUserID(Long userID) throws IllegalArgumentException {
		List<Booking> result = bookingRepository.getBookingsForUserID(userID);
		for (Booking booking : result)
			setRouteOfBooking(booking);
		return result;
	}

	public List<Booking> getBookingsByFilters(Long bookingID, Long userID, Boolean isCanceled)
			throws IllegalArgumentException {
		return bookingRepository.getBookingsByFilters(bookingID, userID, isCanceled);
	}

	public Booking getBookingByID(Long bookingID) throws IllegalArgumentException {
		Booking booking = bookingRepository.getBookingByID(bookingID);
		setRouteOfBooking(booking);
		return booking;
	}

	public Long getRouteNumberOfBookingByID(Long bookingID) throws IllegalArgumentException {
		return bookingRepository.getRouteNumberOfBookingByID(bookingID);
	}

	public double calculateBookingTotalPrice(List<Passenger> passengerList, Long routeNumber) {
		double total_luggage_price = 0, total_ticket_price = 0;
		for (Passenger passenger : passengerList) {
			total_luggage_price += calculatePassengerTotalLuggagePrice(passenger);
		}
		total_ticket_price += routeService.getRouteByRouteNumber(routeNumber).getRoutePriceForSinglePassenger()
				* passengerList.size();

		return total_luggage_price + total_ticket_price;
	}

	public double calculatePassengerTotalLuggagePrice(Passenger passenger) {
		double total_luggage_price = 0;

		Map<Long, List<String>> flightAndLuggage = passenger.getFlightAndLuggage();

		// Iterate through passenger's flight IDs
		for (Map.Entry<Long, List<String>> entry : flightAndLuggage.entrySet()) {
			Long flightId = entry.getKey();

			// get passenger's luggage IDs, relating to flightId
			List<String> luggageIds = flightAndLuggage.getOrDefault(flightId, List.of());

			// Iterate through passenger's luggage IDs
			for (String luggageId : luggageIds) {
				Long longLuggageId = Long.parseLong(luggageId);
				total_luggage_price = total_luggage_price + luggageService.getLuggagePrice(longLuggageId);
			}
		}
		return total_luggage_price;
	}

	public double calculatePassengerTotalTicketPrice(Long routeNumber) {
		Route route = routeService.getRouteByRouteNumber(routeNumber);
		if (route != null)
			return route.getRoutePriceForSinglePassenger();
		return 0;
	}

	private void setRouteOfBooking(Booking booking) {
		Long routeNumber = bookingRepository.getRouteNumberOfBookingByID(booking.getId());
		Route route = (Route) routeService.getRouteByRouteNumber(routeNumber);
		booking.setRoute(route);
	}

	public void updateTotalPrice(Long bookingID, double newTotalPrice) throws IllegalArgumentException {
		bookingRepository.updateTotalPrice(bookingID, newTotalPrice);
	}

	public void updateLastModify(Long bookingID) throws IllegalArgumentException {
		bookingRepository.updateLastModify(bookingID);
	}

}
