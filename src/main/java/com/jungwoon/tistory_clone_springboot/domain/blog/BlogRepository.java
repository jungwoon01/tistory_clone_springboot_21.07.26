package com.jungwoon.tistory_clone_springboot.domain.blog;

import com.jungwoon.tistory_clone_springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    boolean existsByName(String name);
    boolean existsByUrl(String url);

    // 생성
    @Modifying
    @Query(value = "INSERT INTO blog(name, url, userId, createdDate, modifiedDate) value (:name, :url, :userId, now(), now())", nativeQuery = true)
    void mSave(String name, String url, Long userId);

    // 블로그 주인 id로 블로그 불러오기
    List<Blog> getByUserId(Long userId);

    // 블로그 주소로 불러오기
    Optional<Blog> findByUrl(String url);

    // 블로그 주소와 유저 id로 불러오기
    Optional<Blog> findByUrlAndUser(String url, User user);

    Integer countBlogByUserId(Long userId);
}
