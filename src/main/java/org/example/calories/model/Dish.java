package org.example.calories.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish
{
	/**
	 * @author Arekusey-tyan
	 * @reason Use sequence method to identity table id
	 */
/*
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
*/

	private static final String SEQ_NAME = "dishes_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
	private Long id;

	private String name;
	private double calories;
	private double proteins;
	private double fats;
	private double carbohydrates;

	// геттеры, сеттеры и другие методы
}