package com.example.demo.bean.mongo;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
@Entity
public class User {
	private String id;
	private String name;

	private Integer age;
	private Date birthDay;


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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", birthDay=" + birthDay + "]";
	}

}
