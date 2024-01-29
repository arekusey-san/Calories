package org.example.calories.service;

import org.example.calories.model.Dish;
import org.example.calories.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishService
{
	private final DishRepository dishRepo;

	@Autowired
	public DishService(DishRepository dishRepo)
	{
		this.dishRepo = dishRepo;
	}

	public Dish getDishByName(String name)
	{
		return dishRepo.findByName(name);
	}
}
