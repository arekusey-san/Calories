package ru.projects.calories.controller.api;

import ru.projects.calories.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.calories.model.Product;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsRestController
{
	private final ProductService productService;

	@Autowired
	public ProductsRestController(ProductService productService)
	{
		this.productService = productService;
	}


	@GetMapping({ "", "/" })
	public ResponseEntity<List<Product>> getProducts()
	{
		List<Product> products = productService.getAll();

		return ResponseEntity.ok(products);
	}

	@GetMapping("/{name}")
	public ResponseEntity<Product> getProduct(@PathVariable String name)
	{
		Product product = productService.getProductByName(name);

		return ResponseEntity.ok(product);
	}
}
