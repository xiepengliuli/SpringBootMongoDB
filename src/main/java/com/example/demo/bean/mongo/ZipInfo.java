package com.example.demo.bean.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Field;

@Entity
public class ZipInfo {
	public String id;
	public String city;
	public String state;
	public @Field("pop") int population;
	public @Field("loc") double[] location;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}

}