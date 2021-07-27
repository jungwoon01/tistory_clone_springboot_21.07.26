package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.user.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean isUser(PrincipalDetails principalDetails) {
        return principalDetails.getUser().getRole().getKey().equals(Role.USER.getKey());
    }
}
