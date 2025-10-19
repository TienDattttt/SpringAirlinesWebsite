package com.example.SwipeFlight.admin_dashboard.flight;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.SwipeFlight.entities.airport.Airport;
import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.plane.Plane;
import com.example.SwipeFlight.entities.plane.PlaneService;
import com.example.SwipeFlight.server.utils.DateTimeUtils;

@Service
public class AdminFlightService {

	// dependency injections
	@Autowired
	private AdminFlightRepository adminFlightRepository;
	@Autowired
	private PlaneService planeService;

	public void insertFlight(Flight flight) throws IllegalArgumentException {

		LocalDateTime newArrivalDateTime = DateTimeUtils.calculateDateTimeAfterDuration(flight.getDepartureDate(),
				flight.getDepartureTime(), flight.getDuration());

		adminFlightRepository.insertFlight(flight, newArrivalDateTime);

		planeService.updateAvailableQuantity(flight.getPlane().getId(), true);
	}

	public BindingResult validateFlightInsertion(Flight flight, BindingResult result) {
		Plane plane = flight.getPlane();
		Airport departureAirport = flight.getDepartureAirport();
		Airport arrivalAirport = flight.getArrivalAirport();

		if (plane == null || plane.getId() == 0)
			result.rejectValue("plane.id", "error.flight", "Trường này là bắt buộc.");
		if (departureAirport == null || departureAirport.getId() == 0)
			result.rejectValue("departureAirport.id", "error.flight", "Trường này là bắt buộc.");
		if (arrivalAirport == null || arrivalAirport.getId() == 0)
			result.rejectValue("arrivalAirport.id", "error.flight", "Trường này là bắt buộc.");

		return result;
	}

}
