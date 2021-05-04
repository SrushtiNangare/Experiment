package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Customer;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT c.password from Customer c Where c.password=:password")
	public String getPassword(@Param("password") String password);
	
	@Query("SELECT "
			+ "c.customerId, "
			+ "c.firstName, "
			+ "c.lastName, "
			+ "c.contactNo, "
			+ "c.emailId, "
			+ "c.customerAddress FROM Customer c")
	public List<Object> viewAllCustomers();
}
