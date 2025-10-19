package com.example.SwipeFlight.booking_stages.trip;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class BookingTripService {

	public BindingResult validateTripForm(TripForm tripForm, BindingResult result) {
		Long departureAirportID = tripForm.getDepartureAirportID();
		Long arrivalAirportID = tripForm.getArrivalAirportID();
		int numOfPassengers = tripForm.getNumOfPassengers();

		if (numOfPassengers == 0)
			result.rejectValue("numOfPassengers", "error.tripForm", "Trường này là bắt buộc.");
		if (departureAirportID == null || departureAirportID == 0)
			result.rejectValue("departureAirportID", "error.tripForm", "Trường này là bắt buộc.");
		if (arrivalAirportID == null || arrivalAirportID == 0)
			result.rejectValue("arrivalAirportID", "error.tripForm", "Trường này là bắt buộc.");
		if (tripForm.getDepartureDate() == null)
			result.rejectValue("departureDate", "error.tripForm", "Trường này là bắt buộc.");

		return result;
	}
}
