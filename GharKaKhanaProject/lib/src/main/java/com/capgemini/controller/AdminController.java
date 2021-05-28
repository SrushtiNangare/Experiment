package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Admin;
import com.capgemini.entities.Customer;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;
import com.capgemini.service.AdminService;
import com.capgemini.utilities.GlobalResources;

@CrossOrigin
@RestController
@RequestMapping(path = "admins")
public class AdminController {

	private Logger logger = GlobalResources.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	/*
	 * // http://localhost:9090/GharKaKhana-api/admins/addAdmin
	 */
	@PostMapping(path = "/addAdmin", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
		logger.info("addAdmin() method is called");
		Admin result = adminService.registerAdmin(admin);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	//http://localhost:9090/GharKaKhana-api/admins/loginAdmin/{userName}/{password}
		@PostMapping(path = "/loginAdmin/{userName}/{password}")
		public ResponseEntity<String> loginAdmin(@PathVariable("userName") String userName,@PathVariable("password") String password){
			String result = adminService.adminLogin(userName, password);
			if(result != null)
				return new ResponseEntity<>(result,HttpStatus.OK);
			else 
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/addVendor
	 */
	@PostMapping(path = "/addVendor", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
		logger.info("addVendor() method is called");
		Vendor result = adminService.addVendor(vendor);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/viewAllAdmin
	 */
	@GetMapping(path = "/viewAllAdmin")
	public ResponseEntity<List<Admin>> getAllAdmin() {
		logger.info("getAllAdmin() method is called");
		List<Admin> result = adminService.findAllAdmins();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/viewAllCustomer
	 */
	@GetMapping(path = "/viewAllCustomer")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		logger.info("getAllCustomer() method is called");
		List<Customer> result = adminService.findAllCustomer();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/viewAllVendor
	 */
	@GetMapping(path = "/viewAllVendor")
	public ResponseEntity<List<Vendor>> getAllVendor() {
		logger.info("getAllVendor() method is called");
		List<Vendor> result = adminService.findAllVendors();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/deleteVendor/{vendorId}
	 */
	@DeleteMapping(path = "deleteVendor/{vendorId}")
	public ResponseEntity<Boolean> deleteVendor(@PathVariable("vendorId") int vendorId) throws NoSuchVendorException {
		logger.info("deleteVendor() method is called");
		ResponseEntity<Boolean> response = null;
		boolean result = adminService.removeVendor(vendorId);
		if (result)
			response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/updateVendor/
	 */
	@PutMapping(path = "/updateVendor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) {
		logger.info("updateVendor() method is called");
		Vendor result = adminService.addVendor(vendor);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/viewAllOrder
	 */
	@GetMapping(path = "/viewAllOrder")
	public ResponseEntity<List<Order>> getAllOrder() {
		logger.info("getAllOrder() method is called");
		List<Order> result = adminService.findAllOrder();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/findVendorById/
	 */
	@GetMapping(path = "findVendorById/{vendorId}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable("vendorId") int vendorId) throws NoSuchVendorException {
		logger.info("getVendorById() method is called");
		Vendor result = adminService.findVendorById(vendorId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/findOrderById/
	 */
	@GetMapping(path = "findOrderById/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable("orderId") int orderId) throws NoSuchOrderException {
		logger.info("getOrdorById() method is called");
		Order result = adminService.findOrderById(orderId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/findCustomerId/
	 */
	@GetMapping(path = "findCustomerId/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId)
			throws NoSuchCustomerException {
		logger.info("getCustomerById() method is called");
		Customer result = adminService.findCustomerById(customerId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/findOrderByDate
	 */
	@GetMapping(path = "findOrderByDate")
	public ResponseEntity<List<Order>> getOrderByDate() {
		logger.info("getOrderByDate() method is called");
		List<Order> result = adminService.findSortedOrderByDate();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/findOrderByAmount
	 */
	@GetMapping(path = "findOrderByAmount")
	public ResponseEntity<List<Order>> getOrderByAmount() {
		logger.info("getOrderByAmount() method is called");
		List<Order> result = adminService.findSortedOrderByAmount();
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * http://localhost:9090/GharKaKhana-api/admins/deleteOrderById/
	 */
	@DeleteMapping(path = "deleteOrderById/{ordorId}")
	public ResponseEntity<Boolean> deleteOrderById(@PathVariable("ordorId") int orderId) throws NoSuchOrderException {
		logger.info("deleteOrderById() method is called");
		boolean result = adminService.removeOrderByAdmin(orderId);
		if (result)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}