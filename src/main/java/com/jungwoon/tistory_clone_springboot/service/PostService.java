package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.category.CategoryRepository;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.post.PostRepository;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostCreateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostListRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;

    // post 작성 서비스
    @Transactional
    public void write(PostCreateRequestDto postCreateRequestDto, PrincipalDetails principalDetails, String url) {
        System.out.println("서비스 진입");
        Long categoryId = postCreateRequestDto.getCategory();

        User userEntity = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(() -> {
            throw new CustomException("로그인 후 진행해주세요");
        });

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 입니다.");
        });

        // 카테고리를 선택 하지 않았을 경우
        if(categoryId == 0) {
            postRepository.save(postCreateRequestDto.toEntity(userEntity, blogEntity));
            System.out.println("카테고리 없는 서비스 끝");
            return;
        }

        // 카테고리를 선택 했을 경우
        Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 카테고리 입니다.");
        });

        postRepository.save(postCreateRequestDto.toEntity(categoryEntity, userEntity, blogEntity));
        System.out.println("카테고리 있는 서비스 끝");
    }


    // 글 목록 가져오기
    @Transactional(readOnly = true)
    public List<PostListRespDto> posts(String url) {

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("현재 주소의 블로그를 찾을 수 없습니다.");
        });

        System.out.println("getpost");

        List<Post> posts = blogEntity.getPosts();

        System.out.println(posts);

        List<PostListRespDto> postListRespDtos = new ArrayList<>();

        posts.forEach(post -> {
            postListRespDtos.add(PostListRespDto.builder()
                    .title(post.getTitle())
                    .category(post.getCategory().getName())
                    .security(post.getSecurity())
                    .userNickname(post.getUser().getNickname())
                    .build()
            );
        });

        return postListRespDtos;
    }
}
