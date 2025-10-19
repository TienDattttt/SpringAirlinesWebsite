package com.example.SwipeFlight.entities.airport;

import java.io.Serializable;

import com.example.SwipeFlight.entities.city.City;

public class Airport implements Serializable {
	private Long id;
	private String code;
	private String name;
	private City city;

	/* empty constructor */
	public Airport() {
	}

	/* The method returns value of id */
	public Long getId() {
		return id;
	}

	/* The method updates value of id */
	public void setId(Long id) {
		this.id = id;
	}

	/* The method returns value of code */
	public String getCode() {
		return code;
	}

	/* The method updates value of code */
	public void setCode(String code) {
		this.code = code;
	}

	/* The method returns value of name */
	public String getName() {
		return name;
	}

	/* The method updates value of name */
	public void setName(String name) {
		this.name = name;
	}

	/* The method returns value of city */
	public City getCity() {
		return city;
	}

	/* The method updates value of city */
	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Airport [id=" + id + ", code=" + code + ", name=" + name + ", city=" + city + "]";
	}

}
