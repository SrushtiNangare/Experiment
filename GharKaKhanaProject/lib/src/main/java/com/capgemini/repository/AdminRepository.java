package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Admin;
import com.capgemini.entities.Customer;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("SELECT a.adminPassword from Admin a Where a.adminPassword=:password")
	public String getPassword(@Param("password") String password);
	
	@Query("SELECT c.adminUsername from Admin c WHERE c.adminUsername=:adminUsername")
	public String getUserName(@Param("adminUsername") String userName);
	
	@Query("SELECT c from Admin c WHERE c.adminUsername=:adminUsername")
	public Admin getAdminData(@Param("adminUsername") String userName);

}
