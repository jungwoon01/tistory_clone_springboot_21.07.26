package com.jungwoon.tistory_clone_springboot.web.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentRespDto {
    Long id;
    String author;
    String content;
    Long postId;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
}
