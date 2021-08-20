package com.jungwoon.tistory_clone_springboot.web.dto.post;

import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Data
public class PostAndCommentRespDto {
    private Long id;
    private String title;
    private String content;
    private String security;
    private String category;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private List<CommentRespDto> comments;

    public String getCreatedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getModifiedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
