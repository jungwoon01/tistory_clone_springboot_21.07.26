package com.jungwoon.tistory_clone_springboot.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostCreateRequestDto {
    String title;
    String content;
    String security;
    Long blogId;
    Long categoryId;
}
