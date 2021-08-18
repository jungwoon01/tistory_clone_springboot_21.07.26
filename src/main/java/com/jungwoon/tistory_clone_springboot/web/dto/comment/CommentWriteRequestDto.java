package com.jungwoon.tistory_clone_springboot.web.dto.comment;

import com.jungwoon.tistory_clone_springboot.domain.comment.Comment;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentWriteRequestDto {
    String author;
    String password;
    String content;
    Long postId;

    public Comment toEntity(Post post) {
        return Comment.builder()
                .author(author)
                .password(password)
                .content(content)
                .post(post)
                .build();
    }
}
