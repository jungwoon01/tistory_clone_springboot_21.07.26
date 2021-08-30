package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.CategoryService;
import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogAndCategoryRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogAndPostsRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogSidebarRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;
    private final PostService postService;
    private final CategoryService categoryService;
    private final HttpSession httpSession;

    // 블로그 글 관리 페이지
    @GetMapping("/{url}/manage/post")
    public String blogManage(@PathVariable String url, Model model, @PageableDefault(size = 10) Pageable pageable) {

        BlogAndPostsRespDto dto = blogService.blogAndPosts(url, pageable);
        Integer postCount = postService.allPostCount(url);

        model.addAttribute("blog", dto);
        model.addAttribute("postCount", postCount);

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

    // 블로그 메인 페이지
    @GetMapping("/{url}")
    public String blog(@PathVariable String url, Model model) {

        BlogSidebarRespDto blogAndCategoryRespDto = blogService.blogSidebar(url, httpSession);
        Integer postCount = postService.allPostCount(url);

        model.addAttribute("blog", blogAndCategoryRespDto);
        model.addAttribute("category", "전체 글");
        model.addAttribute("postCount", postCount);

        return "blog/blog";
    }

    // 블로그 카테고리별 페이지
    @GetMapping("/{url}/category/{categoryId}")
    public String selectedCategoryBlog(@PathVariable String url, @PathVariable Long categoryId, Model model) {

        BlogSidebarRespDto blogAndCategoryRespDto = blogService.blogSidebar(url, httpSession);
        String categoryName = categoryService.categoryName(categoryId);
        Integer postCount = postService.categoryPostCount(url, categoryId);

        model.addAttribute("blog", blogAndCategoryRespDto);
        model.addAttribute("category", categoryName);
        model.addAttribute("postCount", postCount);

        return "blog/blog";
    }
}
