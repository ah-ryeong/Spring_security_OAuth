package com.winter.securityex01.config.oauth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.winter.securityex01.config.auth.PrincipalDetails;
import com.winter.securityex01.config.oauth.provider.FacebookUserInfo;
import com.winter.securityex01.config.oauth.provider.GoogleUserInfo;
import com.winter.securityex01.config.oauth.provider.OAuth2UserInfo;
import com.winter.securityex01.model.User;
import com.winter.securityex01.repository.UserRepository;

@Service
// OAuth2 로그인 할 때의 서비스가 된다.
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserRepository userRepository;

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
		System.out.println("userRequest clientRegistration : " +userRequest.getClientRegistration()); // getClientRegistration() : 클라이언트가 들고있는 메모리 정보임.
		// 파싱
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return processOAuth2USer(userRequest, oAuth2User); // 세션에 등록됨
	}
	
	private OAuth2User processOAuth2USer(OAuth2UserRequest userRequest, OAuth2User oAuth2User) { 
		// PrincipalDetails를 리턴
		// 일반적으로 로그인 할 때 유저 정보는 User가 들고 있음
		
		// Attribute를 파싱해서 공통 객체로 묶는다. -> 관리가  편하다
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
		} else {
			System.out.println("Google 또는 Facebook 로그인만 가능합니다.");
		}
		
		System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
		System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
		Optional<User> userOptional = userRepository.findbyEmail(oAuth2UserInfo.getEmail());
		
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			// 있어도 update 해줘야 한다.
		} else {
			// user의 패스워드가 null 이기 때문에 OAuth 유저는 일반적으로 로그인을 할 수가 없음.
			user = User.builder()
					.username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		}
		
		// 1. OAuth2로 로그인 할 때는 유저 정보를 attributes가 들고있음 <- 이거 구성해야함
		
		// 2. DB에 이 사람이 있는지 확인
		
		// 3. 있으면 : user 정보 update (구글에 회원정보 수정하면 우리는 모르니까 확인 안 하고 무조건 update)
		
		// 4. 없으면 : 회원가입시키면 됨(insert)
		
		// 5. return을 PrincipalDetails()로 하면 됨. -> PrincipalDetails의 Map 안의 attributes가 들고있으니까
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}
