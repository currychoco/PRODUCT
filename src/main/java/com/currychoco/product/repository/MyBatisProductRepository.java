package com.currychoco.product.repository;

import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.currychoco.product.domain.Member;
import com.currychoco.product.domain.Product;

@Repository
public class MyBatisProductRepository implements ProductRepository{
	
	private final SqlSessionTemplate sqlSession;
	
	public MyBatisProductRepository(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public Product save(Product product) {
		sqlSession.insert("product.save", product);
		return product;
	}
	
	@Override
	public Optional<Product> findById(Long id) {
		return Optional.of(sqlSession.selectOne("product.findById", id));
	}

	@Override
	public Optional<Product> findByName(String name) {
		return Optional.of(sqlSession.selectOne("product.findByName", name));
	}

	@Override
	public List<Product> findAll() {
		return sqlSession.selectList("product.findAll");
	}

	@Override
	public boolean deleteById(Long id) {
		int rowCount = sqlSession.delete("product.deleteById", id);
		return rowCount > 0;
	}

}
