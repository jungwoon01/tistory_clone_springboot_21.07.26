package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.LikesService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesApiController {

    private final LikesService likesService;

    @PostMapping("/api/likes/like")
    public ResponseEntity<?> newLikes(@AuthenticationPrincipal PrincipalDetails principalDetails, Long postId) {

        likesService.newLikes(principalDetails, postId);

        return new ResponseEntity<> (new CMResponseDto(1, "글 좋아요 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/likes/unlike")
    public ResponseEntity<?> unlikes(@AuthenticationPrincipal PrincipalDetails principalDetails, Long postId) {

        likesService.deleteLikes(principalDetails, postId);

        return new ResponseEntity<> (new CMResponseDto(1, "글 삭제 성공", null), HttpStatus.OK);
    }
}
