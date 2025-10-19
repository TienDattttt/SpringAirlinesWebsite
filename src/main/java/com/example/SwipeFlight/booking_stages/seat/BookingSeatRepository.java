package com.example.SwipeFlight.booking_stages.seat;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookingSeatRepository {

	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public void insertIntoReservedSeat(String PassportID, Long flightID, String seatNum)
			throws IllegalArgumentException {
		try {
			String sql = "INSERT INTO reserved_seats " +
					"(passport_id, flight_id, seat_num) "
					+ "VALUES (?, ?, ?); ";
			jdbcTemplate.update(sql, new Object[] { PassportID, flightID, seatNum });
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: BookingSeatRepository\t Method: insertIntoReservedSeat "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to insert into reserved seats.", e);
		}
	}

	public void deleteReservedSeatForPassAndFlight(String PassportID, Long flightID) throws IllegalArgumentException {
		try {
			String sql = "DELETE FROM reserved_seats " +
					"WHERE passport_id = ? AND flight_id = ?; ";
			jdbcTemplate.update(sql, PassportID, flightID);
		} catch (Exception e) {
			System.err.println(
					"\n*** Error ***\nClass: BookingSeatRepository\t Method: deleteReservedSeatForPassAndFlight "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to delete from reserved seats.", e);
		}
	}

	public Timestamp getReservedTimeForPassAndFlight(String PassportID, Long flightID) throws IllegalArgumentException {
		try {
			String sql = "SELECT reservation_time " +
					"FROM reserved_seats " +
					"WHERE passport_id = ? AND flight_id = ?; ";

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, PassportID, flightID);
			if (rows.isEmpty()) {
				return null;
			}
			return (Timestamp) rows.get(0).get("reservation_time");
		} catch (Exception e) {
			System.err
					.println("\n*** Error ***\nClass: BookingSeatRepository\t Method: getReservedTimeForPassAndFlight "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve reserved time.", e);
		}
	}
}
