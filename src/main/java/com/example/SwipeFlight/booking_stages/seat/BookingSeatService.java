package com.example.SwipeFlight.booking_stages.seat;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.passenger.Passenger;
import com.example.SwipeFlight.entities.seat.SeatService;

@Service
public class BookingSeatService {

	// dependency injections
	@Autowired
	private BookingSeatRepository bookingSeatRepository;
	@Autowired
	private SeatService seatService;

	public void deleteReservedSeatForPassAndFlight(String PassportID, Long flightID) throws IllegalArgumentException {
		bookingSeatRepository.deleteReservedSeatForPassAndFlight(PassportID, flightID);
	}

	public Timestamp getReservedTimeForPassAndFlight(String PassportID, Long flightID) throws IllegalArgumentException {
		return bookingSeatRepository.getReservedTimeForPassAndFlight(PassportID, flightID);
	}

	public boolean reserveSelectedSeats(List<Passenger> passengerList, List<Flight> flightList,
			String[] selectedSeatArray) {
		int seatIndex = 0;
		for (Passenger passenger : passengerList) {
			for (Flight flight : flightList) {
				List<String> unavailableSeats = seatService.getUnavailableSeatsForFlightID(flight.getId());
				boolean isSeatAvailable = !(unavailableSeats.contains(selectedSeatArray[seatIndex]));
				if (isSeatAvailable)
					passenger.getFlightAndSeats().put(flight.getId(), selectedSeatArray[seatIndex]);
				else // not legal
					return false;

				// insert seat into Reserved_Seats table
				bookingSeatRepository.insertIntoReservedSeat(passenger.getPassportID(), flight.getId(),
						selectedSeatArray[seatIndex]);
				seatIndex++;
			}
		}
		return true; // legal
	}

}
