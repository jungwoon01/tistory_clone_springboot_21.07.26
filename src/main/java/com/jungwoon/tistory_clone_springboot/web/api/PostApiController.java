package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostAndLikesAndCommentRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostAndLikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/api/posts/{url}/{category}")
    public ResponseEntity<?> posts(@PathVariable String category, @PathVariable String url) {

        List<PostAndLikesAndCommentRespDto> postAndLikesDtos;

        if(category.equals("전체 글"))
            postAndLikesDtos = postService.posts(url, httpSession);
        else
            postAndLikesDtos = postService.posts(url, httpSession, category);

        return new ResponseEntity<> (new CMResponseDto<>(1, "글 목록 가져오기 성공", postAndLikesDtos), HttpStatus.OK);
    }
}
