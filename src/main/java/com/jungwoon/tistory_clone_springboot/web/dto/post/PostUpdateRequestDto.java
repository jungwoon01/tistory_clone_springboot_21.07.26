package com.jungwoon.tistory_clone_springboot.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostUpdateRequestDto {
    private Long id;
    private String title;
    private String content;
    private String security;
    private Long categoryId;
}
