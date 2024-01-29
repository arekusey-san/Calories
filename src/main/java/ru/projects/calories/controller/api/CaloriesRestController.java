package ru.projects.calories.controller.api;

import ru.projects.calories.model.Dish;
import ru.projects.calories.model.Product;
import ru.projects.calories.service.DishService;
import ru.projects.calories.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calories")
public class CaloriesRestController
{
	private final ProductService productService;
	private final DishService dishService;

	@Autowired
	public CaloriesRestController(ProductService productService, DishService dishService)
	{
		this.productService = productService;
		this.dishService = dishService;
	}

	/**
	 * Products restful api
	 */

	@GetMapping({ "/products", "/products/" })
	public ResponseEntity<List<Product>> getProducts()
	{
		List<Product> products = productService.getAll();

		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/{name}")
	public ResponseEntity<Product> getProduct(@PathVariable String name)
	{
		Product product = productService.getProductByName(name);

		return ResponseEntity.ok(product);
	}

	/**
	 * Dishes restful api
	 */

	@GetMapping({"/dishes", "/dishes/"})
	public ResponseEntity<List<Dish>> getDishes()
	{
		List<Dish> dishes = this.dishService.getAll();

		return ResponseEntity.ok(dishes);
	}

	@GetMapping("/dishes/{name}")
	public ResponseEntity<Dish> getDish(@PathVariable String name)
	{
		Dish dish = dishService.getDishByName(name);

		return ResponseEntity.ok(dish);
	}
}
