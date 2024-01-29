package ru.projects.calories.repository;

import ru.projects.calories.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long>
{
	Dish findByName(String name);
}