package com.capgemini.service;

import java.util.List;

import com.capgemini.entities.Admin;
import com.capgemini.entities.Customer;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;

public interface AdminService {
	public Vendor addVendor(Vendor vendor); // done
	
	public String adminLogin(int adminId, String password) throws NoSuchAdminException ;//done

	public boolean removeVendor(int vendorId) throws NoSuchVendorException; // done

	public Vendor modifyVendor(Vendor vendor) throws NoSuchVendorException; // done

	public List<Order> findSortedOrderByAmount();// done

	public List<Order> findSortedOrderByDate();// done

	public List<Order> findAllOrder(); // done

	public Customer findCustomerById(int customerId) throws NoSuchCustomerException; // done

	public Vendor findVendorById(int vendorId) throws NoSuchVendorException; // done

	public boolean removeOrderByAdmin(int orderId) throws NoSuchOrderException;// done

	public Order findOrderById(int orderId) throws NoSuchOrderException; // done

	public Admin findAdminById(int adminId) throws NoSuchAdminException;

	public Admin registerAdmin(Admin admin); // done

	public List<Admin> findAllAdmins(); // done

	public List<Object> findAllVendors(); // done

	public List<Object> findAllCustomer();// done
}