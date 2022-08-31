package com.currychoco.product.service;

import org.springframework.stereotype.Service;

import com.currychoco.product.domain.Member;
import com.currychoco.product.repository.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public void addMember(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
	}

	public boolean checkLogin(String id, String password) {
		return memberRepository.findByIdAndPassword(id, password).isPresent();
	}
	
	private void validateDuplicateMember(Member member) {
		memberRepository.findById(member.getId()).
		ifPresent(m -> {throw new IllegalStateException("이미 존재하는 아이디입니다.");});
		memberRepository.findByName(member.getName()).
		ifPresent(m -> {throw new IllegalStateException("이미 존재하는 닉네임입니다.");});
	}
}
