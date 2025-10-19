package com.example.SwipeFlight.entities.airport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AirportRepository {
	@Autowired // dependency injection
	private JdbcTemplate jdbcTemplate;

	public List<Airport> getAllDepartureAirports() throws IllegalArgumentException {
		List<Airport> result = new ArrayList<Airport>();
		try {
			String sql = "SELECT * FROM airport ORDER BY code; ";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> row : rows) {
				Airport airport = createAirportObjectFromRow(row);
				result.add(airport);
			}
			return result;
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AirportRepository\t Method: getAllDepartureAirports "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve departure airports.", e);
		}
	}

	public List<Airport> getArrivalAirportsByDepartureAirportID(Long departureAirportID)
			throws IllegalArgumentException {
		List<Airport> result = new ArrayList<Airport>();
		try {
			String sql = "SELECT * FROM airport WHERE id <> ? ORDER BY code; ";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, departureAirportID);
			for (Map<String, Object> row : rows) {
				Airport airport = createAirportObjectFromRow(row);
				result.add(airport);
			}
			return result;
		} catch (Exception e) {
			System.err.println(
					"\n*** Error ***\nClass: AirportRepository\t Method: getArrivalAirportsByDepartureAirportID "
							+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve arrival airports.", e);
		}
	}

	public Airport getAirportByID(Long id) throws IllegalArgumentException {
		List<Airport> result = new ArrayList<Airport>();
		try {
			String sql = "SELECT * FROM airport WHERE id = ?; ";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id);
			if (rows.isEmpty()) {
				return null; // No rows found, return null
			}
			for (Map<String, Object> row : rows) {
				Airport airport = createAirportObjectFromRow(row);
				result.add(airport);
			}
			return result.get(0);
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AirportRepository\t Method: getAirportByID "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve airport by id.", e);
		}
	}

	public Long getCityIdOfAirportByID(Long id) throws IllegalArgumentException {
		try {
			String sql = "SELECT city_id FROM airport WHERE id = ?; ";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id);
			if (rows.isEmpty()) {
				return null;
			}
			return (Long) rows.get(0).get("city_id");
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: AirportRepository\t Method: getCityIdOfAirportByID "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Failed to retrieve city id for airport.", e);
		}
	}

	private Airport createAirportObjectFromRow(Map<String, Object> row) {
		Airport airport = new Airport();
		airport.setId((Long) row.get("id"));
		airport.setCode((String) row.get("code"));
		airport.setName((String) row.get("name"));
		// city will be set from service layer
		return airport;
	}
}
