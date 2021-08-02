package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/blog/manage/newpost")
    public ResponseEntity<?> writePost(@RequestBody PostCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.write(dto, principalDetails);
        return new ResponseEntity<>(new CMResponseDto<>(1, "", null), HttpStatus.CREATED);
    }
}
