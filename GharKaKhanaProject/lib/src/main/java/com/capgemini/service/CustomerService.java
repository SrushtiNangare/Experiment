package com.capgemini.service;

import java.util.List;

import com.capgemini.entities.Customer;
import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchDishException;
import com.capgemini.exceptions.NoSuchOrderException;

public interface CustomerService {
	public Customer registerCustomer(Customer customer);

	public String customerLogin(String userName, String password);

	public Order placeOrder(int customerId, List<FoodItem> foodItems, int vendorId);

	public String cancelOrder(int orderId, String status) throws NoSuchOrderException;

	public List<FoodItem> viewDishesSortByPrice();

	public List<FoodItem> searchDishes(String foodName) throws NoSuchDishException;

	public String viewOrderStatusById(int orderId) throws NoSuchOrderException;

	public List<FoodItem> viewMenu();

	public Order findOrderById(int orderId) throws NoSuchOrderException;

	public Order modifyOrder(int orderId, int customerId, List<FoodItem> foodItems, int vendorId)
			throws NoSuchOrderException;

	public List<Order> findAllOrder(int customerId);
	

}