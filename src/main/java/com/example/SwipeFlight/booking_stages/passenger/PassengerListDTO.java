package com.example.SwipeFlight.booking_stages.passenger;

import java.io.Serializable;
import java.util.List;

import com.example.SwipeFlight.entities.passenger.Passenger;

public class PassengerListDTO implements Serializable {

	private List<Passenger> passengerList;

	public List<Passenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<Passenger> passengerList) {
		this.passengerList = passengerList;
	}
}
