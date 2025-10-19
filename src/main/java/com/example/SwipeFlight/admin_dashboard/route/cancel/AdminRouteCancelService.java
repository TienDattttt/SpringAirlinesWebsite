package com.example.SwipeFlight.admin_dashboard.route.cancel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.flight.FlightService;
import com.example.SwipeFlight.entities.plane.PlaneService;

@Service
public class AdminRouteCancelService {

	// dependency injections
	@Autowired
	private AdminRouteCancelRepository adminRouteCancelRepository;
	@Autowired
	private FlightService flightService;
	@Autowired
	private PlaneService planeService;

	public void cancelRoute(Long routeNumber) throws IllegalArgumentException {

		List<Flight> flights = flightService.getAllFlightsInRouteNum(routeNumber);
		for (Flight flight : flights) {

			adminRouteCancelRepository.cancelFlight(flight.getId());

			adminRouteCancelRepository.deleteFromPassengerFlight(flight.getId());

			planeService.updateAvailableQuantity(flight.getPlane().getId(), false);
		}
	}

}
