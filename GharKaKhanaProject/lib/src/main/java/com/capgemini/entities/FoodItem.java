package com.capgemini.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Entity
@Scope(scopeName = "prototype")
@Table(name = "FoodItem")
public class FoodItem {
	/* defining primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOOD_ID") /* specifying column name */
	private int foodId;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "Food_Name", length = 25, nullable = false)
	private String foodName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "VENDOR_ID")
	private Vendor vendor;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "Food_Price", length = 25, nullable = false)
	private double foodPrice;

	@Column(name = "Food_Quantity", length = 25)
	private int foodQuantity;

	public int getFoodQuantity() {
		return foodQuantity;
	}

	public void setFoodQuantity(int foodQuantity) {
		this.foodQuantity = foodQuantity;
	}

	public FoodItem(String foodName, double foodPrice, int foodQuantity) {
		super();
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.foodQuantity = foodQuantity;
	}

	public FoodItem() {

	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}

	@Override
	public String toString() {
		return "FoodItem [foodId=" + foodId + ", foodName=" + foodName + ", vendor=" + vendor + ", foodPrice="
				+ foodPrice + "]";
	}

}
