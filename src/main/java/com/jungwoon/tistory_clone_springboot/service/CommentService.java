package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.domain.comment.Comment;
import com.jungwoon.tistory_clone_springboot.domain.comment.CommentRepository;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.post.PostRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomApiException;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.WriteCommentRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    @Transactional
    public CommentRespDto writeComment(WriteCommentRequestDto dto) {

        Post postEntity = postRepository.findById(dto.getPostId()).orElseThrow(() -> {
            throw new CustomApiException("댓글 작성 실패\n존재하지 않는 글 입니다.");
        });

        Comment commentEntity = commentRepository.save(dto.toEntity(postEntity));

        return CommentRespDto.builder()
                .id(commentEntity.getId())
                .author(commentEntity.getAuthor())
                .content(commentEntity.getContent())
                .postId(commentEntity.getPost().getId())
                .createdDate(commentEntity.getCreatedDate())
                .modifiedDate(commentEntity.getModifiedDate())
                .build();
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new CustomApiException("존재하는 댓글이 아닙니다.");
        });

        commentRepository.delete(commentEntity);
    }

    // 비밀번호 일치 여부 확인
    @Transactional(readOnly = true)
    public Boolean checkPassword(Long commentId, String password) {
        Comment commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new CustomApiException("존재하는 댓글이 아닙니다.");
        });

        return commentEntity.getPassword().equals(password);
    }
}
