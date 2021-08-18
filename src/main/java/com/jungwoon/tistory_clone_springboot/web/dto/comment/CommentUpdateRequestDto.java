package com.jungwoon.tistory_clone_springboot.web.dto.comment;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class CommentUpdateRequestDto {
    private Long commentId;
    private String password;
    private String content;
}
