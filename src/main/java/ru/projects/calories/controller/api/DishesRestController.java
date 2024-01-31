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
	public ResponseEntity<Dish> getDish(@PathVariable String name)
	{
		Dish dish = dishService.getDishByName(name);

		return ResponseEntity.ok(dish);
	}

	@PostMapping("/add")
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
	public ResponseEntity<Dish> editDish(@PathVariable Long id)
	{
		Dish dish = dishService.findById(id);

		return ResponseEntity.ok(dish);
	}

	@DeleteMapping("/delete/{id}")
	@Operation(
			description = "Delete dish by id"
	)
	public ResponseEntity<String> deleteDish(@PathVariable Long id)
	{
		Dish dish = dishService.findById(id);

		return ResponseEntity.ok("ok");
	}
}
