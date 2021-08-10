package com.jungwoon.tistory_clone_springboot.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class PostListRespDto {
    private Long id;
    private String title;
    private String security;
    private String userNickname;
    private String category;
    private LocalDateTime localDateTime;
}
