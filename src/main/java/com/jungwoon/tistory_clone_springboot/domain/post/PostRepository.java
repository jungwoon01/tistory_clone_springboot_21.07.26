package com.jungwoon.tistory_clone_springboot.domain.post;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 블로그 url에 대한 카테고리 id의 post 가져오기
    @Query(value =
            "SELECT * " +
            "FROM post " +
            "WHERE categoryId = :categoryId AND blogId = (SELECT id FROM blog WHERE url = :url) AND security = '공개'" +
            "ORDER BY createdDate DESC",
            nativeQuery = true)
    List<Post> mFindAllByCategoryIdAndBlogUrl(Long categoryId, String url);

    // 블로그 url에 대한 post 가져오기
    @Query(value =
            "SELECT * " +
                    "FROM post " +
                    "WHERE blogId = (SELECT id FROM blog WHERE url = :url) " +
                    "ORDER BY createdDate DESC",
            nativeQuery = true)
    Page<Post> mFindAllByBlogUrl(String url, Pageable pageable);

    Post findByIdAndBlog(Long id, Blog blog);

    Integer countAllByBlog(Blog blog);

    Integer countAllByBlogAndCategory(Blog blogEntity, Category categoryEntity);
}
