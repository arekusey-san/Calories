package ru.projects.calories.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.calories.model.Dish;
import ru.projects.calories.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
public class DishesRestController
{
	private final DishService dishService;

	@Autowired
	public DishesRestController(DishService dishService)
	{
		this.dishService = dishService;
	}

	@GetMapping({"", "/"})
	public ResponseEntity<List<Dish>> getDishes()
	{
		List<Dish> dishes = this.dishService.getAll();

		return ResponseEntity.ok(dishes);
	}

	@GetMapping("/{name}")
	public ResponseEntity<Dish> getDish(@PathVariable String name)
	{
		Dish dish = dishService.getDishByName(name);

		return ResponseEntity.ok(dish);
	}
}
