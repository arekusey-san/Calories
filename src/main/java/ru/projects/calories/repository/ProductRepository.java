package ru.projects.calories.repository;

import ru.projects.calories.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{
	Product findByName(String name);
}