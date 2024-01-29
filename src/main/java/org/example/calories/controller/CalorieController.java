package org.example.calories.controller;

import org.example.calories.model.Dish;
import org.example.calories.model.Product;
import org.example.calories.service.DishService;
import org.example.calories.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalorieController
{
	@Autowired
	private ProductService productService;

	@Autowired
	private DishService dishService;

	@GetMapping("/product/{name}")
	public ResponseEntity<Product> getProduct(@PathVariable String name)
	{
		Product product = productService.getProductByName(name);
		return ResponseEntity.ok(product);
	}

	@GetMapping("/dish/{name}")
	public ResponseEntity<Dish> getDish(@PathVariable String name)
	{
		Dish dish = dishService.getDishByName(name);
		return ResponseEntity.ok(dish);
	}

	// Добавьте методы для расчета нормы потребления калорий, добавления, изменения и удаления продуктов/блюд
}
