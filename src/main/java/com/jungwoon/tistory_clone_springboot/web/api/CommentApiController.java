package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.service.CommentService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.WriteCommentRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment/write")
    public ResponseEntity<?> writeComment(@RequestBody WriteCommentRequestDto dto) {

        System.out.println(dto);

        CommentRespDto commentRespDto = commentService.writeComment(dto);

        return new ResponseEntity<>(new CMResponseDto<>(1, "댓글 작성 성공", commentRespDto), HttpStatus.OK);
    }
}
