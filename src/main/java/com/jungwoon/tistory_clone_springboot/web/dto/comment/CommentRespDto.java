package com.jungwoon.tistory_clone_springboot.web.dto.comment;

import com.jungwoon.tistory_clone_springboot.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRespDto {
    Long id;
    String author;
    String content;
    Long postId;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @Builder
    public CommentRespDto(Long id, String author, String content, Long postId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.postId = postId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public CommentRespDto(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
