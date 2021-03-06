package com.jungwoon.tistory_clone_springboot.web.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class BlogListResponseDto {
    private Long id;
    private String name;
    private String url;
}
