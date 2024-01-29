package ru.projects.calories.service;

import ru.projects.calories.model.Dish;
import ru.projects.calories.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService
{
	private final DishRepository dishRepo;

	@Autowired
	public DishService(DishRepository dishRepo)
	{
		this.dishRepo = dishRepo;
	}

	public List<Dish> getAll()
	{
		return this.dishRepo.findAll();
	}

	public Dish getDishByName(String name)
	{
		return this.dishRepo.findByName(name);
	}
}
