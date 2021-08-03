package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogCreateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogManageRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;
    private final HttpSession httpSession;

    // 블로그 관리 페이지
    @GetMapping("/{url}/manage")
    public String blogManage(@PathVariable String url, Model model) {

        BlogManageRespDto blogManageRespDto = blogService.blogManage(url);

        model.addAttribute("blog", blogManageRespDto);

        return "blog/manage";
    }

    @PostMapping("/{url}/manage/create")
    public String createBlog(BlogCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        blogService.create(dto, principalDetails);
        httpSession.setAttribute("pricipal", principalDetails); // 블로그 추가 세션 정보 수정
        return "redirect:/user/blog";
    }


    // 글쓰기 페이지
    @GetMapping("/{url}/manage/newpost")
    public String newPost() {
        return "blog/manage-newpost";
    }
}
