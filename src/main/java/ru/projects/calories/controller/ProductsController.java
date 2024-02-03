package ru.projects.calories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.projects.calories.model.Product;
import ru.projects.calories.model.User;
import ru.projects.calories.service.ProductService;
import ru.projects.calories.service.UserService;

@Controller
@RequestMapping("/products")
public class ProductsController
{
	private final UserService userService;
	private final ProductService productService;

	@Autowired
	public ProductsController(UserService userService, ProductService productService)
	{
		this.userService = userService;
		this.productService = productService;
	}

	@GetMapping
	public String getProducts(Model model, Authentication auth)
	{
		boolean admin = false;
		boolean manager = false;
		User user = null;

		if (auth != null)
		{
			admin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
			manager = auth.getAuthorities().contains(new SimpleGrantedAuthority("MANAGER"));
			user = this.userService.findByUsername(auth.getName()).orElse(null);
		}

		model.addAttribute("pageName", "База продуктов");
		model.addAttribute("admin", admin);
		model.addAttribute("manager", manager);
		model.addAttribute("products", this.productService.getAll());

		return "products/productList";
	}

	@GetMapping("/edit")
	public String editProduct(Model model, Authentication auth)
	{
		return "products/edit_product";
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id)
	{
		productService.deleteById(id);
		return ResponseEntity.ok("Product deleted successfully.");
	}

	@GetMapping("/{productId}/view")
	public String viewUser(@PathVariable Long productId, Authentication auth, Model model)
	{
		Product product = productService.findById(productId);

		model.addAttribute("pageName", "Продукт: " + product.getName());
		model.addAttribute("product", product);
		model.addAttribute("thisUser", auth.getPrincipal());

		return "products/product_view";
	}
}
