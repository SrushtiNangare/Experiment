package com.capgemini.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.entities.Customer;
import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchDishException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.repository.CustomerRepository;
import com.capgemini.repository.FoodItemRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.VendorRepository;
import com.capgemini.utilities.GlobalResources;

/*Customer Service Implementation*/
@Service
public class CustomerServiceImpl implements CustomerService {

	Logger logger = GlobalResources.getLogger(CustomerServiceImpl.class);

	@Autowired
	/* Creating reference (it creates loosely coupled application) */
	private CustomerRepository customerRepository;
	@Autowired
	private FoodItemRepository foodItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private VendorRepository vendorRepository;
	@Autowired
	private AdminService adminService;

	@Override
	/* Customer registers by giving information */
	public Customer registerCustomer(Customer customer) {
		logger.info("registerCustomer() called");
		Customer result = null;
		if (isValidCustomer(customer)) {
			logger.info("Valid Customer");
			result = customerRepository.save(customer);
		}
		return result;
	}
	
	@Override
	public String customerLogin(String userName, String password) {
		logger.info("customerLogin() called");
		Customer customer = customerRepository.getCustomerData(userName);
		String user= customerRepository.getUserName(userName);
		if (customer.getUserName().equals(user) && customer.getPassword().equals(password))
			return "Login Successful";
		else
			return "Invalid Customer";
	}


	/* Place Order by selecting dishes from menu */
	@Override
	public Order placeOrder(int customerId, List<FoodItem> foodItems, int vendorId) {
		logger.info("placeOrder() called");
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Customer customer = customerRepository.findById(customerId).get();
		logger.info("placeOrder() customerRepo");
		Vendor vendor = vendorRepository.findById(vendorId).get();
		logger.info("placeOrder() vendorRepo");
		List<FoodItem> list = new ArrayList<>();
		if (customer != null) {
			Order order = new Order();
			order.setCustomer(customer);
			order.setOrderPrice(calculatePrice(foodItems));
			for (FoodItem foodItem : foodItems) {
				list.add(foodItemRepository.findById(foodItem.getFoodId()).get());
			}
			order.setFoodItems(list);
			order.setOrderTime(time);
			order.setOrderDate(date);
			order.setOrderStatus("Pending");
			order.setVendor(vendor);
			order.setOrderPaymentStatus("Pending");
			order = orderRepository.save(order);
			order = orderRepository.findByOrderId(order.getOrderId());
			return order;/* inserting record in vendor table */
		}
		return null;

	}

	/* Calculating Order Price */
	public double calculatePrice(List<FoodItem> foodItems) {
		logger.info("calculatePrice() called");
		double totalPrice = 0;
		for (FoodItem foodItem : foodItems)
			totalPrice += foodItem.getFoodPrice() * foodItem.getFoodQuantity();
		return totalPrice;
	}

	@Override
	/* Update Order by selecting from menu */
	public Order modifyOrder(int orderId, int customerId, List<FoodItem> foodItems, int vendorId)
			throws NoSuchOrderException {
		logger.info("modifyOrder() called");
		List<FoodItem> list = new ArrayList<>();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Customer customer = customerRepository.findById(customerId).get();
		Vendor vendor = vendorRepository.findById(vendorId).get();
		try {
			if (isvalidOrderId(orderId)) {
				logger.info("Valid Order Id");
				Order order = findOrderById(orderId); /* calling method findVendorById */
				if (order != null) {
					order.setCustomer(customer);
					order.setOrderPrice(calculatePrice(foodItems));
					for (FoodItem foodItem : foodItems) {
						list.add(foodItemRepository.findById(foodItem.getFoodId()).get());
					}
					order.setFoodItems(list);
					order.setOrderTime(time);
					order.setOrderDate(date);
					order.setOrderStatus("Pending");
					order.setVendor(vendor);
					order.setOrderPaymentStatus("Pending");
					orderRepository.save(order);
					return order;
				}
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderException(
					"Order with " + orderId + " is not found");/* Throwing and handling exception */
		}
		return null;
	}

	@Override
	/* View All Order */
	public List<Order> findAllOrder(int customerId) {
		logger.info("findAllOrder() called");
		return orderRepository.findAllCustomerOrders(customerId);
	}

	/* Find Order Using Order ID */
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

	/* Cancel order by giving order id */
	@Override
	public String cancelOrder(int orderId, String status) throws NoSuchOrderException {
		logger.info("cancelOrder() called");
		try {
			if (isvalidOrderId(orderId)) {
				Order o1 = orderRepository.findByOrderId(orderId);
				o1.setOrderStatus(status);
				orderRepository.save(o1);
				return status;
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderException(
					"Order with " + orderId + " is not found"); /* Throwing and handling exception */
		}
		return null;
	}

	/* View All Food Items from the menu */
	@Override
	public List<FoodItem> viewMenu() {
		logger.info("veiwMenu() called");
		return foodItemRepository.viewMenu();
	}

	@Override
	/* View Dishes by Price in Ascending or Descending Order */
	public List<FoodItem> viewDishesSortByPrice() {
		logger.info("viewDishesSortByPrice() called");
		return foodItemRepository.getDishesBySortedAmount();
	}

	@Override
	/* Search Dishes by their names */
	public List<FoodItem> searchDishes(String foodName) throws NoSuchDishException {
		logger.info("searchDishes() called");
		return foodItemRepository.getDishesByName(foodName);
	}

	/* View Order status using Order ID */
	@Override
	public String viewOrderStatusById(int orderId) throws NoSuchOrderException {
		logger.info("viewStatusByid() called");
		String status = orderRepository.getOrderStatusById(orderId);
		return status;
	}

	/* Validate Customer */
	public boolean isValidCustomer(Customer customer) {
		logger.info("isValidCustomer() called");
		boolean flag = true;
		String s = Long.toString(customer.getContactNo());
		if (!customer.getFirstName().matches("[A-Za-z]+"))
			flag = false;
		else if (!customer.getLastName().matches("[A-Za-z]+"))
			flag = false;
		else if (!customer.getUserName().matches("[A-Za-z]+"))
			flag = false;
		else if (!customer.getPassword().matches("[A-Za-z]+[@#$%&]"))
			flag = false;
		else if (!s.matches("\\d{10}"))
			flag = false;
		else if (!customer.getEmailId().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"))
			flag = false;
		return flag;
	}

	/* Validate OrderId */
	public boolean isvalidOrderId(int orderId) {
		logger.info("isvalidOrderId() called");
		if (orderId <= 0)
			return false;
		return true;
	}

	/* Validate FoodId */
	public boolean isValidFoodId(int foodId) {
		logger.info("isValidFoodId() called");
		if (foodItemRepository.getOne(foodId) != null) {
			return true;
		}
		return false;
	}

}