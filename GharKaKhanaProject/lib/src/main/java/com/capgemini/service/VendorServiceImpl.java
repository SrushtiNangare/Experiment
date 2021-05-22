package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchFoodItemException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;
import com.capgemini.repository.FoodItemRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.VendorRepository;
import com.capgemini.utilities.GlobalResources;

/*Vendor Service Implementation*/
@Service
public class VendorServiceImpl implements VendorService {

	Logger logger = GlobalResources.getLogger(VendorServiceImpl.class);

	/* Creating reference (it creates loosely coupled application) */
	@Autowired
	/* Creating reference (it creates loosely coupled application) */
	private FoodItemRepository foodItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AdminService adminService;

	@Autowired
	private VendorRepository vendorRepository;

	/* Vendor will login using ID and Password */
	@Override
	public String vendorLogin(int vendorId, String password) throws NoSuchVendorException {
		logger.info("VendorLogin() called");
		Vendor vendor = adminService.findVendorById(vendorId);
		String pass = vendorRepository.getPassword(password);
		if (vendor.getVendorPassword().equals(pass))
			return "Login Successful";
		else
			return "Invalid UserId or Password";
	}

	@Override
	/* Add Food to Menu by accepting values */
	public FoodItem addFood(FoodItem foodItem, int vendorId) throws NoSuchVendorException {// this method should't be
																							// included
		logger.info("addFood() called");
		Vendor vendor = adminService.findVendorById(vendorId);
		foodItem.setVendor(vendor);
		return foodItemRepository.save(foodItem);
	}

	@Override
	/* Modify Food item from Menu */
	public FoodItem modifyFood(FoodItem foodItem, int vendorId) throws NoSuchFoodItemException, NoSuchVendorException {
		logger.info("modifyFood() called");
		Vendor vendor = adminService.findVendorById(vendorId);
		foodItem.setVendor(vendor);
		return foodItemRepository.save(foodItem);
	}

	@Override
	/* Delete Food from Menu */
	public boolean removeFood(int foodId) throws NoSuchFoodItemException {
		logger.info("removeFood() called");
		try {
			if (isvalidFoodId(foodId)) {
				logger.info("Valid Food Id");
				FoodItem foodItem = findFoodById(foodId); /* calling method findMenuById */
				if (foodItem != null) {
					foodItemRepository.delete(foodItem);
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchFoodItemException(
					"Food with " + foodId + " is not found"); /* Throwing and handling exception */
		}

		return true;
	}

	@Override
	/* View all the food items from the Menu */
	public List<FoodItem> viewAllFoodItems() {
		logger.info("viewAllMenu() called");
		return foodItemRepository.findAll();
	}

	@Override
	/* Find food item using foodId */
	public FoodItem findFoodById(int foodId) throws NoSuchFoodItemException {
		logger.info("findFoodById() called");
		try {
			if (isvalidFoodId(foodId)) {
				logger.info("Valid Food Id");
				Optional<FoodItem> foodItem = foodItemRepository.findById(foodId);
				if (foodItem.get() != null)
					return foodItem.get();
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchFoodItemException("FoodItem with id " + foodId + " not found.");
		}

		return null;
	}

	@Override
	/* Set Order Status */
	public boolean setOrderStatusById(int orderId, String status) throws NoSuchOrderException {
		Order order = null;
		order = adminService.findOrderById(orderId);
		order.setOrderStatus(status);
		orderRepository.save(order);
		return true;
	}

	@Override
	/* Vendor will set the order payment status */
	public boolean setOrderPaymentStatus(int orderId, String status) throws NoSuchOrderException {
		Order order1 = null;
		order1 = adminService.findOrderById(orderId);
		order1.setOrderPaymentStatus(status);
		orderRepository.save(order1);
		return true;

	}

	@Override
	/* View all Orders */
	public List<Order> viewAllOrder(int vendorId) {
		logger.info("viewOrder() called");
		return orderRepository.findAllVendorOrders(vendorId);
	}

	/* Validation for Food ID */
	public boolean isvalidFoodId(int foodId) {
		if (foodId <= 0)
			return false;
		return true;
	}

}