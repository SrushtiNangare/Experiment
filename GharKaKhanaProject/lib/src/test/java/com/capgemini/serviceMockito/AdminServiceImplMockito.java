package com.capgemini.serviceMockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capgemini.entities.Customer;
import com.capgemini.entities.CustomerAddress;
import com.capgemini.entities.Vendor;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.exceptions.NoSuchVendorException;
import com.capgemini.repository.AdminRepository;
import com.capgemini.repository.CustomerRepository;
import com.capgemini.repository.VendorRepository;
import com.capgemini.service.AdminService;
import com.capgemini.service.CustomerService;

@SpringBootTest
class AdminServiceImplMockito {
	@Autowired
	private AdminService service;
	@Autowired
	private CustomerService customerService;
	@MockBean
	private AdminRepository repository;
	@MockBean
	private CustomerRepository customerRepository;
	@MockBean
	private VendorRepository vendorRepository;

	@Test
	void testFindCustomerByIdShouldReturnCustomerObject() throws NoSuchCustomerException {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setFirstName("Keval");
		customer.setLastName("Chheda");
		customer.setUserName("Keuuval");
		customer.setPassword("Keval");
		customer.setContactNo(4545454545L);
		CustomerAddress address = new CustomerAddress();
		address.setAddressId(1);
		address.setCity("Test");
		address.setArea("Testing");
		address.setState("Test");
		address.setPincode(100000);
		customer.setCustomerAddress(address);
		Optional<Customer> expected = Optional.of(customer);
		when(customerRepository.findById(1)).thenReturn(expected);

		Customer result = service.findCustomerById(customer.getCustomerId());
		assertEquals(expected, result);

	}

//	@Test
//	void testFindAdminByIdShouldReturnCustomerObject() throws Exception {
//		Admin admin = new Admin();
//		admin.setAdminName("only");
//		admin.setAdminUsername("chheda");
//		admin.setAdminPassword("onlychheda");
//
//		Optional<Admin> expected = Optional.of(admin);
//		when(repository.findById(admin.getAdminId())).thenReturn(expected);
//
//		Admin result = service.findAdminById(admin.getAdminId());
//		assertEquals(expected, result);
//
//	}

	@Test
	void testFindVendorByIdShouldReturnVendorObject() throws Exception {
		Vendor vendor = new Vendor();
		vendor.setVendorName("only");
		vendor.setVendorUsername("chheda");
		vendor.setVendorPassword("onlychheda");

		Optional<Vendor> expected = Optional.of(vendor);
		when(vendorRepository.findById(1)).thenReturn(expected);

		Vendor result = service.findVendorById(vendor.getVendorId());
		assertEquals(vendor, result);

	}

	@Test
	void testFindCustomerByIdShouldThrowNoSuchStudentException() {
		assertThrows(NoSuchCustomerException.class, () -> {
			service.findCustomerById(100);
		});
	}

	@Test
	void testFindVendorByIdShouldThrowNoSuchVendorException() {
		assertThrows(NoSuchVendorException.class, () -> {
			service.findVendorById(100);
		});
	}

}