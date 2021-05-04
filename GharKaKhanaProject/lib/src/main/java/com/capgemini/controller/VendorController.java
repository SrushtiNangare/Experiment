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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.exceptions.NoSuchFoodItemException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;
import com.capgemini.service.VendorService;
import com.capgemini.utilities.GlobalResources;

@RestController
@RequestMapping(path = "vendors")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	private Logger logger = GlobalResources.getLogger(VendorController.class);

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/loginVendor/{vendorId}/{password}
	 */
	@PostMapping(path = "/loginVendor/{vendorId}/{password}")
	public ResponseEntity<String> loginVendor(@PathVariable("vendorId") int vendorId,
			@PathVariable("password") String password) throws NoSuchVendorException {
		logger.info("login() called");
		String result = vendorService.vendorLogin(vendorId, password);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/findFoodId/
	 */
	@GetMapping(path = "findFoodId/{foodId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FoodItem> getFoodById(@PathVariable("foodId") int foodId) throws NoSuchFoodItemException {
		logger.info("getFoodById() called");
		FoodItem result = vendorService.findFoodById(foodId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors//addFoodItem/{vendorId}
	 */
	@PostMapping(path = "/addFoodItem/{vendorId}")
	public ResponseEntity<FoodItem> addFoodItem(@RequestBody FoodItem foodItem, @PathVariable("vendorId") int vendorId) throws NoSuchVendorException {
		logger.info("addFoodItem() called");
		FoodItem result = vendorService.addFood(foodItem, vendorId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/viewAllFoodItems
	 */
	@GetMapping(path = "/viewAllFoodItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FoodItem>> getAllFoodItems() {
		logger.info("viewAllFoodItems() called");
		List<FoodItem> result = vendorService.viewAllFoodItems();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/updateFood/
	 */
	@PutMapping(path = "/updateFood", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<FoodItem> modifyFood(@RequestBody FoodItem foodItem) throws NoSuchFoodItemException {
		FoodItem result = vendorService.modifyFood(foodItem);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/deleteFoodItem/{foodId}
	 */
	@DeleteMapping(path = "/deleteFoodItem/{foodId}")
	public ResponseEntity<Boolean> deleteFoodItem(@PathVariable("foodId") int foodId) throws NoSuchFoodItemException {
		logger.info("deleteFoodItem() called");
		boolean result = vendorService.removeFood(foodId);
		if (result)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/setOrderStatus/orderId/status
	 */
	@PutMapping(path = "/setOrderStatus/{orderId}/{status}")
	public ResponseEntity<Boolean> setOrderStatus(@PathVariable("orderId") int orderId,
			@PathVariable("status") String status) throws NoSuchOrderException {
		boolean result = vendorService.setOrderStatusById(orderId, status);
		if (result)
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/setOrderPaymentStatus/orderId/
	 * status
	 */
	@PutMapping(path = "/setOrderPaymentStatus/{orderId}/{status}")
	public ResponseEntity<Boolean> setOrderPaymentStatus(@PathVariable("orderId") int orderId,
			@PathVariable("status") String status) throws NoSuchOrderException {
		boolean result = vendorService.setOrderPaymentStatus(orderId, status);
		if (result)
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/vendors/viewAllOrder/vendorId
	 */
	@GetMapping(path = "/viewAllOrder/{vendorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> viewAllOrders(@PathVariable("vendorId") int vendorId) {
		logger.info("viewAllOrders() called");
		List<Order> result = vendorService.viewAllOrder(vendorId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}