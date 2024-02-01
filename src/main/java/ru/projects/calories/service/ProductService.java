package ru.projects.calories.service;

import ru.projects.calories.model.Product;
import ru.projects.calories.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

	public Product findById(Long id)
	{
		Optional<Product> product = productRepo.findById(id);

		return product.orElse(null);
	}

	public Product save(Product products)
	{
		Product product = productRepo.save(products);

		return product;
	}

	public Product delete(Product product)
	{
		productRepo.delete(product);

		return product;
	}

	// Другие методы для управления продуктами
}