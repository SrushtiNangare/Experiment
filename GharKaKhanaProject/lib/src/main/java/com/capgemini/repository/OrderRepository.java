package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>  {

	@Query("SELECT o FROM Order o ORDER BY o.orderDate ASC")
	public List<Order> getOrderBySortedDate();
	
	@Query("SELECT o FROM Order o ORDER BY o.orderPrice ASC")
	public List<Order> getOrderBySortedAmount();

	@Query("SELECT o FROM Order o WHERE o.orderId=:orderId")
	public Order findByOrderId(@Param("orderId")int orderId);
	
	@Query("SELECT o FROM Order o WHERE o.customer.customerId=:customerId")
	public List<Order> findAllCustomerOrders(@Param("customerId")int customerId);
	
	@Query("SELECT o FROM Order o WHERE o.vendor.vendorId=:vendorId")
	public List<Order> findAllVendorOrders(@Param("vendorId")int vendorId);
	
	@Query("SELECT o.orderStatus FROM Order o WHERE o.orderId=:orderId")
	public String getOrderStatusById(@Param("orderId")int orderId);
	
	@Modifying
	@Query("UPDATE Order o SET o.customer.customerId = NULL WHERE o.orderId=:orderId")
	public void setCustomerIdNull(@Param("orderId")int orderId);
	
	@Modifying
	@Query("UPDATE Order o SET o.vendor.vendorId = NULL WHERE o.orderId=:orderId")
	public void setVendorIdNull(@Param("orderId")int orderId);
}
