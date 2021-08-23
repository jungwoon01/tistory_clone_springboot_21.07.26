package com.jungwoon.tistory_clone_springboot.web.dto.likes;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LikesListRespDto {
    private Long id;
    private Integer count;
    private Long userId;
    private Long postId;
}
