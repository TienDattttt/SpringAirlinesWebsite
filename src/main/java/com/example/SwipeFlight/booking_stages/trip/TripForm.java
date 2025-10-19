package com.example.SwipeFlight.booking_stages.trip;

import java.io.Serializable;
import java.sql.Date;

public class TripForm implements Serializable {

	private Long departureAirportID;
	private Long arrivalAirportID;
	private Date departureDate;
	private int numOfPassengers;

	public Long getDepartureAirportID() {
		return departureAirportID;
	}

	public void setDepartureAirportID(Long departureAirportID) {
		this.departureAirportID = departureAirportID;
	}

	public Long getArrivalAirportID() {
		return arrivalAirportID;
	}

	public void setArrivalAirportID(Long arrivalAirportID) {
		this.arrivalAirportID = arrivalAirportID;
	}

	/* The method returns value of departureDate */
	public Date getDepartureDate() {
		return departureDate;
	}

	/* The method updates value of departureDate */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	/* The method returns value of numOfPassengers */
	public int getNumOfPassengers() {
		return numOfPassengers;
	}

	/* The method updates value of numOfPassengers */
	public void setNumOfPassengers(int numOfPassengers) {
		this.numOfPassengers = numOfPassengers;
	}
}
