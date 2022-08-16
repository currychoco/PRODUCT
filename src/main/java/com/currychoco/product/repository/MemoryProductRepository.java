package com.currychoco.product.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.currychoco.product.domain.Product;

public class MemoryProductRepository implements ProductRepository{

	private static Map<Long, Product> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Product save(Product product) {
		product.setId(++sequence);
		store.put(product.getId(), product);
		return product;
	}

	@Override
	public Optional<Product> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public List<Product> findAll() {
		return new ArrayList<Product>(store.values());
	}
	
	@Override
	public Optional<Product> findByName(String name) {	
		return store.values().stream().filter(product -> product.getName().equals(name)).findAny();
	}

	@Override
	public boolean deleteById(Long id) {
		Product p = store.remove(id);
		if(p == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
