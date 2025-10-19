package com.example.SwipeFlight.admin_dashboard.route.add;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.flight.FlightService;
import com.example.SwipeFlight.entities.route.Route;

@Service
public class AdminRouteAddFlightService {

	// dependency injections
	@Autowired
	private AdminRouteAddFlightRepository adminRouteAddFlightRepository;
	@Autowired
	private FlightService flightService;

	public List<Flight> getSuggestedFlightsToAdd(Route route) throws IllegalArgumentException {
		List<Flight> result = adminRouteAddFlightRepository.getSuggestedFlightsToAdd(route);
		for (Flight flight : result) {
			flightService.setPlaneOfFlight(flight);
			flightService.setDepartureAirportOfFlight(flight);
			flightService.setArrivalAirportOfFlight(flight);
		}
		return result;
	}

	public void addFlightToRoute(Route route) throws IllegalArgumentException {

		Long nextSequenceNumber = adminRouteAddFlightRepository.retrieveNextSequenceNumber(route.getRouteNumber());

		if (nextSequenceNumber == null || route.getFlights() == null)
			return;

		Long flightIDToAdd = route.getFlights().get(0).getId();

		adminRouteAddFlightRepository.breakDirectRouteForFlight(flightIDToAdd);

		adminRouteAddFlightRepository.addFlightToSequence(route.getRouteNumber(), flightIDToAdd, nextSequenceNumber);
	}

	public BindingResult validateAddFlightToRoute(Route route, BindingResult result) {
		Flight flightToAdd = route.getFlights().get(0);

		if (flightToAdd == null || flightToAdd.getId() == null || flightToAdd.getId() == 0)
			result.rejectValue("flights[0].id", "error.route", "Vui lòng chọn chuyến bay.");

		return result;
	}
}
