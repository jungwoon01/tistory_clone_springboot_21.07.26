package com.jungwoon.tistory_clone_springboot.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(userId, postId, createdDate, modifiedDate) VALUES(:userId, :postId, now(), now())", nativeQuery = true)
    void mSave(Long userId, Long postId);

    Likes findLikesByUserIdAndPostId(Long id, Long postId);
}
