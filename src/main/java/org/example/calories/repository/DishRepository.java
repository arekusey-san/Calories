package org.example.calories.repository;

import org.example.calories.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long>
{
	Dish findByName(String name);
}