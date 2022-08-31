package com.currychoco.product.repository;

import java.util.List;
import java.util.Optional;

import com.currychoco.product.domain.Member;
import com.currychoco.product.domain.Product;

public interface ProductRepository {

	Product save(Product product);
	Optional<Product> findById(Long id);
	Optional<Product> findByName(String name);
	List<Product> findAll();
	boolean deleteById(Long id);
}
