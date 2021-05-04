package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

	@Query("SELECT m.foodName, m.foodPrice FROM FoodItem m ORDER BY m.foodPrice ASC")
	List<Object> getDishesBySortedAmount();

	@Query("SELECT m FROM FoodItem m Where m.foodName =:foodName")
	List<FoodItem> getDishesByName(@Param("foodName") String foodName);

	@Query("SELECT m.foodPrice FROM FoodItem m Where m.foodName =:foodName")
	public double getFoodPriceByName(@Param("foodName") String foodName);

	@Query("SELECT m.foodName, m.foodPrice FROM FoodItem m ")
	public List<Object> viewMenu();
	
	@Query("SELECT m FROM FoodItem m ORDER BY m.foodId")
	public List<FoodItem> items();
}
