package com.winter.securityex01.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

// ORM : Object Relation(테이블) Mapping
// 자바 오브젝트로 테이블을 매핑시켜주는 것 
// 프로그램을(오라클, MySQL 등) 바꿀 때 마다 알아서 테이블을 매핑시켜준다.

@Data
@Entity // 데이터베이스 테이블의 모델이 된다.
public class User {
	@Id // 프라이머리키를 걸어주는 어노테이션이다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링전략
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; // ROLE_USER, ROLE_ADMIN
	@CreationTimestamp
	private Timestamp createDate;
}
