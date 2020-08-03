package com.winter.securityex01.controller;

import org.hibernate.service.spi.Manageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winter.securityex01.config.auth.PrincipalDetails;
import com.winter.securityex01.model.User;
import com.winter.securityex01.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({"","/"})
	public @ResponseBody String index() {
		return "인덱스 페이지 입니다.";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println(principal);
		return "user 페이지 입니다.";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin 페이지 입니다.";
	}
	
//	@PostAuthorize("hasRole('ROLE_MANAGER')")
//	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Secured("ROLE_MANAGER")
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager 페이지입니다.";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@PostMapping("/joinProc")
	public String joinProc(User user) { // text나 JSON일때만 RequestBody 걸어준다.
		System.out.println("회원가입 진행 :" + user);
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/";
	}
}
