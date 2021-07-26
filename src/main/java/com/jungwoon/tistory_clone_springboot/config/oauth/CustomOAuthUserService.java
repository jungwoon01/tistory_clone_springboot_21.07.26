package com.jungwoon.tistory_clone_springboot.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 가입 사이트 식별
        Map<String, Object> oAuthAttr = super.loadUser(userRequest).getAttributes(); // 넘어온 유저 정보 map

        System.out.println("registrationId | oAuthAttr : " + registrationId + " | " + oAuthAttr);

        return null;
    }
}
