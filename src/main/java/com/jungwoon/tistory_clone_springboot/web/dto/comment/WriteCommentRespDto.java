package com.jungwoon.tistory_clone_springboot.web.dto.comment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WriteCommentRespDto {
    String author;
    String content;
    Long postId;
}
