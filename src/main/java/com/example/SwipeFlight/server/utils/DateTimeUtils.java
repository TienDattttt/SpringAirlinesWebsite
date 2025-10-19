package com.example.SwipeFlight.server.utils;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtils {

	public static LocalDateTime calculateDateTimeAfterDuration(Date startDate, Time startTime, Duration duration)
			throws IllegalArgumentException {
		try {
			LocalDateTime dateTimeAfterDuration = LocalDateTime.of(startDate.toLocalDate(), startTime.toLocalTime());
			return dateTimeAfterDuration.plus(duration);
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: DateTimeUtils\t Method: calculateDateTimeAfterDuration "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Calculation of localDateTime Failed.", e);
		}
	}

	public static boolean isNowBeforeGivenDateTimeWithOffset(Date date, Time time, int offsetDays)
			throws IllegalArgumentException {
		try {
			LocalDate localDate = date.toLocalDate();
			LocalDateTime localDateTime = localDate.atTime(time.toLocalTime());
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime offsetDateTime = now.plusDays(offsetDays);

			return now.isBefore(localDateTime) && offsetDateTime.isBefore(localDateTime);
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: DateTimeUtils\t Method: isNowBeforeGivenDateTimeWithOffset "
					+ "\nDetails: " + e.getMessage());
			throw new IllegalArgumentException("Dates Comparisation Failed.", e);
		}
	}

	public static long calculateTimestamp(Date date, Time time) throws IllegalArgumentException {
		try {

			java.util.Date combinedDateTime = new java.util.Date(date.getTime() + time.getTime());
			return combinedDateTime.getTime();
		} catch (Exception e) {
			System.err.println("\n*** Error ***\nClass: DateTimeUtils\t Method: calculateTimestamp " + "\nDetails: "
					+ e.getMessage());
			throw new IllegalArgumentException("Timestamp calculation Failed.", e);
		}
	}

}
