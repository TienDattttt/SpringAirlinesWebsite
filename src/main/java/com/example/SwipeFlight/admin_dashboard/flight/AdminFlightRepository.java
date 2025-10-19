package com.example.SwipeFlight.admin_dashboard.flight;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.server.utils.Constants;

@Repository
public class AdminFlightRepository {

	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public void insertFlight(Flight flight, LocalDateTime calculatedArrivalDateTime) throws IllegalArgumentException {

		try {
			// flight properties
			Duration duration = flight.getDuration();
			Time departureTime = flight.getDepartureTime();
			Date departureDate = flight.getDepartureDate();

			// Insert the flight
			String sql = "INSERT INTO flight " +
					"(plane_id, departure_airport_id, arrival_airport_id, departure_date, departure_time, " +
					"arrival_date, arrival_time, ticket_price, duration, status_id) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql, new Object[] {
					flight.getPlane().getId(), flight.getDepartureAirport().getId(), flight.getArrivalAirport().getId(),
					departureDate, departureTime,
					Date.valueOf(calculatedArrivalDateTime.toLocalDate()),
					Time.valueOf(calculatedArrivalDateTime.toLocalTime()),
					flight.getTicketPrice(), duration.getSeconds(),
					Constants.FLIGHT_STATUS_SCHEDULED
			});

			sql = "SELECT LAST_INSERT_ID()";
			Long newFlightID = jdbcTemplate.queryForObject(sql, Long.class);

			insertNewRouteForFlightID(newFlightID);

		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminFlightRepository\t Method: insertFlight "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to insert flight.", e);
		}
	}

	private void insertNewRouteForFlightID(Long newFlightID) throws IllegalArgumentException {
		try {
			String sql = "INSERT INTO route (route_number, flight_id, sequence_number) " +
					"SELECT COALESCE(MAX(route_number), 0) + 1, ?, 1 " +
					"FROM (SELECT route_number FROM route) AS temp";
			jdbcTemplate.update(sql, newFlightID);
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminFlightRepository\t Method: insertNewRouteForFlightID "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Không thể chèn tuyến đường.", e);
		}
	}

}
