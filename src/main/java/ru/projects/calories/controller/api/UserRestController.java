package ru.projects.calories.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Методы для работы с пользователями")
public class UserRestController
{
	@GetMapping()
	public void getUser()
	{
	}
}
