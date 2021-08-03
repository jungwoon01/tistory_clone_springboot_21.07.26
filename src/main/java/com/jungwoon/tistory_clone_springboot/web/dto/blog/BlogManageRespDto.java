package com.jungwoon.tistory_clone_springboot.web.dto.blog;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class BlogManageRespDto {
    private Long id;
    private String name;
    private String url;

    public BlogManageRespDto(Blog blog) {
        this.id = blog.getId();
        this.name = blog.getName();
        this.url = blog.getUrl();
    }
}
