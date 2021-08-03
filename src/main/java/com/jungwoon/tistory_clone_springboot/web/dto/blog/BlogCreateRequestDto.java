package com.jungwoon.tistory_clone_springboot.web.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BlogCreateRequestDto {
    private String name;
    private String url;
    private String security;
    private Long blogId;
}
