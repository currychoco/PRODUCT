package com.currychoco.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.currychoco.product.domain.Member;
import com.currychoco.product.domain.Product;
import com.currychoco.product.dto.ProductReadDto;
import com.currychoco.product.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	//@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Long addProduct(Product product) {
		validateDuplicateProduct(product);
		productRepository.save(product);
		return product.getId();
	}
	
	private void validateDuplicateProduct(Product product) {
		productRepository.findByName(product.getName()).
		ifPresent(m -> {throw new IllegalStateException("이미 존재하는 상품입니다.");});
	}
	
	public List<ProductReadDto> findProdcuts(){
		
		List<Product> products = productRepository.findAll();
		List<ProductReadDto> dtos = new ArrayList<>();
		for(Product product : products) {
			ProductReadDto dto = new ProductReadDto();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public Optional<Product> findOne(Long productId){
		return productRepository.findById(productId);
	}
	
	public boolean deleteById(Long id) {
		return productRepository.deleteById(id);
	}
}
