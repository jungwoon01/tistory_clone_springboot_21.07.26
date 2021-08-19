package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.service.BlogService;
import com.jungwoon.tistory_clone_springboot.service.CategoryService;
import com.jungwoon.tistory_clone_springboot.service.PostService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogListResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.category.CategorySaveRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostCreateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostUpdateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.SecurityUpdateRequestDto;
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
    private final CategoryService categorySave;

    // post 작성
    @PostMapping("/{url}/manage/newpost")
    public ResponseEntity<?> writePost(@RequestBody PostCreateRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String url) {
        System.out.println("컨트롤러 진입");
        postService.write(dto, principalDetails, url);
        System.out.println("컨트롤러 끝");
        return new ResponseEntity<>(new CMResponseDto<>(1, "글 작성 성공", null), HttpStatus.CREATED);
    }

    // 블로그 리스트
    @GetMapping("/blog/api/blogs")
    public ResponseEntity<?> blogs(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인 확인
        if (principalDetails == null) {
            return new ResponseEntity<>(new CMResponseDto<>(0, "로그인이 되어있지 않습니다.", null), HttpStatus.OK);
        }

        List<BlogListResponseDto> dto = blogService.blogList(principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMResponseDto<>(1, "블로그 리스트 불러오기 성공", dto), HttpStatus.OK);
    }

    // 카테고리 추가
    @PostMapping("/{url}/manage/category/save")
    public ResponseEntity<?> saveCategory(@PathVariable String url, @RequestBody List<CategorySaveRequestDto> categoryList,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {

        categorySave.categorySave(url, categoryList, principalDetails);

        return new ResponseEntity<>(new CMResponseDto<>(1, "카테고리 저장 성공", null), HttpStatus.OK);
    }

    // 글 공개/비공개 수정
    @PutMapping("/{url}/manage/post/security")
    public ResponseEntity<?> securityUpdate(@RequestBody SecurityUpdateRequestDto dto, @PathVariable String url) {

        postService.securityUpdate(dto, url);

        return new ResponseEntity<>(new CMResponseDto<>(1, "글 공개/비공개 업데이트 성공", null), HttpStatus.OK);
    }

    // 글 수정
    @PutMapping("/{url}/manage/post/modify")
    public ResponseEntity<?> modifyPost(@RequestBody PostUpdateRequestDto dto) {

        postService.modifyPost(dto);

        return new ResponseEntity<>(new CMResponseDto<>(1, "글 수정 성공", null), HttpStatus.OK);
    }

    // 글 삭제
    @DeleteMapping("/{url}/manage/post/delete")
    public ResponseEntity<?> deletePost(Long postId) {

        postService.deletePost(postId);

        return new ResponseEntity<>(new CMResponseDto<>(1, "글 삭제 성공", null), HttpStatus.OK);
    }
}
