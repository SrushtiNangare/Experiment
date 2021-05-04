package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	@Query("SELECT v.vendorPassword from Vendor v Where v.vendorPassword=:password")
	public String getPassword(@Param("password") String password);
	
	@Query("SELECT "
			+ "v.vendorId, "
			+ "v.vendorName, "
			+ "v.vendorContact, "
			+ "v.vendorAddress FROM Vendor v")
	public List<Object> viewAllVendors();
}
