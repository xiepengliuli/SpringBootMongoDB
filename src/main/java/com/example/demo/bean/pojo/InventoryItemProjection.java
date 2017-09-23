package com.example.demo.bean.pojo;

import org.springframework.data.annotation.Id;

/**
 * 库存商品_投影类
 * 
 * @author Administrator
 *
 */
public class InventoryItemProjection {

	private @Id int id;
	private String item;
	private String description;
	private int qty;
	private int discount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}