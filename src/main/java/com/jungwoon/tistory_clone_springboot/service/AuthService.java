package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.user.Role;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    // 유저인지 방문자인지 확인
    public boolean isUser(PrincipalDetails principalDetails) {
        return principalDetails.getUser().getRole().getKey().equals(Role.USER.getKey());
    }

    @Transactional(readOnly = true)
    public void setSessionUser(PrincipalDetails principalDetails, HttpSession httpSession) {
        User userEntity = userRepository.findByAttributesId(principalDetails.getUser().getAttributesId());

        principalDetails.setUser(userEntity, httpSession);
    }
}
