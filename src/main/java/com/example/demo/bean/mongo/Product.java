package com.example.demo.bean.mongo;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Product {
	private String id;
	private String name;
	private double netPrice;
	private int spaceUnits;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}
	public int getSpaceUnits() {
		return spaceUnits;
	}
	public void setSpaceUnits(int spaceUnits) {
		this.spaceUnits = spaceUnits;
	}
	
	
}