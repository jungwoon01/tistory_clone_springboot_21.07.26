package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.service.UserService;
import com.jungwoon.tistory_clone_springboot.web.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    // 유저 등록 컨트롤러
    @PostMapping("/user/sign-up")
    public String signUp(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User userEntity = userService.signUp(signUpRequestDto.getNickname(), principalDetails);

        return "redirect:/";
    }
}
