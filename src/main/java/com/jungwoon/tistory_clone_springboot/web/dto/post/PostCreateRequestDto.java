package com.jungwoon.tistory_clone_springboot.web.dto.post;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostCreateRequestDto {
    String title;
    String content;
    String security;
    Long category;

    public Post toEntity(Category category, User user, Blog blog) {
        return Post.builder()
                .title(title)
                .content(content)
                .security(security)
                .category(category)
                .blog(blog)
                .user(user)
                .build();
    }

    public Post toEntity(User user, Blog blog) {
        return Post.builder()
                .title(title)
                .content(content)
                .security(security)
                .blog(blog)
                .user(user)
                .build();
    }
}
