package com.jungwoon.tistory_clone_springboot.domain.post;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByIdAndBlog(Long id, Blog blog);
}
