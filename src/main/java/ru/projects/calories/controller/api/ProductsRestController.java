package ru.projects.calories.controller.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.security.ProtectionDomain;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Продукты", description = "Методы работы с продуктами")
@ApiResponses(
		value = {
				@ApiResponse(responseCode = "404", description = "Упс... Не удалось найти указанный путь", content = @Content),
				@ApiResponse(
						responseCode = "500",
						description = "Упс... Кажется, сервер к которому Вы обращаетесь выключен. Попробуйте позже.",
						content = @Content
				)
		}
)
public class ProductsRestController
{
	private final ProductService productService;

	@Autowired
	public ProductsRestController(ProductService productService)
	{
		this.productService = productService;
	}

	@GetMapping(value = "", produces = { "application/json", "application/xml" })
	@Operation(summary = "Получение всех продуктов", description = "Возвращает массив с продуктами.")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешная операция",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema(implementation = Product.class))
									)
							}
					)
			}
	)
	public ResponseEntity<List<Product>> getProducts()
	{
		List<Product> products = productService.getAll();

		return ResponseEntity.ok(products);
	}

	@GetMapping("/{name}")
	@Operation(
			summary = "Получение продукта",
			description = "Возвращает объект продукта с указанным именем на основе Product.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешная операция",
							content = {
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = Product.class)
									)
							}
					)
			}
	)
	public ResponseEntity<Product> getProduct(@PathVariable String name)
	{
		Product product = productService.getProductByName(name);

		return ResponseEntity.ok(product);
	}

	@PostMapping("/add")
	@Operation(
			summary = "Добавление продукта",
			description = "Возвращает объект добавленного продукта на основе Product.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "appilcation/json", schema = @Schema(implementation = Product.class))),
					@ApiResponse(responseCode = "401", description = "Упс... Кажется, Вы забыли авторизоваться!", content = @Content),
					@ApiResponse(responseCode = "403", description = "Упс... Кажется, у Вас недостаточно прав. Вы администратор?", content = @Content)
			}
	)
	public ResponseEntity<String> addProduct(ProductDTO dto)
	{
		Product product = Product.builder()
				.calories(dto.getCalories())
				.carbohydrates(dto.getCarbohydrates())
				.fats(dto.getFats())
				.proteins(dto.getProteins())
				.name(dto.getName())
				.build();

		productService.save(product);

		return ResponseEntity.ok("ok");
	}

	@PostMapping("/edit/{id}")
	@Operation(
			summary = "Изменение продукта",
			description = "Возвращает объект отредактированного продукта с указанным ID на основе Product.class"
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "appilcation/json", schema = @Schema(implementation = Product.class))),
					@ApiResponse(responseCode = "401", description = "Упс... Кажется, Вы забыли авторизоваться!", content = @Content),
					@ApiResponse(responseCode = "403", description = "Упс... Кажется, у Вас недостаточно прав. Вы администратор?", content = @Content)
			}
	)
	@Parameters(
			value = {
					@Parameter(name = "id", required = true, description = "ID продукта, который Вы собираетесь изменить.", in = ParameterIn.PATH, schema = @Schema(type = "integer"))
			}
	)
	public ResponseEntity<Product> editProduct(@PathVariable Long id, ProductDTO dto)
	{
		Product product = productService.findById(id);

		product.setCalories(dto.getCalories());
		product.setFats(dto.getFats());
		product.setCarbohydrates(dto.getCarbohydrates());
		product.setName(dto.getName());
		product.setProteins(dto.getProteins());

		Product saveProduct = productService.save(product);

		if (saveProduct == null)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(saveProduct);
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Удаление продукта по его ID",
			description = "Полностью удалят продукт по ID из базы данных."
	)
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Операция выполненеа успешпо", content = @Content(mediaType = "appilcation/json", schema = @Schema(implementation = Product.class))),
					@ApiResponse(responseCode = "401", description = "Упс... Кажется, Вы забыли авторизоваться!", content = @Content),
					@ApiResponse(responseCode = "403", description = "Упс... Кажется, у Вас недостаточно прав. Вы администратор?", content = @Content)
			}
	)
	@Parameters(
			value = {
					@Parameter(
							name = "id",
							description = "ID продукта, который вы собираетесь удалить.",
							required = true,
							in = ParameterIn.PATH,
							schema = @Schema(type = "integer")
					)
			}
	)
	public ResponseEntity<String> deleteProduct(@PathVariable Long id)
	{
		Product product = productService.findById(id);

		productService.delete(product);

		if (product == null)
		{
			return ResponseEntity.status(404).body("Не удалось найти и удалить продукт с ID: " + id);
		}

		return ResponseEntity.ok("ok");
	}
}
