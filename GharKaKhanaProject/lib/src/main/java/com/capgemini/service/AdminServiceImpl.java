package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.entities.Admin;
import com.capgemini.entities.Customer;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;
import com.capgemini.repository.AdminRepository;
import com.capgemini.repository.CustomerRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.VendorRepository;
import com.capgemini.utilities.GlobalResources;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	/* Creating reference (it creates loosely coupled application) */
	private CustomerRepository customerRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AdminService adminService;

	private Logger logger = GlobalResources.getLogger(AdminServiceImpl.class);

	@Override
	/* Register Admin with their details */
	public Admin registerAdmin(Admin admin) {
		logger.info("registerAdmin() called");
		Admin result = null;
		if (isValidAdmin(admin)) {
			logger.info("Valid Admin");
			result = adminRepository.save(admin);
		}
		return result;
	}

	/* Admin will login using ID and Password */
	@Override
	public String adminLogin(int adminId, String password) throws NoSuchAdminException {
		logger.info("AdminLogin() called");
		Admin admin = adminService.findAdminById(adminId);
		String pass = adminRepository.getPassword(password);
		if (admin.getAdminPassword().equals(pass))
			return "Login Successful";
		else
			return "Invalid UserId or Password";
	}

	@Override
	/* Find All Registered Admins with their details */
	public List<Admin> findAllAdmins() {
		logger.info("findAllAdmins() called");
		return adminRepository.findAll();
	}

	/* Find Admin using Admin ID */
	@Override
	public Admin findAdminById(int adminId) throws NoSuchAdminException {
		logger.info("findAdminById() called");
		try {
			Optional<Admin> admin = adminRepository.findById(adminId); /* selecting vendor by id */
			if (admin.get() != null) {
				return admin.get();
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchAdminException(
					"Admin with " + adminId + " is not found"); /* Throwing and handling exception */
		}
		return null;
	}

	@Override
	/* Adds vendor by accepting vendor object */
	public Vendor addVendor(Vendor vendor) {
		logger.info("addVendor() called");
		Vendor result = null;
		if (isValidVendor(vendor)) {
			result = vendorRepository.save(vendor); /* inserting record in vendor table */
		}
		return result;
	}

	/* View All Vendors */
	@Override
	public List<Object> findAllVendors() {
		logger.info("findAllVendors() called");
		return vendorRepository.viewAllVendors();
	}

	/* Find Vendor Using VendorId */
	@Override
	public Vendor findVendorById(int vendorId) throws NoSuchVendorException {
		logger.info("findVendorById() called");
		try {
			if (isvalidVendorId(vendorId)) {
				logger.info("Valid vendor Id");
				Optional<Vendor> vendor = vendorRepository.findById(vendorId); /* selecting vendor by id */
				if (vendor.get() != null) {
					return vendor.get();
				}
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchVendorException(
					"Vendor with " + vendorId + " is not found"); /* Throwing and handling exception */
		}

		return null;
	}

	@Override
	/* Updates vendor by accepting vendor id */
	public Vendor modifyVendor(Vendor vendor) throws NoSuchVendorException {
		logger.info("modifyVendor() called");
		Vendor result = null;
		if (isValidVendor(vendor))
			result = vendorRepository.save(vendor);
		return result;
	}

	@Override
	/* Deletes vendor by accepting Vendor Id */
	public boolean removeVendor(int vendorId) throws NoSuchVendorException {
		logger.info("removeVendor() called");
		try {
			if (isvalidVendorId(vendorId)) {
				Vendor vendor = findVendorById(vendorId); /* calling method findVendorById */
				if (vendor != null) {
					vendorRepository.delete(vendor);
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchVendorException(
					"Vendor with " + vendorId + " is not found"); /* Throwing and handling exception */
		}

		return true;
	}

	/* View All customers */
	@Override
	public List<Object> findAllCustomer() {
		logger.info("findAllCustomer() called");
		return customerRepository.viewAllCustomers();
	}

	/* Find Customer Using customer ID */
	@Override
	public Customer findCustomerById(int customerId) throws NoSuchCustomerException {
		logger.info("findCustomerById() called");
		try {
			if (isvalidCustomerId(customerId)) {
				logger.info("valid customer id");
				Optional<Customer> customer = customerRepository.findById(customerId); /* selecting customer by id */
				if (customer.get() != null) {
					return customer.get();
				}
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchCustomerException(
					"Customer with " + customerId + " is not found"); /* Throwing and handling exception */
		}

		return null;
	}

	@Override
	/* View All Orders */
	public List<Order> findAllOrder() {
		logger.info("findAllOrder() called");
		return orderRepository.findAll();
	}

	@Override
	/* View Order sorted by Price */
	public List<Order> findSortedOrderByAmount() {
		logger.info("findSortedOrderByAmount() called");
		return orderRepository.getOrderBySortedAmount();
	}

	@Override
	/* View Order sorted by Date */
	public List<Order> findSortedOrderByDate() {
		logger.info("findSortedOrderByDate() called");
		return orderRepository.getOrderBySortedDate();
	}

	/* FInd Order using OrderID */
	@Override
	public Order findOrderById(int orderId) throws NoSuchOrderException {
		logger.info("findOrderById() called");
		try {
			if (isvalidOrderId(orderId)) {
				logger.info("Valid Order Id");
				Optional<Order> order = orderRepository.findById(orderId); /* selecting order by id */
				if (order.get() != null) {
					return order.get();
				}
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderException(
					"Order with " + orderId + " is not found"); /* Throwing and handling exception */
		}

		return null;
	}

	/* Remove Order Using Order ID */
	@Override
	public boolean removeOrderByAdmin(int orderId) throws NoSuchOrderException {
		logger.info("removeOrderByAdmin() called");
		try {
			if (isvalidOrderId(orderId)) {
				logger.info("Valid Order Id");
				orderRepository.setCustomerIdNull(orderId);
				orderRepository.setVendorIdNull(orderId);
				Order order = findOrderById(orderId);/* calling method findOrderById */
				orderRepository.save(order);
				orderRepository.deleteById(orderId);
				return true;
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderException(
					"Order with " + orderId + " is not found"); /* Throwing and handling exception */
		}

		return true;
	}

	/* Validate Admin */
	private boolean isValidAdmin(Admin admin) {
		logger.info("isValidAdmin() called");
		if (!admin.getAdminName().matches("[A-Za-z]+ [A-Za-z]+"))
			return false;
		else if (!admin.getAdminUsername().matches("[A-Za-z]+"))
			return false;
		else if (!admin.getAdminPassword().matches("[A-Za-z]+[@#$%&]"))
			return false;
		return true;
	}

	/* Validate Vendor */
	public boolean isValidVendor(Vendor vendor) {
		logger.info("isValidVendor() called");
		boolean flag = true;
		String s = Long.toString(vendor.getVendorContact());
		if (!vendor.getVendorName().matches("[A-Za-z]+ [A-Za-z]+"))
			flag = false;
		else if (!vendor.getVendorUsername().matches("[A-Za-z]+"))
			flag = false;
		else if (!vendor.getVendorPassword().matches("[A-Za-z]+[@#$%&]"))
			flag = false;
		else if (!s.matches("\\d{10}"))
			flag = false;
		return flag;
	}

	/* Validate Order ID */
	public boolean isvalidOrderId(int orderId) {
		logger.info("isvalidOrderId() called");
		if (orderId <= 0)
			return false;
		else
			return true;
	}

	/* Validate Vendor ID */
	public boolean isvalidVendorId(int vendorId) {
		logger.info("isvalidVendorId called");
		if (vendorId <= 0)
			return false;
		else
			return true;
	}

	/* Validate Customer ID */
	public boolean isvalidCustomerId(int customerId) {
		logger.info("isvalidCustomerId called");
		if (customerId <= 0)
			return false;
		else
			return true;
	}

}