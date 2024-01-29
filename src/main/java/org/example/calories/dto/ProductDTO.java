package org.example.calories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO
{
	private Long id;
	private String name;
	private double calories;
	private double proteins;
	private double fats;
	private double carbohydrates;
}
