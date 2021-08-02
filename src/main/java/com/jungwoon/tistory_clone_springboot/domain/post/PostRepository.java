package com.jungwoon.tistory_clone_springboot.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query(value = "INSERT INTO post(title, content, security, userId, blogId, categoryId) " +
            "VALUES(:title, :content, :security, :userId, :blogId, :categoryId)", nativeQuery = true)
    void mSave(String title, String content, String security, Long userId, Long blogId, Long categoryId);

    @Modifying
    @Query(value = "INSERT INTO post(title, content, security, userId, blogId) " +
            "VALUES(:title, :content, :security, :userId, :blogId)", nativeQuery = true)
    void mSaveNullCategory(String title, String content, String security, Long userId, Long blogId);
}
