package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

	@Query("SELECT m FROM FoodItem m ORDER BY m.foodPrice ASC")
	List<FoodItem> getDishesBySortedAmount();

	@Query("SELECT m FROM FoodItem m Where m.foodName =:foodName")
	List<FoodItem> getDishesByName(@Param("foodName") String foodName);

	@Query("SELECT m.foodPrice FROM FoodItem m Where m.foodName =:foodName")
	public double getFoodPriceByName(@Param("foodName") String foodName);

	@Query("SELECT m FROM FoodItem m ")
	public List<FoodItem> viewMenu();
	
	@Query("SELECT m FROM FoodItem m ORDER BY m.foodId")
	public List<FoodItem> items();
	
	@Query("SELECT m FROM FoodItem m Where m.vendor.vendorId =:vendorId")
	public List<FoodItem> findItemByVendorId(@Param("vendorId") int vendorId);
		
}
