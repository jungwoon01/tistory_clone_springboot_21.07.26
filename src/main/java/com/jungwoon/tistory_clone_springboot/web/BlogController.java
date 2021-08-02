package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/blog/create")
    public String createBlog(BlogCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        blogService.create(dto, principalDetails);
        return "redirect:/user/blog";
    }
}
