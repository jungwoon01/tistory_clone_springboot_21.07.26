package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.manage.BlogCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class BlogManageController {

    private final BlogService blogService;
    private final PostService postService;

    @PostMapping("/blog/manage/create")
    public String createBlog(BlogCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        blogService.create(dto, principalDetails);
        return "redirect:/user/blog";
    }

    @GetMapping("/blog/manage")
    public String blogManage() {
        return "blog-manage";
    }

    @GetMapping("/blog/manage/newpost")
    public String newPost() {
        return "blog-manage-newpost";
    }
}
