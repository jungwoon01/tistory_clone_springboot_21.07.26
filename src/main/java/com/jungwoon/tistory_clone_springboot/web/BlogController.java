package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogAndCategoryRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogAndPostsRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostListRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;
    private final PostService postService;

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

    // post 수정 페이지
    @GetMapping("/{url}/manage/post/modify/{postId}")
    public String modifyPost(@PathVariable String url, @PathVariable Long postId, Model model) {

        BlogAndCategoryRespDto blogAndCategoryRespDto = blogService.blogManage(url);
        PostRespDto postRespDto = postService.post(postId);

        model.addAttribute("blog", blogAndCategoryRespDto);
        model.addAttribute("post", postRespDto);

        return "blog/manage-updatepost";
    }

    // 블로그 페이지
    @GetMapping("/{url}")
    public String blog(@PathVariable String url, Model model) {

        List<PostListRespDto> postListRespDto = postService.posts(url);

        model.addAttribute("posts", postListRespDto);

        return "blog/blog";
    }
}
