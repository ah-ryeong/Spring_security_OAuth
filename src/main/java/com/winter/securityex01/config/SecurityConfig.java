package com.winter.securityex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC 빈(bean, 객체)을 등록 , 오브젝트는 아님 오브젝트는 메모리에 띄우지 X
@EnableWebSecurity // 필터 체인 관리 시작(전체 필터를 관리할 수 있는 설정(클래스) 파일), 체인 하나하나를 직접적으로 관리할 수 있다. 필터들 사이에 끼어드는거 X
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 컨트롤러 접근 전에 낚아 채도록 함, prePostEnabled = true : 특정 주소 접근시 권한 및 인증을 미리 체크하겠다는 뜻.
// securedEnabled = true : 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화 SecurityConfig.java에 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean // 리턴할 때 IoC 등록, 메서드를 IoC하는 방법임
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable(); // ﻿csrf-Token 비활성화
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() // 잠궈준다. authenticated : 인증을 물어봄
//			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // access : 권한을 물어봄
//			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')") // security에서 role을 만들때는 prefix의 ROLE_이 약속된 규칙이라 꼭 써줘야한다.
//			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") 
//			.antMatchers("/user").hasRole("USER")
			.anyRequest().permitAll() // 열어준다.
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProc") // 해당 주소로 접속하면 Authentication Manager를 탄다.
			.defaultSuccessUrl("/"); // 로그인을 낚아채서 해당주소로 리다이렉션해준다.
	}
}
