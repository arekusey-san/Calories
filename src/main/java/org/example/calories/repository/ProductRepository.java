package org.example.calories.repository;

import org.example.calories.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
ProductRepository extends JpaRepository<Product, Long>
{
	Product findByName(String name);
}