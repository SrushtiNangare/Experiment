package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Customer;
import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchDishException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.service.CustomerService;
import com.capgemini.utilities.GlobalResources;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	private Logger logger = GlobalResources.getLogger(CustomerController.class);

	/*
	 * http://localhost:9090/GharKaKhana-api/customers/addCustomer
	 */	
	@PostMapping(path = "/addCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		logger.info("addCustomer() called");
		Customer result = service.registerCustomer(customer);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	
	/*
	 * http://localhost:9090/GharKaKhana-api/customers/loginCustomer/customerId/ password
	 */	 
	@PostMapping(path = "/loginCustomer/{customerId}/{password}")
	public ResponseEntity<String> login(@PathVariable("customerId") int customerId,
			@PathVariable("password") String password) throws NoSuchCustomerException {
		logger.info("login() called");
		String result = service.customerLogin(customerId, password);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 *  http://localhost:9090/GharKaKhana-api/customers/placeOrder/customerId/vendorId
	 */
	@PostMapping(path = "placeOrder/{customerId}/{vendorId}")
	public ResponseEntity<Order> placeOrder(@PathVariable("customerId") int customerId,
			@RequestBody List<FoodItem> foodItems, @PathVariable("vendorId") int vendorId) {
		logger.info("placeOrder() called");
		Order result = service.placeOrder(customerId, foodItems, vendorId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	/*
	 *  http://localhost:9090/GharKaKhana-api/customers/status/orderId
	 */	
	@GetMapping(path = "status/{orderId}")
	public ResponseEntity<String> viewOrderStatus(@PathVariable("orderId") int orderId)
			throws NoSuchOrderException {
		logger.info("viewOrderStatus() called");
		String result = service.viewOrderStatusById(orderId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	/*
	 * // http://localhost:9090/GharKaKhana-api/customers/cancel/
	 */
	@DeleteMapping(path = "/cancel/{orderId}")
	public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") int orderId) throws NoSuchOrderException {
		logger.info("cancelOrder() called");
		boolean result = service.cancelOrder(orderId);
		if (result)
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/customers/searchDish/foodName
	 */
	@GetMapping(path = "/searchDish/{foodName}")
	public ResponseEntity<List<FoodItem>> searchDishByName(@PathVariable("foodName") String foodName)
			throws NoSuchDishException {
		logger.info("searchDishByName() called");
		List<FoodItem> result = service.searchDishes(foodName);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/customers/sortDishByPrice
	 */
	@GetMapping(path = "/sortDishByPrice")
	public ResponseEntity<List<Object>> sortDishByPrice() {
		logger.info("sortDishByPrice() called");
		List<Object> result = service.viewDishesSortByPrice();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/customers/viewAllOrders/customerId
	 */
	@GetMapping(path = "/viewAllOrders/{customerId}")
	public ResponseEntity<List<Order>> getAllOrders(@PathVariable("customerId") int customerId) {
		logger.info("getAllOrder() called");
		List<Order> result = service.findAllOrder(customerId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/customers/viewAllFoodItem
	 */	
	@GetMapping(path = "/viewAllFoodItem")
	public ResponseEntity<List<Object>> viewAllFoodItem() {
		logger.info("getAllOrder() called");
		List<Object> result = service.viewMenu();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	

	/*
	 * http://localhost:9090/GharKaKhana-api/customers/findOrderById/orderId
	 */	
	@GetMapping(path = "findOrderById/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable("orderId") int orderId) throws NoSuchOrderException {
		logger.info("getOrdorById() called");
		Order result = service.findOrderById(orderId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * http://localhost:9090/GharKaKhana-api/customers/modifyOrder/orderId
	 */	
	@PostMapping(path = "modifyOrder/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Order> modifyOrder(@PathVariable("orderId") int orderId) throws NoSuchOrderException {
			logger.info("getOrdorById() called");
			Order result = service.modifyOrder(orderId);
			if (result != null)
				return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
}
