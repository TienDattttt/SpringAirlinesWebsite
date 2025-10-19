package com.example.SwipeFlight.admin_dashboard.route.modify;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SwipeFlight.entities.flight.Flight;

@Repository
public class AdminRouteModifyRepository {

	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public Long getRouteNumberByFlightID(Long flightID) throws IllegalArgumentException {
		try {

			String sql = "SELECT route_number FROM route WHERE flight_id = ?;";
			Long routeNumber = jdbcTemplate.queryForObject(sql, Long.class, flightID);
			return routeNumber;
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminRouteModifyRepository\t Method: getRouteNumberByFlightID "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve route number for flight id.", e);
		}
	}

	public void updateFlight(Flight flight, Date newDepartureDate, Time newDepartureTime,
			LocalDateTime calculatedArrivalDateTime, int status, Long newPlaneID)
			throws IllegalArgumentException {
		try {
			// update the flight
			String sql = "UPDATE flight " +
					"SET departure_date = ?, departure_time = ?, " +
					"arrival_date = ?, arrival_time = ?, status_ID = ?, plane_id = ? " +
					"WHERE id = ?; ";

			jdbcTemplate.update(sql, new Object[] {
					newDepartureDate, newDepartureTime,
					Date.valueOf(calculatedArrivalDateTime.toLocalDate()),
					Time.valueOf(calculatedArrivalDateTime.toLocalTime()), status, newPlaneID,
					flight.getId()
			});
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminRouteModifyRepository\t Method: updateFlight "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Không cập nhật được chuyến bay.", e);
		}
	}

}
