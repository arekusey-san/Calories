package ru.projects.calories.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.calories.model.User;
import ru.projects.calories.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Методы для работы с пользователями")
public class UserRestController
{
	private final UserService userService;

	@Autowired
	public UserRestController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping()
	@Operation(summary = "Получение всех пользователей", description = "Возвращает массив с пользователями.")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешная операция",
							content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))
					)
			}
	)
	public ResponseEntity<List<User>> getUsers()
	{
		List<User> users = this.userService.getAll();

		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Получение пользователя",
			description = "Возвращает объект пользоватедя с указанным именем на основе User.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешная операция",
							content = {
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = User.class)
									)
							}
					)
			}
	)
	public ResponseEntity<User> getUserById(@PathVariable Long id)
	{
		User user = userService.getUserById(id);

		if (user == null)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(user);
	}
}
