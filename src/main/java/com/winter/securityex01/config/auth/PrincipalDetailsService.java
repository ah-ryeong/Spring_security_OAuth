package com.winter.securityex01.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.winter.securityex01.model.User;
import com.winter.securityex01.repository.UserRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService { // UserDetailsService가 메모리에 뜨고, PrincipalDetailsService가 덮어씌어진다. 

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return null;
		}
		return new PrincipalDetails(user);
	}

}
