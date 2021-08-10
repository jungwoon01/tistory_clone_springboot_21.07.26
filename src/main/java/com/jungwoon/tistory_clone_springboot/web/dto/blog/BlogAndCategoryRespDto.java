package com.jungwoon.tistory_clone_springboot.web.dto.blog;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class BlogAndCategoryRespDto {
    private Long id;
    private String name;
    private String url;
    private List<Category> categories;

    public BlogAndCategoryRespDto(Blog blog) {
        this.id = blog.getId();
        this.name = blog.getName();
        this.url = blog.getUrl();
        this.categories = blog.getCategories();
    }
}
