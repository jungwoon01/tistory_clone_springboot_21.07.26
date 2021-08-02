package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.web.dto.manage.BlogCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

    @PostMapping("{url}/manage/create")
    public String createBlog(BlogCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        blogService.create(dto, principalDetails);
        return "redirect:/user/blog";
    }

    @GetMapping("{url}/manage")
    public String blogManage() {
        return "blog/manage";
    }

    // 글쓰기 페이지
    @GetMapping("{url}/manage/newpost")
    public String newPost() {
        return "blog/manage-newpost";
    }
}
