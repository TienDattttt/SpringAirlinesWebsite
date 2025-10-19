package com.example.SwipeFlight.entities.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.entities.country.Country;
import com.example.SwipeFlight.entities.country.CountryService;

@Service
public class CityService {

	// dependency injections
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryService countryService;

	public void updateCity(City city, String filePath) throws IllegalArgumentException {
		cityRepository.updateCity(city, filePath);
	}

	public City getCityByID(Long id) throws IllegalArgumentException {
		City result = cityRepository.getCityByID(id);
		setCountryOfCity(result);
		return result;
	}

	public List<String> getCityNamesByKeyboard(String keyboard) throws IllegalArgumentException {
		return cityRepository.getCityNamesByKeyboard(keyboard);
	}

	public List<City> getCitiesByKeyboard(String keyboard) throws IllegalArgumentException {
		List<City> result = cityRepository.getCitiesByKeyboard(keyboard);
		for (City city : result)
			setCountryOfCity(city);
		return result;
	}

	public Long getCountryIdOfCityByID(Long id) throws IllegalArgumentException {
		return cityRepository.getCountryIdOfCityByID(id);
	}

	private void setCountryOfCity(City city) throws IllegalArgumentException {
		Long countryID = cityRepository.getCountryIdOfCityByID(city.getId());
		Country country = (Country) countryService.getCountryByID(countryID);
		city.setCountry(country);
	}
}
