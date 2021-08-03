package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.UserService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogListResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.user.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final BlogService blogService;

    // 유저 등록 컨트롤러
    @PostMapping("/user/sign-up")
    public String signUp(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        userService.signUp(signUpRequestDto.getNickname(), principalDetails);

        return "redirect:/";
    }

    // 블로그 목록 페이지
    @GetMapping("/user/blog")
    public String user(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Integer count = blogService.count(principalDetails);

        model.addAttribute("count", count);

        return "user/user-blog";
    }

    // 블로그 생성 페이지
    @GetMapping("/user/blog/make")
    public String make() {
        return "user/user-blog-make";
    }
}
