package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.UserService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogCreateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogListResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.user.SignUpRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.user.UserBlogCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final BlogService blogService;
    private final HttpSession httpSession;

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

        UserBlogCountDto countDto = blogService.count(principalDetails);

        model.addAttribute("countDto", countDto);

        return "user/user-blog";
    }

    // 블로그 생성 페이지
    @GetMapping("/user/blog/make")
    public String make(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(!blogService.isCreateBlog(principalDetails.getUser().getId())) {
            throw new CustomException("개설 가능 블로그 수를 초과했습니다.");
        }
        return "user/user-blog-make";
    }

    // 블로그 생성
    @PostMapping("user/blog/make")
    public String createBlog(BlogCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        blogService.create(dto, principalDetails);
        httpSession.setAttribute("pricipal", principalDetails); // 블로그 추가 세션 정보 수정
        return "redirect:/user/blog";
    }
}
