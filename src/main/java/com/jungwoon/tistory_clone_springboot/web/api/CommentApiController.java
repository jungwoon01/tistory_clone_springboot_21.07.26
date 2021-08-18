package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.service.CommentService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentUpdateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentWriteRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/comment/write")
    public ResponseEntity<?> writeComment(@RequestBody CommentWriteRequestDto dto) {

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

    // 댓글 수정
    @PutMapping("/api/comment/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequestDto requestDto) {

        Boolean isSame = commentService.checkPassword(requestDto.getCommentId(), requestDto.getPassword());

        if(!isSame) {
            return new ResponseEntity<>(new CMResponseDto<>(-1, "비밀번호가 일치하지 않습니다.", null), HttpStatus.OK);
        }

        CommentRespDto commentRespDto = commentService.updateComment(requestDto);

        return new ResponseEntity<>(new CMResponseDto<>(1, "댓글 수정 성공", commentRespDto), HttpStatus.OK);
    }
}
