package com.example.demo.bean.mongo;


public class ZipInfoStats {
  public String id;
  public String state;
  public City biggestCity;
  public City smallestCity;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public City getBiggestCity() {
	return biggestCity;
}
public void setBiggestCity(City biggestCity) {
	this.biggestCity = biggestCity;
}
public City getSmallestCity() {
	return smallestCity;
}
public void setSmallestCity(City smallestCity) {
	this.smallestCity = smallestCity;
}
  
}