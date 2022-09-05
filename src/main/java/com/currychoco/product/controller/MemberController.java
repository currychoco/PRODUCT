package com.currychoco.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.currychoco.product.domain.Member;
import com.currychoco.product.dto.MemberJoinDto;
import com.currychoco.product.service.MemberService;

@Controller
public class MemberController {

	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/members/join")
	public String joinForm() {
		return "members/memberJoin";
	}
	
	@PostMapping(value = "/members/join")
	public String join(MemberJoinDto joinDto) {
		Member member = new Member();
		member.setId(joinDto.getId());
		member.setPassword(joinDto.getPassword());
		member.setName(joinDto.getName());
		memberService.addMember(member);
		return "redirect:/members/join";
	}
	
	@GetMapping(value = "/members/login")
	public String loginForm() {
		return "members/memberLogin";
	}
	
	@PostMapping(value = "/members/login")
	public String login(MemberJoinDto joinDto, HttpServletRequest request) {
		
		Member member = memberService.checkLogin(joinDto.getId(), joinDto.getPassword());
		if(member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
			session.setAttribute("name", member.getName());
			session.setAttribute("ip", request.getLocalAddr());
			return "redirect:/";
		} else {
			return "redirect:/members/login";
		}
	}
}
