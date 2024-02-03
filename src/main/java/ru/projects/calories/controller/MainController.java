package ru.projects.calories.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.projects.calories.dto.UserDTO;
import ru.projects.calories.model.Role;
import ru.projects.calories.model.User;
import ru.projects.calories.repository.UserRepository;

@Controller
public class MainController
{
	private final UserRepository userRepo;

	public MainController(UserRepository userRepo)
	{
		this.userRepo = userRepo;
	}

	@GetMapping()
	public String getMain(Model model, Authentication auth)
	{
		boolean admin;
		User user;
		if (auth != null)
		{
			admin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
			user = this.userRepo.findUserByName(auth.getName()).orElse(null);
		}
		else
		{
			admin = false;
			user = null;
		}

		model.addAttribute("pageName", "Главная");
		model.addAttribute("admin", admin);
		model.addAttribute("user", user);

		return "index";
	}

	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("pageName", "Авторизация");

		return "login";
	}

	@GetMapping("/login_error")
	public String loginError(Model model)
	{
		model.addAttribute("pageName", "Ошибка авторизации");

		return "login";
	}

	@GetMapping("/register")
	public String register(Model model)
	{
		model.addAttribute("pageName", "Регистрация");

		return "register";
	}
}
