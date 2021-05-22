package com.capgemini.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component /* it will detect our custom beans */
@Scope(scopeName = "prototype") /* keeping this as prototype */
@Entity /* Creating table */
@Table(name = "OrderTbl") /* Specifying table name */
public class Order {
	@Id /* defining primary key */
	@GeneratedValue(strategy = GenerationType.IDENTITY) /* generating value automatically */
	@Column(name = "Order_Id") /* specifying column name */
	private int orderId;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "Order_Date", nullable = false)
	private LocalDate orderDate;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "Order_Time", nullable = false)
	private LocalTime orderTime;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "Order_Status", length = 200, nullable = false)
	private String orderStatus;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "Order_Price", length = 10, nullable = false)
	private double orderPrice;

	/*
	 * specifying column name, giving length and giving constraint as not null
	 */
	@Column(name = "OrderPayment_Status", length = 50, nullable = false)
	private String orderPaymentStatus;

	public String getOrderPaymentStatus() {
		return orderPaymentStatus;
	}

	public void setOrderPaymentStatus(String orderPaymentStatus) {
		this.orderPaymentStatus = orderPaymentStatus;
	}

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "VENDOR_ID")
	private Vendor vendor;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "food_orders", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
			@JoinColumn(name = "food_id") })
	private List<FoodItem> foodItems = new ArrayList<>();

	public List<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	/* creating parameterized constructor */
	public Order(LocalDate orderDate, LocalTime orderTime, String orderStatus, double orderPrice,
			String orderPaymentStatus, Customer customer, Vendor vendor, List<FoodItem> foodItems) {
		super();
		this.orderDate = orderDate;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.orderPrice = orderPrice;
		this.orderPaymentStatus = orderPaymentStatus;
		this.customer = customer;
		this.vendor = vendor;
		this.foodItems = foodItems;
	}

	/* creating constructor */
	public Order() {

	}

	/* creating getters and setters */
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalTime orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	/* creating toString method */
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderTime=" + orderTime + ", orderStatus="
				+ orderStatus + ", orderPrice=" + orderPrice + ", orderPaymentStatus=" + orderPaymentStatus
				+ ", customer=" + customer + ", vendor=" + vendor + ", FoodItems=" + foodItems + "]";
	}

}