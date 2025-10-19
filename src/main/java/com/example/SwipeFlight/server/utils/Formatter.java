package com.example.SwipeFlight.server.utils;

import java.time.Duration;

public class Formatter {

	public static String formatDuration(Duration duration) throws IllegalArgumentException {
		try {
			long hours = duration.toHours();
			long minutes = duration.toMinutes() % 60;

			return String.format("%02d:%02d", hours, minutes);
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: Formatter\t Method: formatDuration "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Formatting duration Failed.", e);
		}
	}
}
