package com.example.demo.bean.mongo;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class TagCount {
	public String id;
	public List<String> tag;
	public String name;
	public String address;
	public int n;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public List<String> getTag() {
		return tag;
	}

	public void setTag(List<String> tag) {
		this.tag = tag;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

}