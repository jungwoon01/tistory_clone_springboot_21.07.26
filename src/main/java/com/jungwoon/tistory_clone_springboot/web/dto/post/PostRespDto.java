package com.jungwoon.tistory_clone_springboot.web.dto.post;

import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostRespDto {
    Long id;
    String title;
    String content;
    String security;
    Long categoryId;
    LocalDateTime modifiedDate;
    LocalDateTime createdDate;

    @Builder
    public PostRespDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.security = post.getSecurity();
        this.categoryId = post.getCategory() == null ? 0 : post.getCategory().getId();
        this.modifiedDate = post.getModifiedDate();
        this.createdDate = post.getCreatedDate();
    }
}
