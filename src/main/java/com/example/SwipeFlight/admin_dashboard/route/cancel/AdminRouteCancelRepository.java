package com.example.SwipeFlight.admin_dashboard.route.cancel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SwipeFlight.server.utils.Constants;

@Repository
public class AdminRouteCancelRepository {

	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public void cancelFlight(Long flightID) throws IllegalArgumentException {
		try {
			// update the flight
			String sql = "UPDATE flight " +
					" SET status_id = " + Constants.FLIGHT_STATUS_CANCELED +
					" WHERE id = ?; ";
			jdbcTemplate.update(sql, new Object[] {
					flightID
			});
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminRouteCancelRepository\t Method: cancelFlight "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to cancel flight.", e);
		}
	}

	public void deleteFromPassengerFlight(Long canceledFlightID) throws IllegalArgumentException {
		try {
			String sql = "DELETE FROM passenger_flight " + "WHERE flight_id = ?; ";
			jdbcTemplate.update(sql, new Object[] { canceledFlightID });
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AdminRouteCancelRepository\t Method: deleteFromPassengerFlight "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to delete from Passenger_Flight table.", e);
		}
	}
}
