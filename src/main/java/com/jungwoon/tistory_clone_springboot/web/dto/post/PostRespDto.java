package com.jungwoon.tistory_clone_springboot.web.dto.post;

import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class PostRespDto {
    private Long id;
    private String title;
    private String content;
    private String security;
    private String userNickname;
    private String category;
    private Long categoryId;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

    @Builder
    public PostRespDto(Long id, String title, String content, String security, String userNickname,
                       String category, Long categoryId, LocalDateTime modifiedDate, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.security = security;
        this.userNickname = userNickname;
        this.category = category;
        this.categoryId = categoryId;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

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

    public String getCreatedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getModifiedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
