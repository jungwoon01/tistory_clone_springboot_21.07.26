package com.jungwoon.tistory_clone_springboot.web.dto.category;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategorySaveRequestDto {
    private Long id;
    private String name;
    private Integer priority;
    private String security;

    public Category toEntity(Blog blog) {
        return Category.builder()
                .name(name)
                .priorityNum(priority)
                .security(security)
                .blog(blog)
                .build();
    }

    public boolean isCreated() {
        return this.id == -1;
    }

    public boolean isDeleted() {
        return this.name.equals("") && priority.equals(-1) && security.equals("");
    }
}
