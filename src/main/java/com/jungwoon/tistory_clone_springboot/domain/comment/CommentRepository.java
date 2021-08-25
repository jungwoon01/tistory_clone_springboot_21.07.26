package com.jungwoon.tistory_clone_springboot.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByPostId(long longValue);
}
