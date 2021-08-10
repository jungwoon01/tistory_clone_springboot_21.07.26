package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogAndCategoryRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogAndPostsRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

    // 블로그 글 관리 페이지
    @GetMapping("/{url}/manage/post")
    public String blogManage(@PathVariable String url, Model model) {

        BlogAndPostsRespDto dto = blogService.blogAndPosts(url);

        model.addAttribute("blog", dto);

        return "blog/manage-post";
    }

    // 글쓰기 페이지
    @GetMapping("/{url}/manage/newpost")
    public String newPost(@PathVariable String url, Model model) {

        BlogAndCategoryRespDto blogAndCategoryRespDto = blogService.blogManage(url);
        model.addAttribute("blog", blogAndCategoryRespDto);

        return "blog/manage-newpost";
    }

    // 카테고리 관리 페이지
    @GetMapping("/{url}/manage/category")
    public String manageCategory(@PathVariable String url, Model model) {

        BlogAndCategoryRespDto blogAndCategoryRespDto = blogService.blogManage(url);
        model.addAttribute("blog", blogAndCategoryRespDto);

        return "blog/manage-category";
    }
}
