package com.example.demo.bean.mongo;
import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.annotation.Id;

/**
 * 库存商品类
 * 
 * @author Administrator
 *
 */

@Entity(value = "inventory")//value注解没起作用
public class InventoryItem {

	private @Id int id;
	private String item;
	private String description;
	private int qty;

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

}