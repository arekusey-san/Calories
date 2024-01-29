package ru.projects.calories.repository;

import ru.projects.calories.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
ProductRepository extends JpaRepository<Product, Long>
{
	Product findByName(String name);
}