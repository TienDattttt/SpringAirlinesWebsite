package com.example.SwipeFlight.entities.airport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.city.City;
import com.example.SwipeFlight.entities.city.CityService;

@Service
public class AirportService {

	// dependency injections
	@Autowired
	private AirportRepository airportRepository;
	@Autowired
	private CityService cityService; // to set the city attribute for each airport

	public List<Airport> getAllDepartureAirports() throws IllegalArgumentException {
		List<Airport> result = airportRepository.getAllDepartureAirports();
		for (Airport airport : result)
			setCityOfAirport(airport);
		return result;
	}

	public List<Airport> getArrivalAirportsByDepartureAirportID(Long departureCityID) throws IllegalArgumentException {
		List<Airport> result = airportRepository.getArrivalAirportsByDepartureAirportID(departureCityID);
		for (Airport airport : result)
			setCityOfAirport(airport);
		return result;
	}

	public Airport getAirportByID(Long id) throws IllegalArgumentException {
		Airport result = airportRepository.getAirportByID(id);
		setCityOfAirport(result);
		return result;
	}

	public Long getCityIdOfAirportByID(Long id) throws IllegalArgumentException {
		return airportRepository.getCityIdOfAirportByID(id);
	}

	private void setCityOfAirport(Airport airport) {
		Long cityID = airportRepository.getCityIdOfAirportByID(airport.getId());
		City city = (City) cityService.getCityByID(cityID);
		airport.setCity(city);
	}
}