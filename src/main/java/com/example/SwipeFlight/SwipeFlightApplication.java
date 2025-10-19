package com.example.SwipeFlight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SwipeFlightApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwipeFlightApplication.class, args);
		System.out.println("==> Fly Now application is running.");
	}

}
