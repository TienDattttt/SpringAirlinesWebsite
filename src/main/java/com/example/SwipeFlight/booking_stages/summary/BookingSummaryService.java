package com.example.SwipeFlight.booking_stages.summary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.passenger.Passenger;

@Service
public class BookingSummaryService {

	@Autowired // dependency injection
	private BookingSummaryRepository bookingSummaryRepository;

	public Long insertIntoBooking(List<Passenger> passengerList,
			Long routeNumber, double totalPrice, Long sessionUserID)
			throws IllegalArgumentException {
		return bookingSummaryRepository.insertIntoBooking(passengerList, routeNumber, totalPrice, sessionUserID);
	}
}
