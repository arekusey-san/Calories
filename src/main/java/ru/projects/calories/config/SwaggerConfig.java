package ru.projects.calories.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig
{
	@Bean
	public OpenAPI openAPI()
	{
		return new OpenAPI()
				.servers(
						List.of(
								new Server()
										.url("http://localhost:3009")
							   )
						)
				.info(
						new Info()
								.title("Restful API")
								.version("1.0")
								.license(new License().name("Apache 2.0"))
					 );
	}
}
