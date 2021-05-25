package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Customer;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT c.password from Customer c Where c.customerId=:customerId")
	public String getPassword(@Param("customerId") int customerId);
	
	@Query("SELECT c.userName from Customer c WHERE c.userName=:userName")
	public String getUserName(@Param("userName") String userName);
	
	@Query("SELECT c from Customer c WHERE c.userName=:userName")
	public Customer getCustomerData(@Param("userName") String userName);
	
	@Query("SELECT c FROM Customer c")
	public List<Customer> viewAllCustomers();
}
