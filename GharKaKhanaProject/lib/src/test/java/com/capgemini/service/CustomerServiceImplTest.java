package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.entities.Customer;
import com.capgemini.entities.CustomerAddress;
import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.repository.OrderRepository;

@SpringBootTest
class CustomerServiceImplTest {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderRepository orderRepository;

	@Test
	void testRegisterCustomerShouldAddCustomer() throws NoSuchCustomerException {
		Customer customer = new Customer();
		customer.setFirstName("Hello");
		customer.setLastName("World");
		customer.setUserName("Test");
		customer.setEmailId("Test12@gmail.com");
		customer.setPassword("Test");
		customer.setContactNo(9536485621L);
		CustomerAddress address = new CustomerAddress();
		address.setCity("Test");
		address.setArea("Testing");
		address.setState("Test");
		address.setPincode(100000);
		customer.setCustomerAddress(address);

		Customer expected = customerService.registerCustomer(customer);
		Customer actual = adminService.findCustomerById(expected.getCustomerId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getUserName(), actual.getUserName());
		assertEquals(expected.getEmailId(), actual.getEmailId());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getContactNo(), actual.getContactNo());
		assertEquals(expected.getCustomerId(), actual.getCustomerId());

	}

	@Test
	void testFindOrderByIdShouldReturnOrderObject() throws NoSuchOrderException {
		Order order = new Order();
		FoodItem item = new FoodItem();
		Vendor vendor = new Vendor();
		item.setFoodName("Pav bhaji");
		item.setFoodPrice(200);
		item.setFoodQuantity(2);
		List<FoodItem> list = new ArrayList<>();
		list.add(item);
		Order expected = customerService.placeOrder(order.getVendor().getVendorId(), list, order.getCustomer().getCustomerId());
		order.setOrderPaymentStatus("Payment Successful");
		Order actual = adminService.findOrderById(expected.getOrderId());
		assertEquals(expected, actual);
	}

	@Test
	void testModifyOrderShouldReturnOrderObject() throws NoSuchOrderException {
		Order order = new Order();
		order.setOrderPaymentStatus("Payment Successful");
		order.setOrderPrice(300);
		order.setOrderStatus("Delivered");
		Order order1 = orderRepository.save(order);
		Order expected = customerService.modifyOrder(order1.getOrderId());
		Order actual = adminService.findOrderById(expected.getOrderId());
		assertEquals(expected, actual);
	}

	/*
	 * @Test void testViewDishesSortByPrice() { }
	 * 
	 * @Test void testSearchDishes() { }
	 * 
	 * @Test void testViewOrderStatus() { }
	 */

	/*
	 * @Test void testViewMenu() { }
	 */

	/*
	 * @Test void testFindCustomerById() { }
	 * 
	 * @Test void testPlaceOrder() { }
	 * 
	 * @Test void testCancelOrder() { }
	 */

}