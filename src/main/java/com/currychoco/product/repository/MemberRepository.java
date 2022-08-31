package com.currychoco.product.repository;

import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.currychoco.product.domain.Member;

@Repository
public class MemberRepository {

	private final SqlSessionTemplate sqlSession;
	
	public MemberRepository(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void save(Member member) {
		sqlSession.insert("member.save", member);
	}

	public Optional<Member> findByName(String name) {
		return Optional.ofNullable(sqlSession.selectOne("member.findByName", name));
	}
	
	public Optional<Member> findById(String id) {
		return Optional.ofNullable(sqlSession.selectOne("member.findById", id));
	}
	public Optional<Member> findByIdAndPassword(String id, String password) {
		Member member = new Member();
		member.setId(id);
		member.setPassword(password);
		return Optional.ofNullable(sqlSession.selectOne("member.findByIdAndPassword", member));
	}
}
