package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomValidationException;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogCreateRequestDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogListResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.BlogManageRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.user.UserBlogCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    // 블로그 생성
    @Transactional
    public void create(BlogCreateRequestDto requestDto, PrincipalDetails principalDetails) {

        String blogName = requestDto.getName();
        String blogUrl = requestDto.getUrl() + ".tistory.com";

        // 중복 유효성 검사
        if( blogRepository.existsByName(blogName) || blogRepository.existsByUrl(blogUrl)){
            throw new CustomValidationException("블로그 이름이나 url이 중복됩니다.", null);
        }

        // 블로그 수 검사(5개 이하)
        if(isCreateBlog(principalDetails.getUser().getId())) {
            throw new CustomException("개설 가능한 블로그 수를 초과했습니다.");
        }

        blogRepository.mSave(blogName, blogUrl, principalDetails.getUser().getId());
        User userEntity = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(() -> {
            throw new CustomException("로그인 후 다시 시도 하세요.");
        });

        // 유저 정보 수정
        principalDetails.setUser(userEntity);
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

    // 주소로 블로그 가져오기
    @Transactional(readOnly = true)
    public BlogManageRespDto blogManage(String url) {

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("잘못된 블로그 주소 입니다.");
        });

        return new BlogManageRespDto(blogEntity);
    }

    // 블로그 countDto
    @Transactional(readOnly = true)
    public UserBlogCountDto count(PrincipalDetails principalDetails) {
        UserBlogCountDto countDto = new UserBlogCountDto();
        Integer count;

        count = blogRepository.countBlogByUserId(principalDetails.getUser().getId());

        count = 5 - count;
        if(count > 0) countDto.setCanCreate(true);
        countDto.setCount(count);

        return countDto;
    }

    // 블로그 만들 수 있는지
    @Transactional(readOnly = true)
    public boolean isCreateBlog(Long userId) {
        int count = blogRepository.countBlogByUserId(userId);
        return count <= 5;
    }
}
