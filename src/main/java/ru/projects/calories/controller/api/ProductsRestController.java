package ru.projects.calories.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.projects.calories.dto.ProductDTO;
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
@Tag(name = "Методы работы с продуктами")
public class ProductsRestController
{
	private final ProductService productService;

	@Autowired
	public ProductsRestController(ProductService productService)
	{
		this.productService = productService;
	}

	@GetMapping("")
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

	@PostMapping("/add")
	public ResponseEntity<String> addProduct(ProductDTO dto)
	{
		Product product = Product.builder()
				.calories(dto.getCalories())
				.carbohydrates(dto.getCarbohydrates())
				.fats(dto.getFats())
				.proteins(dto.getProteins())
				.name(dto.getName())
				.build();

		return ResponseEntity.ok("ok");
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<Product> editProduct(@PathVariable Long id, ProductDTO dto)
	{
		Product product = productService.findById(id);

		return ResponseEntity.ok(product);
	}

	@DeleteMapping("/delete/{id}")
	@Operation(
			description = "Delete product by id"
	)
	public ResponseEntity<String> deleteProduct(@PathVariable Long id)
	{
		Product product = productService.findById(id);

		return ResponseEntity.ok("ok");
	}
}
