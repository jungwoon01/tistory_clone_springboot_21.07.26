package com.jungwoon.tistory_clone_springboot.domain.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    boolean existsByName(String name);
    boolean existsByUrl(String url);

    // 생성
    @Modifying
    @Query(value = "INSERT INTO blog(name, url, userId) value (:name, :url, :userId)", nativeQuery = true)
    void mSave(String name, String url, Long userId);

    // 블로그 주인 id로 블로그 불러오기
    List<Blog> getByUserId(Long userId);
}
