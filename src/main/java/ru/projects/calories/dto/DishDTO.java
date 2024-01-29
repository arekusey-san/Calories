package ru.projects.calories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishDTO
{
	private Long id;
	private String name;
	private double calories;
	private double proteins;
	private double fats;
	private double carbohydrates;
}
