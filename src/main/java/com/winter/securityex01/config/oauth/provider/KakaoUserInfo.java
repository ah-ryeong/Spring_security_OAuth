package com.winter.securityex01.config.oauth.provider;

import java.util.Map;
import java.util.Properties;

public class KakaoUserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		Map<String, Object> kakao_account =(Map<String, Object>) attributes.get("kakao_account");
		return String.valueOf(kakao_account.get("email"));
	}

	@Override
	public String getName() {
		Map<String, Object> nickname = (Map<String, Object>) attributes.get("properties");
		return String.valueOf(nickname.get("nickname"));
	}

}
