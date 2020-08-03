package com.winter.securityex01.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

// OAuth2 로그인 할 때의 서비스가 된다.
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	// userRequest는 code를 받아서 AccessToken을 응답 받은 객체
	@Override // OAuth2User를 return -> 세션이 2개가 된다.
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // Google 회원 프로필 조회
		// oAuth2User 정보를 어디에 담아서 무엇을 리턴하면 될까?
		// PrincipalDetails에 담아서 세션을 하나로 만들어서 리턴 
		// 1. PrincipalDetails에 OAuth2User 정보를 집어 넣어 준다.
		// 2. PrincipalDetails를 리턴한다.
		System.out.println("oAuth2User : " + oAuth2User); // oAuth2User : 토큰을 통해 응답받은 회원정보
		System.out.println("userRequest token : " + userRequest.getAccessToken().getTokenValue()); // userRequest : 토큰을 가지고 있음
		System.out.println("userRequest clientRegistration : " +userRequest.getClientRegistration());
		// 파싱
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return super.loadUser(userRequest); // 세션에 등록됨
	}
	
	private OAuth2User processOAuth2USer(OAuth2UserRequest userRequest, OAuth2User oAuth2User) { 
		// PrincipalDetails를 리턴
		// 일반적으로 로그인 할 때 유저 정보는 User가 들고 있음
		// 1. OAuth2로 로그인 할 때는 유저 정보를 attributes가 들고있음 <- 이거 구성해야함
		
		// 2. DB에 이 사람이 있는지 확인
		
		// 3. 있으면 : user 정보 update (구글에 회원정보 수정하면 우리는 모르니까 확인 안 하고 무조건 update)
		
		// 4. 없으면 : 회원가입시키면 됨(insert)
		
		// 5. return을 PrincipalDetails()로 하면 됨. -> PrincipalDetails의 Map 안의 attributes가 들고있으니까
		return null;
	}
}
