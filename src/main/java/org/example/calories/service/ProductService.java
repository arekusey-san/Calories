package org.example.calories.service;

import org.example.calories.model.Product;
import org.example.calories.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{
	private final ProductRepository productRepo;

	@Autowired
	public ProductService(ProductRepository productRepo)
	{
		this.productRepo = productRepo;
	}

	public Product getProductByName(String name)
	{
		return productRepo.findByName(name);
	}

	// Другие методы для управления продуктами
}