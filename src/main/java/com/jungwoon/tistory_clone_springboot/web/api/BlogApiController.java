package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogListResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final PostService postService;
    private final BlogService blogService;
    // post 작성
    @PostMapping("/{url}/manage/newpost")
    public ResponseEntity<?> writePost(@RequestBody PostCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String url) {
        postService.write(dto, principalDetails);
        return new ResponseEntity<>(new CMResponseDto<>(1, "글 작성", null), HttpStatus.CREATED);
    }
    // 블로그 리스트
    @GetMapping("/blog/api/blogs")
    public ResponseEntity<?> blogs(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인 확인
        if(principalDetails == null) {
            return new ResponseEntity<>(new CMResponseDto<>(0, "로그인이 되어있지 않습니다.", null), HttpStatus.OK);
        }

        List<BlogListResponseDto> dto = blogService.blogList(principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMResponseDto<>(1, "블로그 리스트 불러오기 성공", dto), HttpStatus.OK);
    }
}
