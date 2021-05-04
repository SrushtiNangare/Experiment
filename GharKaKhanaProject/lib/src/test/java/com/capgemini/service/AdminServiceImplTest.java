package com.capgemini.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.entities.Admin;
import com.capgemini.entities.Order;
import com.capgemini.entities.Vendor;
import com.capgemini.entities.VendorAddress;
import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchOrderException;
import com.capgemini.exceptions.NoSuchVendorException;

@SpringBootTest
class AdminServiceImplTest {

	@Autowired
	private AdminService service;
	

	@Test
	// find vendor by vendor id
	void testFindVendorByVendorIdShouldReturnVendorObject() throws NoSuchVendorException {
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
		Vendor expected = service.addVendor(vendor);
		Vendor actual = service.findVendorById(expected.getVendorId());
		assertEquals(expected.getVendorId(), actual.getVendorId());
	}

	@Test
	void testRemoveVendorByVendorIdShouldReturnVendorObject() throws NoSuchVendorException {
		Vendor vendor = new Vendor();
		vendor.setVendorName("Keval Chheda");
		vendor.setVendorUsername("Chheda");
		vendor.setVendorPassword("KEev@");
		vendor.setVendorContact(4545454545L);

		VendorAddress address = new VendorAddress();
		address.setVendorCity("Mumbai");
		address.setVendorState("Maharashtra");
		address.setVendorPincode(400001);
		address.setArea("dadar");
		vendor.setVendorAddress(address);

		boolean actual = service.removeVendor(vendor.getVendorId());
		boolean expected = true;
		assertEquals(expected, actual);

		// assertTrue(vendor1);
	}

	@Test
	void testRemoveOrderByOrderIdShouldReturnVendorObject() throws NoSuchOrderException {
		Order order = new Order();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		order.setOrderTime(time);
		order.setOrderDate(date);
		order.setOrderPrice(250);
		order.setOrderStatus("Delivered");
		order.setOrderPaymentStatus("Payment Successful");
		boolean order1 = service.removeOrderByAdmin(order.getOrderId());
		boolean expected = true;
		assertEquals(expected, order1);

	}

	@Test
	void testModifyVendorByVendorIdShouldReturnVendorObject() throws NoSuchVendorException {
		Vendor vendor = new Vendor();
		vendor.setVendorName("Keval Chheda");
		vendor.setVendorUsername("Chhedda");
		vendor.setVendorPassword("Chheda@");
		vendor.setVendorContact(4545454545L);
		VendorAddress address = new VendorAddress();
		// We cannot set id because it is auto generated
		address.setVendorCity("Mumbai");
		address.setVendorState("gujarat");
		address.setVendorPincode(400001);
		address.setArea("dadar");
		vendor.setVendorAddress(address);

		Vendor expected = service.modifyVendor(vendor);
		Vendor actual = service.findVendorById(expected.getVendorId());
		assertEquals(expected.getVendorUsername(), actual.getVendorUsername());
		assertEquals(expected.getVendorName(), actual.getVendorName());
		assertEquals(expected.getVendorId(), actual.getVendorId());
	}

	@Test
	void testAdminRegisterAdminIdShouldReturnAdminObject() throws NoSuchAdminException {
		Admin admin = new Admin();
		admin.setAdminName("Keval Chedda");
		admin.setAdminUsername("Jain");
		admin.setAdminPassword("Chheda@");

		Admin expected = service.registerAdmin(admin);
		Admin actual = service.findAdminById(expected.getAdminId());
		assertEquals(expected.getAdminName(), actual.getAdminName());
		assertEquals(expected.getAdminUsername(), actual.getAdminUsername());
		assertEquals(expected.getAdminPassword(), actual.getAdminPassword());
	}

	@Test
	void testFindCustomerByCustomerIdShouldThrowNoSuchCustomerException() {
		assertThrows(NoSuchCustomerException.class, () -> service.findCustomerById(100));
	}

	@Test
	void testFindVendorByVendorIdShouldThrowNoSuchVendorException() {
		assertThrows(NoSuchVendorException.class, () -> service.findVendorById(100));
	}

	@Test
	void testDeleteVendorByVendorIdShouldThrowNoSuchCustomerException() {
		assertThrows(NoSuchVendorException.class, () -> service.removeVendor(100));

	}
}