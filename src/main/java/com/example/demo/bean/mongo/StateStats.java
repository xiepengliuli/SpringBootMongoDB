package com.example.demo.bean.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class StateStats {
  private @Id String id;
  private  String state;
  private @Field("totalPop") int totalPopulation;
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
public int getTotalPopulation() {
	return totalPopulation;
}
public void setTotalPopulation(int totalPopulation) {
	this.totalPopulation = totalPopulation;
}
  
  
}