package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogManageRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

    // 블로그 관리 페이지
    @GetMapping("/{url}/manage")
    public String blogManage(@PathVariable String url, Model model) {

        BlogManageRespDto blogManageRespDto = blogService.blogManage(url);
        model.addAttribute("blog", blogManageRespDto);

        return "blog/manage";
    }

    // 글쓰기 페이지
    @GetMapping("/{url}/manage/newpost")
    public String newPost() {
        return "blog/manage-newpost";
    }

    // 카테고리 관리 페이지
    @GetMapping("/{url}/manage/category")
    public String manageCategory(@PathVariable String url, Model model) {

        BlogManageRespDto blogManageRespDto = blogService.blogManage(url);
        model.addAttribute("blog", blogManageRespDto);

        return "blog/manage-category";
    }
}
