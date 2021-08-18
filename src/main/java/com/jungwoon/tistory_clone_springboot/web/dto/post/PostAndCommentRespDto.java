package com.jungwoon.tistory_clone_springboot.web.dto.post;

import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class PostAndCommentRespDto {
    private Long id;
    private String title;
    private String content;
    private String security;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private List<CommentRespDto> comments;
}
