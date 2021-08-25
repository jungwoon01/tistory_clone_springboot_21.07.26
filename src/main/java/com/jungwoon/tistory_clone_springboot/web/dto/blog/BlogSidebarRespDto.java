package com.jungwoon.tistory_clone_springboot.web.dto.blog;

import com.jungwoon.tistory_clone_springboot.web.dto.category.CategoryAndPostCountRespDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BlogSidebarRespDto {
    private Long id;
    private String name;
    private String url;
    private Boolean isHost;
    private List<CategoryAndPostCountRespDto> categories;
}
