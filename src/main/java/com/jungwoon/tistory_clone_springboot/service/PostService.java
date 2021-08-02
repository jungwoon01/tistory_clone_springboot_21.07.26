package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.post.PostRepository;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void write(PostCreateRequestDto postCreateRequestDto, PrincipalDetails principalDetails) {
        String title = postCreateRequestDto.getTitle();
        String content = postCreateRequestDto.getContent();
        String security = postCreateRequestDto.getSecurity();
        Long userId = principalDetails.getUser().getId();
        Long blogId = postCreateRequestDto.getBlogId();
        Long categoryId = postCreateRequestDto.getCategoryId();

        if(categoryId == null){
            postRepository.mSaveNullCategory(title, content, security, userId, blogId);
        }

        postRepository.mSave(title, content, security, userId, blogId, categoryId);
    }
}
