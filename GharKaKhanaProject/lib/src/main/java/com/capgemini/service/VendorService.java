package com.capgemini.service;

import java.util.List;

import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.exceptions.NoSuchFoodItemException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;

public interface VendorService {
	public String vendorLogin(String userName, String password);
	
	public FoodItem addFood(FoodItem foodItem,int vendorId) throws NoSuchVendorException;

	public FoodItem modifyFood(FoodItem foodItem, int vendorId) throws NoSuchFoodItemException, NoSuchVendorException;

	public boolean removeFood(int foodId) throws NoSuchFoodItemException;

	public boolean setOrderPaymentStatus(int orderId, String status) throws NoSuchOrderException;

	public List<FoodItem> viewAllFoodItems();

	public boolean setOrderStatusById(int orderId, String status) throws NoSuchOrderException;

	public List<Order> viewAllOrder(int vendorId);

	public FoodItem findFoodById(int foodId) throws NoSuchFoodItemException;
	
	public List<FoodItem> findFoodItemById(int vendorId);

}