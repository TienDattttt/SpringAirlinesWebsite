package com.example.SwipeFlight.server;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationCleanupService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void scheduleCleanupTask() {
		try {

			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

			executorService.scheduleAtFixedRate(this::cleanupExpiredReservations, 0, 30, TimeUnit.SECONDS);
			System.out.println("==> Scheduled task for expired reservations executed successfully.");
		} catch (RejectedExecutionException e) {
			System.err.println("*** Error -- Execute -- task cannot be scheduled for execution.");
		} catch (NullPointerException e) {
			System.err.println("*** Error -- Execute -- " + e.getMessage());
		} catch (Exception e) {
			System.err.println("*** Error -- Execute -- execute error: " + e.getMessage());
		}
	}

	public void cleanupExpiredReservations() {
		try {
			String sql = "DELETE FROM reserved_seats WHERE reservation_time < ?";

			LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
			jdbcTemplate.update(sql, fiveMinutesAgo);
		} catch (Exception e) {
			System.err.println("*** Error -- SQL -- " + e.getMessage());
		}
	}
}
