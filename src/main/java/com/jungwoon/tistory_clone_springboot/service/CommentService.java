package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.domain.comment.Comment;
import com.jungwoon.tistory_clone_springboot.domain.comment.CommentRepository;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.post.PostRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomApiException;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.WriteCommentRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.WriteCommentRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public WriteCommentRespDto writeComment(WriteCommentRequestDto dto) {

        Post postEntity = postRepository.findById(dto.getPostId()).orElseThrow(() -> {
            throw new CustomApiException("댓글 작성 실패\n존재하지 않는 글 입니다.");
        });

        Comment commentEntity = commentRepository.save(dto.toEntity(postEntity));

        return WriteCommentRespDto.builder()
                .author(commentEntity.getAuthor())
                .content(commentEntity.getContent())
                .postId(commentEntity.getPost().getId())
                .build();
    }
}
