package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomValidationException;
import com.jungwoon.tistory_clone_springboot.web.dto.manage.BlogCreateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.manage.BlogListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 생성
    @Transactional
    public void create(BlogCreateRequestDto requestDto, PrincipalDetails principalDetails) {

        String blogName = requestDto.getName();
        String blogUrl = requestDto.getUrl() + ".tistory.com";

        if( blogRepository.existsByName(blogName) || blogRepository.existsByUrl(blogUrl)){
            throw new CustomValidationException("블로그 이름이나 url이 중복됩니다.", null);
        }

        blogRepository.mSave(blogName, blogUrl, principalDetails.getUser().getId());
    }

    // 블로그 userId로 목록 가져오기
    @Transactional(readOnly = true)
    public List<BlogListResponseDto> blogList(Long userId) {
        List<BlogListResponseDto> respBlogs = new ArrayList<>();

        List<Blog> blogEntities = blogRepository.getByUserId(userId);

        blogEntities.forEach(entity -> {
            respBlogs.add(BlogListResponseDto.builder()
                    .name(entity.getName())
                    .url(entity.getUrl())
                    .build());
        });

        return respBlogs;
    }
}
