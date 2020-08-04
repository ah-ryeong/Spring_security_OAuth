package com.winter.securityex01.test;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.winter.securityex01.model.User;
import com.winter.securityex01.repository.UserRepository;

@RestController
public class OptionalControllerTest {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test/user/{id}")
	public User 옵셔널_유저찾기(@PathVariable int id) {
		// 첫 번째 방법
//		Optional<User> userOptional = userRepository.findById(id);
//		User user;
//		
//		if (userOptional.isPresent()) { // true -> null 이 아님
//			user = userOptional.get(); 
//		} else {
//			user = new User();
//		}
		
//		return user;
		
		// 두 번째 방법
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			// orElseGet : 있으면 return , 없으면 빈 객체를 리턴해준다.
//			@Override
//			public User get() {
//				return new User();
////				return new User().builder().id(5).username("홍길동").email("홍길동@naver.com").build();
//			}
//		});
//		return user;
		
		// 세 번째 방법
		User user = userRepository.findById(id).orElseThrow(()-> {
				return new NullPointerException("값이 없습니다.");
		});
				
		return user;
	}
}
