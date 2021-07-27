package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    // 방문자면 회원가입
    @GetMapping("/auth/sign-up")
    public String signUp(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 유저일 때
        if (authService.isUser(principalDetails))
            return "redirect:/";

        // 방문자일 때
        return "sign-up";
    }

    // 로그인 페이지
    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }
}
