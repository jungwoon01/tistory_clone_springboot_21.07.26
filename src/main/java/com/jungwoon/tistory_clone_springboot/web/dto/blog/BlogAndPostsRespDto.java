package com.jungwoon.tistory_clone_springboot.web.dto.blog;

import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostListRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class BlogAndPostsRespDto {
    private Long id;
    private String name;
    private String url;
    private List<PostListRespDto> posts;
}
