package ru.projects.calories.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.calories.dto.DishDTO;
import ru.projects.calories.model.Dish;
import ru.projects.calories.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@Tag(name = "Блюда", description = "Методы работы с блюдами")
public class DishesRestController
{
	private final DishService dishService;

	@Autowired
	public DishesRestController(DishService dishService)
	{
		this.dishService = dishService;
	}

	@GetMapping(value = "", produces = { "application/xml", "application/json" })
	@Operation(summary = "Получение всех блюд", description = "Возвращает массив с блюдами.")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешная операция",
							content = @Content(array = @ArraySchema(schema = @Schema(implementation = Dish.class)))
					)
			}
	)
	public ResponseEntity<List<Dish>> getDishes()
	{
		List<Dish> dishes = this.dishService.getAll();

		return ResponseEntity.ok(dishes);
	}

	@GetMapping("/{name}")
	@Operation(
			summary = "Получение блюда",
			description = "Возвращает объект блюда с указанным именем на основе Dish.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешная операция",
							content = {
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = Dish.class)
									)
							}
					)
			}
	)
	public ResponseEntity<Dish> getDish(@PathVariable String name)
	{
		Dish dish = dishService.getDishByName(name);

		return ResponseEntity.ok(dish);
	}

	@PostMapping("/add")
	@Operation(
			summary = "Добавление блюда",
			description = "Возвращает объект добавленного блюда на основе Dish.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "appilcation/json", schema = @Schema(implementation = Dish.class))),
					@ApiResponse(responseCode = "401", description = "Упс... Кажется, Вы забыли авторизоваться!", content = @Content),
					@ApiResponse(responseCode = "403", description = "Упс... Кажется, у Вас недостаточно прав. Вы администратор?", content = @Content)
			}
	)
	public ResponseEntity<String> addDish(DishDTO dto)
	{
		Dish dish = Dish.builder()
				.calories(dto.getCalories())
				.carbohydrates(dto.getCarbohydrates())
				.proteins(dto.getProteins())
				.fats(dto.getFats())
				.name(dto.getName())
				.build();

		return ResponseEntity.ok("ok");
	}

	@PostMapping("/edit/{id}")
	@Operation(
			summary = "Изменение блюда",
			description = "Возвращает объект отредактированного блюда с указанным ID на основе Dish.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "appilcation/json", schema = @Schema(implementation = Dish.class))),
					@ApiResponse(responseCode = "401", description = "Упс... Кажется, Вы забыли авторизоваться!", content = @Content),
					@ApiResponse(responseCode = "403", description = "Упс... Кажется, у Вас недостаточно прав. Вы администратор?", content = @Content)
			}
	)
	@Parameters(
			value = {
					@Parameter(name = "id", required = true, description = "ID блюда, который Вы собираетесь изменить.", in = ParameterIn.PATH, schema = @Schema(type = "integer"))
			}
	)
	public ResponseEntity<Dish> editDish(@PathVariable Long id)
	{
		Dish dish = dishService.findById(id);

		return ResponseEntity.ok(dish);
	}

	@DeleteMapping("/delete/{id}")
	@Operation(
			summary = "Удаление блюда по его ID",
			description = "Полностью удалят блюдо по ID из базы данных."
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Операция выполненеа успешпо", content = @Content(mediaType = "appilcation/json", schema = @Schema(implementation = Dish.class))),
					@ApiResponse(responseCode = "401", description = "Упс... Кажется, Вы забыли авторизоваться!", content = @Content),
					@ApiResponse(responseCode = "403", description = "Упс... Кажется, у Вас недостаточно прав. Вы администратор?", content = @Content)
			}
	)
	@Parameters(
			value = {
					@Parameter(
							name = "id",
							description = "ID блюда, который вы собираетесь удалить.",
							required = true,
							in = ParameterIn.PATH,
							schema = @Schema(type = "integer")
					)
			}
	)
	public ResponseEntity<String> deleteDish(@PathVariable Long id)
	{
		Dish dish = dishService.findById(id);

		return ResponseEntity.ok("ok");
	}
}
