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
import ru.projects.calories.model.Dish;
import ru.projects.calories.model.Product;
import ru.projects.calories.model.User;
import ru.projects.calories.service.DishService;
import ru.projects.calories.service.UserService;

@Controller
@RequestMapping("/dishes")
public class DishesController
{
	private final UserService userService;
	private final DishService dishService;

	@Autowired
	public DishesController(UserService userService, DishService dishService)
	{
		this.userService = userService;
		this.dishService = dishService;
	}

	@GetMapping
	public String getDishes(Model model, Authentication auth)
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

		model.addAttribute("pageName", "База блюд");
		model.addAttribute("admin", admin);
		model.addAttribute("manager", manager);
		model.addAttribute("dishes", this.dishService.getAll());

		return "dishes/dishesList";
	}

	@GetMapping("/edit")
	public String editDish(Model model, Authentication auth)
	{
		return "dishes/edit_dish";
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id)
	{
		dishService.deleteById(id);
		return ResponseEntity.ok("User deleted successfully.");
	}

	@GetMapping("/{dishId}/view")
	public String viewUser(@PathVariable Long dishId, Authentication auth, Model model)
	{
		Dish dish = dishService.findById(dishId);

		model.addAttribute("pageName", "Блюдо: " + dish.getName());
		model.addAttribute("dish", dish);
		model.addAttribute("thisUser", auth.getPrincipal());

		return "dishes/dish_view";
	}
}
