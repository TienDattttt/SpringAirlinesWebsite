package com.example.SwipeFlight.admin_dashboard.route.add;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SwipeFlight.entities.flight.Flight;
import com.example.SwipeFlight.entities.route.Route;
import com.example.SwipeFlight.server.utils.Constants;

@Repository
public class AdminRouteAddFlightRepository {

	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public Long retrieveNextSequenceNumber(Long routeNumber) throws IllegalArgumentException {
		try {
			String sql = "SELECT MAX(sequence_number) " +
					"FROM route " +
					"WHERE route_number = ?; ";
			Long nextSequenceNumber = jdbcTemplate.queryForObject(sql, Long.class, routeNumber) + 1;
			return nextSequenceNumber;
		} catch (Exception e) {
			System.err.println(
					"\n*** Error ***\nClass: AdminRouteAddFlightRepository\t Method: retrieveNextSequenceNumber "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve next sequence number.", e);
		}
	}

	public void breakDirectRouteForFlight(Long flightID) throws IllegalArgumentException {
		try {
			String sql = "DELETE FROM route " +
					"WHERE flight_id = ? AND sequence_number = 1;";
			jdbcTemplate.update(sql, new Object[] { flightID });
		} catch (Exception e) {
			System.err.println(
					"\n*** Error ***\nClass: AdminRouteAddFlightRepository\t Method: breakDirectRouteForFlight "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to delete entry from Route.", e);
		}
	}

	public void addFlightToSequence(Long routeNumber, Long flightIDToAdd, Long nextSequenceNumber)
			throws IllegalArgumentException {
		try {
			String sql = "INSERT INTO route " +
					"(route_number, flight_id, sequence_number) "
					+ "VALUES (?, ?, ?); ";
			jdbcTemplate.update(sql, new Object[] {
					routeNumber, flightIDToAdd, nextSequenceNumber });
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminRouteAddFlightRepository\t Method: addFlightToSequence "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to add a flight to route.", e);
		}
	}

	public List<Flight> getSuggestedFlightsToAdd(Route route) throws IllegalArgumentException {
		int totalSeatsAmount = route.getFlights().get(0).getPlane().getNumOfRows()
				* route.getFlights().get(0).getPlane().getNumOfSeatsPerRow();

		try {
			Flight prevFlight = route.getFlights().get(0);
			LocalDateTime dt = LocalDateTime.of(prevFlight.getArrivalDate().toLocalDate(),
					prevFlight.getArrivalTime().toLocalTime());
			String formattedPrevFlightArrival = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			String sql = "SELECT * " +
					"FROM flight f " +
					"JOIN plane p ON f.plane_id = p.id " +
					"WHERE f.status_id != " + Constants.FLIGHT_STATUS_CANCELED + // condition (1)
					" AND f.id IN ( " + // condition (2)
					"SELECT flight_id " +
					"FROM route " +
					"WHERE is_booking_allowed = false " + // condition (3)
					"GROUP BY flight_id " +
					"HAVING COUNT(*) = 1 " +
					")  " +
					" AND f.id NOT IN ( " +
					"SELECT flight_id " +
					"FROM route " +
					"WHERE route_number IN ( " +
					"SELECT route_number " +
					"FROM route " +
					"GROUP BY route_number " +
					"HAVING COUNT(*) > 1 " +
					") " +
					") " +
					" AND f.id NOT IN ( " + // condition (4)
					"SELECT flight_id " +
					"FROM route " +
					"WHERE route_number = " + route.getRouteNumber() +
					") " +
					" AND p.num_of_rows * p.num_of_seats_per_row = " + totalSeatsAmount + " " + // condition 9
					" AND f.departure_airport_id = " + route.getFlights().get(0).getArrivalAirport().getId() + " " + // condition
																														// (5)
					" AND f.arrival_airport_id != " + route.getFlights().get(0).getDepartureAirport().getId() + " " + // condition
																														// (6)
					" AND TIMESTAMP(f.departure_date, f.departure_time) > " + "'" + formattedPrevFlightArrival + "'"
					+ " " + // condition (7)
					" AND ABS(TIMESTAMPDIFF(HOUR, TIMESTAMP(f.departure_date, f.departure_time)," + // condition (8)
					"'" + formattedPrevFlightArrival + "'" + ")) <= 8 " +
					";";

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			List<Flight> result = new ArrayList<Flight>();
			for (Map<String, Object> row : rows) {
				Flight flight = createFlightObjectFromRow(row);
				result.add(flight);
			}
			return result;
		} catch (Exception e) {
			System.err
					.println("\n*** Error ***\nClass: AdminRouteAddFlightRepository\t Method: getSuggestedFlightsToAdd "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve suggested flights.", e);
		}
	}

	private Flight createFlightObjectFromRow(Map<String, Object> row) {
		Flight flight = new Flight();
		flight.setId((Long) row.get("id"));

		flight.setDepartureDate((Date) row.get("departure_date"));
		flight.setDepartureTime((Time) row.get("departure_time"));
		flight.setArrivalDate((Date) row.get("arrival_date"));
		flight.setArrivalTime((Time) row.get("arrival_time"));
		flight.setTicketPrice((double) row.get("ticket_price"));
		flight.setDuration((Duration) Duration.ofSeconds((long) row.get("duration")));
		flight.setStatusID((Long) row.get("status_id"));
		return flight;
	}

}
