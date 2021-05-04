package com.capgemini.serviceMockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capgemini.entities.FoodItem;
import com.capgemini.exceptions.NoSuchFoodItemException;
import com.capgemini.repository.FoodItemRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.VendorRepository;
import com.capgemini.service.VendorService;

@SpringBootTest
class VendorServiceImplTestMockito {

	@Autowired
	private VendorService vendorService;

	@MockBean
	/* Creating reference (it creates loosely coupled application) */
	private FoodItemRepository foodItemRepository;

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private VendorRepository vendorRepository;

	@Test
	public void findMenuByIdTest() throws NoSuchFoodItemException {
		FoodItem menu = new FoodItem();
		menu.setFoodId(5);
		menu.setFoodName("Burger");
		menu.setFoodPrice(120);
		menu.setFoodQuantity(2);

		Optional<FoodItem> expected = Optional.of(menu);
		when(foodItemRepository.findById(5)).thenReturn(expected);

		FoodItem result = vendorService.findFoodById(menu.getFoodId());
		assertEquals(menu, result);
	}

	@Test
	void testFindMenuByIdShouldThrowNoSuchFoodItemException() {
		assertThrows(NoSuchFoodItemException.class, () -> {
			vendorService.findFoodById(100);
		});
	}
}