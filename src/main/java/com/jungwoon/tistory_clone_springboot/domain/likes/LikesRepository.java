package com.jungwoon.tistory_clone_springboot.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "CREATE INTO likes(userId, postId, createdDate, modifiedDate) value (:userId, :postId, now(), now())", nativeQuery = true)
    void mSave(Long userId, Long postId);

    Likes findByIdAndUserId(Long id, Long userId);
}
