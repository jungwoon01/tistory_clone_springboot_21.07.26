package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.user.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // 유저인지 방문자인지 확인
    public boolean isUser(PrincipalDetails principalDetails) {
        return principalDetails.getUser().getRole().getKey().equals(Role.USER.getKey());
    }
}
