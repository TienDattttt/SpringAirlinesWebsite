package com.example.SwipeFlight.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartup implements CommandLineRunner {

	@Autowired // dependency injection
	private DatabaseInitializer databaseInitializer;

	@Autowired // dependency injection
	private ReservationCleanupService reservationCleanupService;

	@Override
	public void run(String... args) {
		databaseInitializer.initializeDatabase();
		reservationCleanupService.scheduleCleanupTask();
	}
}
