package com.example.SwipeFlight.booking_stages.passenger;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.SwipeFlight.entities.passenger.Passenger;

@Service
public class BookingPassengerService {

	@Autowired // dependency injection
	private BookingPassengerRepository bookingPassengerRepository;

	public void insertPassenger(Passenger passenger, Long bookingID) {

		bookingPassengerRepository.insertIntoPassenger(passenger, bookingID);

		String passportId = passenger.getPassportID();
		Map<Long, String> flightAndSeats = passenger.getFlightAndSeats();
		Map<Long, List<String>> flightAndLuggage = passenger.getFlightAndLuggage();

		// iterate through each flight (key of the map)
		for (Map.Entry<Long, String> entry : flightAndSeats.entrySet()) {
			Long flightId = entry.getKey();

			// step (1)
			String seatStr = entry.getValue();

			// step (2)
			List<String> luggageIds = flightAndLuggage.getOrDefault(flightId, List.of());
			String luggageIdsStr = String.join(",", luggageIds);

			// step (3)
			bookingPassengerRepository.insertIntoPassengerFlight(bookingID, passportId, flightId, seatStr,
					luggageIdsStr);
		}
	}

	public boolean isPassengerBookedToRouteNum(String passportID, Long routeNum) {
		return bookingPassengerRepository.isPassengerBookedToRouteNum(passportID, routeNum);
	}

	public BindingResult validatePassengers(List<Passenger> passengerList, Long routeNumber,
			BindingResult result) {
		// iterate through each passenger in the given list
		for (int i = 0; i < passengerList.size(); i++) {
			Passenger passenger = passengerList.get(i);

			// first name
			if (passenger.getFirstName().isEmpty()) {
				result.rejectValue("passengerList[" + i + "].firstName", "error.passengerList",
						"Tên không được để trống");
			} else {
				String regex = "^[a-zA-Z]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(passenger.getFirstName());
				if (!matcher.matches())
					result.rejectValue("passengerList[" + i + "].firstName", "error.passengerList",
							"Tên chỉ được bao gồm các chữ cái");
			}

			// last name
			if (passenger.getLastName().isEmpty()) {
				result.rejectValue("passengerList[" + i + "].lastName", "error.passengerList",
						"Tên không được để trống");
			} else {
				String regex = "^[a-zA-Z]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(passenger.getLastName());
				if (!matcher.matches())
					result.rejectValue("passengerList[" + i + "].lastName", "error.passengerList",
							"Tên chỉ được bao gồm các chữ cái");
			}

			// gender
			if (passenger.getGender().equals("0")) {
				result.rejectValue("passengerList[" + i + "].gender", "error.passengerList", "Tên không được để trống");
			}

			// passportID
			if (passenger.getPassportID().isEmpty()) {
				result.rejectValue("passengerList[" + i + "].passportID", "error.passengerList",
						"Tên không được để trống");
			} else if (passenger.getPassportID().length() != 9) {
				result.rejectValue("passengerList[" + i + "].passportID", "error.passengerList",
						"phải bao gồm 9 ký tự");
			} else {
				String regex = "^[a-zA-Z0-9]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(passenger.getPassportID());
				if (!matcher.matches())
					result.rejectValue("passengerList[" + i + "].passportID", "error.passengerList",
							"trường bao gồm các ký tự không hợp lệ.");
				else {
					// there are no equal passport ids in this booking
					for (int j = 0; j < passengerList.size(); j++) {
						if (i != j && passengerList.get(i).getPassportID().equals(passengerList.get(j).getPassportID()))
							result.rejectValue("passengerList[" + i + "].passportID", "error.passengerList",
									"ID hộ chiếu phải là duy nhất");
					}

					// the passport id has not booked before, for the requested route
					if (bookingPassengerRepository.isPassengerBookedToRouteNum(passenger.getPassportID(), routeNumber))
						result.rejectValue("passengerList[" + i + "].passportID", "error.passengerList",
								"ID hộ chiếu đã được đặt cho chuyến bay này");
				}
			}
		}

		return result;
	}

}
