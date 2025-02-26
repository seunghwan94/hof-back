package com.lshwan.hof.service.social;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    // OAuth2 사용자 정보 가져오기
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    Map<String, Object> attributes = oAuth2User.getAttributes(); 

    // 구글 사용자 정보에서 필요한 정보 추출
    String email = (String) attributes.get("email");
    String name = (String) attributes.get("name");

    // 사용자 정보 기반으로 User 객체 생성
    return new DefaultOAuth2User(
        Collections.singleton(new OAuth2UserAuthority("ROLE_USER", attributes)),
        attributes,
        "name");
  }  
}
