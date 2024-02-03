package ru.projects.calories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.projects.calories.dto.UserDTO;
import ru.projects.calories.model.User;
import ru.projects.calories.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController
{
	private final UserService userService;

	@Autowired
	public UserController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping
	public String userList(Authentication auth, Model model)
	{
		boolean admin;
		boolean manager;
		User user;
		if (auth != null)
		{
			admin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
			manager = auth.getAuthorities().contains(new SimpleGrantedAuthority("MANAGER"));
			user = this.userService.findByUsername(auth.getName()).orElse(null);
		}
		else
		{
			admin = false;
			manager = false;
			user = null;
		}

		model.addAttribute("pageName", "База пользователей");
		model.addAttribute("admin", admin);
		model.addAttribute("manager", manager);
		model.addAttribute("local", user);
		model.addAttribute("users", userService.getAll());

		return "users/userList";
	}

	@PutMapping("/{id}/promote")
	public ResponseEntity<String> promoteUser(@PathVariable Long id)
	{
		userService.promoteUserById(id);
		return ResponseEntity.ok("User promoted successfully.");
	}

	@PutMapping("/{id}/demote")
	public ResponseEntity<String> demoteUser(@PathVariable Long id)
	{
		userService.demoteUserById(id);
		return ResponseEntity.ok("User demoted successfully.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id)
	{
		userService.deleteUserById(id);
		return ResponseEntity.ok("User deleted successfully.");
	}

	@GetMapping("/new")
	public String newUser(Model model)
	{
		model.addAttribute("user", new UserDTO());
		return "users/new_user";
	}

	@PostMapping("/new")
	public String saveUser(UserDTO user, Model model)
	{
		if (userService.save(user))
		{
			return "redirect:/users";
		}
		else
		{
			model.addAttribute("user", user);
			return "users/new_user";
		}
	}

	@GetMapping("/{id}/edit")
	public String editUser(@PathVariable Long id, Model model)
	{
		User u = userService.findById(id);
		UserDTO user = UserDTO.builder().login(u.getLogin()).email(u.getEmail())
							  .name(u.getName()).sex(u.getSex())
							  .id(u.getId()).build();
		model.addAttribute("user", user);
		return "users/edit_user";
	}

	@PostMapping("/{id}/save")
	public String saveEditUser(UserDTO user, @PathVariable Long id, Model model)
	{
		if (userService.saveEdit(user, id))
		{
			return "redirect:/users";
		}
		else
		{
			model.addAttribute("user", user);
			model.addAttribute("error", "Error");
			return "users/edit_user";
		}
	}

	@GetMapping("/{userId}/view")
	public String viewUser(@PathVariable Long userId, Authentication auth, Model model)
	{
		User user = userService.findById(userId);

		model.addAttribute("pageName", "Пользователь: " + user.getLogin());
		model.addAttribute("user", user);
		model.addAttribute("thisUser", auth.getPrincipal());

		return "users/user_view";
	}
}
