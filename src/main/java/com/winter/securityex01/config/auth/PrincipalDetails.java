package com.winter.securityex01.config.auth;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.winter.securityex01.model.User;

import lombok.Data;

// Authentication 객체에 저장할 수 있는 유일한 타입이다.
@Data
public class PrincipalDetails implements UserDetails {

	private User user;
	
	public PrincipalDetails(User user) { // 생성자
		super();
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정이 만료됐는지 확인함
		return true; // 최종 접속시간 확인해서 1년 지났으면 false 넣어야함.
	}

	@Override
	public boolean isAccountNonLocked() { // 계정이 잠겼는지 확인
		return true; // 비밀번호 5번이상 틀리면 false 넣어주면 됨.
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 권한 확인
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		System.out.println("PrincipalDetail 확인 : " +authorities);
		return authorities; // 여기 유저 정보 전부 다 리턴 
	}

}
