package ru.projects.calories.service;

import ru.projects.calories.model.Product;
import ru.projects.calories.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
	private final ProductRepository productRepo;

	@Autowired
	public ProductService(ProductRepository productRepo)
	{
		this.productRepo = productRepo;
	}

	public List<Product> getAll()
	{
		return this.productRepo.findAll();
	}

	public Product getProductByName(String name)
	{
		return this.productRepo.findByName(name);
	}

	// Другие методы для управления продуктами
}