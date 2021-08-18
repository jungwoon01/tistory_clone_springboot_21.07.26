package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.service.CommentService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.WriteCommentRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/comment/write")
    public ResponseEntity<?> writeComment(@RequestBody WriteCommentRequestDto dto) {

        CommentRespDto commentRespDto = commentService.writeComment(dto);

        return new ResponseEntity<>(new CMResponseDto<>(1, "댓글 작성 성공", commentRespDto), HttpStatus.CREATED);
    }

    // 댓글 비밀번호 확인
    @PostMapping("/api/comment/check_password")
    public ResponseEntity<?> checkPassword(Long commentId, String password) {

        Boolean isSame = commentService.checkPassword(commentId, password);

        if(isSame) {
            return new ResponseEntity<>(new CMResponseDto<>(1, "비밀번호 일치", null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new CMResponseDto<>(-1, "비밀번호 불일치", null), HttpStatus.OK);
    }


    // 댓글 삭제
    @DeleteMapping("/api/comment/delete")
    public ResponseEntity<?> deleteComment(Long commentId) {

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(new CMResponseDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }
}
