package com.jungwoon.tistory_clone_springboot.config.oauth;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.OAuthAttributes;
import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 가입 사이트 식별
        Map<String, Object> oAuthAttr = super.loadUser(userRequest).getAttributes(); // 넘어온 유저 정보 map

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuthAttr); // 필요한 것만 정리된 attributes

        User userEntity = userRepository.findByAttributesId(attributes.getAttributesId()); // attributes 정보로 유저 찾기

        // 처음 로그인을 시도하는 유저
        if(userEntity == null) {
            return new PrincipalDetails(userRepository.save(attributes.toUserEntity()), oAuthAttr);
        }

        return new PrincipalDetails(userEntity, oAuthAttr);
    }
}
