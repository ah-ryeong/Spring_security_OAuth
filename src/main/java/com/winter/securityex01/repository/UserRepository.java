package com.winter.securityex01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.winter.securityex01.model.User;

// JpaRepository를 상속하면 자동으로 컴포넌트 스캔된다.
public interface UserRepository extends JpaRepository<User, Integer> {

	// Jpa Naiming 전략
	// SELECT * FROM user WHERE username = ?1
	User findByUsername(String username); // 네이밍쿼리 : 자동으로 쿼리를 생성해준다.
	
//	@Query(value = "SELECT * FROM user WHERE email = 1?", nativeQuery = true)
//	Optional<User> mFindEmail(String email);
	
	// SELECT * FROM user WHERE email =?1
	Optional<User> findbyEmail(String email);
	
}
