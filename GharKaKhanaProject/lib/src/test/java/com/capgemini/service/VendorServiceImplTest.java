package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.entities.FoodItem;
import com.capgemini.entities.Vendor;
import com.capgemini.entities.VendorAddress;
import com.capgemini.exceptions.NoSuchFoodItemException;
import com.capgemini.exceptions.NoSuchVendorException;

@SpringBootTest
class VendorServiceImplTest {

	@Autowired
	private VendorService vendorService;
	@Autowired
	private AdminService service;

	@Test
	void testFindMenutByIdShouldReturnMenuObject() throws NoSuchFoodItemException, NoSuchVendorException {
		FoodItem menu = new FoodItem();
		Vendor vendor = new Vendor();
		vendor.setVendorName("Keval Chheda");
		vendor.setVendorUsername("Chheda");
		vendor.setVendorPassword("Kev@");
		vendor.setVendorContact(4545454545L);
		VendorAddress address = new VendorAddress();
		// We cannot set id because it is auto generated
		address.setVendorCity("Mumbainagri");
		address.setVendorState("Maharashtra");
		address.setArea("panvel");
		address.setVendorPincode(400001);
		vendor.setVendorAddress(address);
		Vendor v1 = service.addVendor(vendor);
		menu.setFoodName("Burger");
		menu.setFoodPrice(120);
		menu.setFoodQuantity(1);
		menu.setVendor(vendor);
		FoodItem expected = vendorService.addFood(menu, 0);
		FoodItem actual = vendorService.findFoodById(expected.getFoodId());
		assertEquals(expected.getFoodId(), actual.getFoodId());
	}

	@Test
	void testFindMenuByIdShouldThrowNoSuchFoodItemException() {
		assertThrows(NoSuchFoodItemException.class, () -> {
			vendorService.findFoodById(100);
		});
	}

	@Test
	void testFindFoodByIdShouldThrowNoSuchFoodItemException() {
		assertThrows(NoSuchFoodItemException.class, () -> {
			vendorService.findFoodById(100);
		});
	}

	@Test
	void testmodifyFoodShouldReturnMenuObject() throws NoSuchFoodItemException, NoSuchVendorException {
		Vendor vendor = new Vendor();

		FoodItem menu = new FoodItem();
		menu.setFoodName("Before Testing");
		menu.setFoodPrice(80);
		FoodItem expected = vendorService.addFood(menu, vendor.getVendorId());
		expected.setFoodName("After Testing");
		expected.setFoodPrice(90);
		expected = vendorService.modifyFood(expected);
		assertEquals(expected.getFoodName(), menu.getFoodName());
		assertEquals(expected.getFoodPrice(), menu.getFoodPrice());
	}

	@Test
	void testRemoveFoodByfoodId() throws NoSuchFoodItemException, NoSuchVendorException {
		FoodItem menutoberemoved = new FoodItem();
		Vendor vendor = new Vendor();
		menutoberemoved.setFoodName("pizza");
		menutoberemoved.setFoodPrice(125);
		FoodItem item = vendorService.addFood(menutoberemoved, vendor.getVendorId());
		boolean expected = true;
		boolean actual = vendorService.removeFood(item.getFoodId());
		assertEquals(expected, actual);
	}

}