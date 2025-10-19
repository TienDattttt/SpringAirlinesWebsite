package com.example.SwipeFlight.booking_stages.passenger;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SwipeFlight.entities.passenger.Passenger;

@Repository
public class BookingPassengerRepository {

	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public void insertIntoPassenger(Passenger passenger, Long bookingID) throws IllegalArgumentException {
		try {
			// get passenger's fields
			// (excluding flightAndSeats and flightAndLuggage which relate to
			// Passenger_Flight table)
			String passportId = passenger.getPassportID();
			String firstName = passenger.getFirstName();
			String lastName = passenger.getLastName();
			String gender = passenger.getGender();

			// insert an entry
			String sql = "INSERT INTO passenger " +
					"(booking_id, passport_id, first_name, last_name, gender) " +
					"VALUES (?, ?, ?, ?, ?); ";
			jdbcTemplate.update(sql, new Object[] { bookingID, passportId, firstName, lastName, gender });
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerRepository\t Method: insertIntoPassenger "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to insert into Passenger table.", e);
		}
	}

	public void insertIntoPassengerFlight(Long bookingID, String passportId,
			Long flightId, String seatStr, String luggageIdsStr) throws IllegalArgumentException {
		try {
			String sql = "INSERT INTO passenger_flight " +
					"(booking_id, passport_id, flight_id, seat_num, luggage_ids) " +
					"VALUES (?, ?, ?, ?, ?); ";
			jdbcTemplate.update(sql, new Object[] { bookingID, passportId, flightId, seatStr, luggageIdsStr });
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: BookingPassengerRepository\t Method: insertIntoPassengerFlight "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to insert into Passenger_Flight table.", e);
		}
	}

	public boolean isPassengerBookedToRouteNum(String passportID, Long routeNum) throws IllegalArgumentException {
		int count_result = 0;
		try {
			String sql = "SELECT COUNT(*) AS passenger_exists " +
					"FROM passenger_flight pf " +
					"JOIN route r ON pf.flight_id = r.flight_id " +
					"WHERE pf.passport_id = '" + passportID + "' " +
					"AND r.route_number = ?; ";

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, routeNum);
			Long countResultLong = (Long) rows.get(0).get("passenger_exists");
			count_result = countResultLong.intValue();
		} catch (Exception e) {
			System.err
					.println("\n*** Error ***\nClass: BookingPassengerRepository\t Method: isPassengerBookedToRouteNum "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed counting from Passenger_Flight table.", e);
		}
		if (count_result == 0)
			return false;
		return true;
	}

}
