package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.category.CategoryRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.web.dto.category.CategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public void categorySave(String url, List<CategorySaveRequestDto> requestDtos, PrincipalDetails principalDetails) {

        Blog blogEntity = blogRepository.findByUrlAndUser(url, principalDetails.getUser()).orElseThrow(() -> {
            throw new CustomException("카테고리 저장 실패!\n로그인을 하지 않았거나, 잘못된 블로그 주소입니다.");
        });

        requestDtos.forEach((requestDto) -> {
            if(requestDto.isCreated()) {
                // 생성
                categoryRepository.save(requestDto.toEntity(blogEntity));
            } else if (requestDto.isDeleted()) {
                // 삭제
                categoryRepository.deleteById(requestDto.getId());
            } else {
                // 수정
                Category categoryEntity = categoryRepository.findById(requestDto.getId()).orElseThrow(() -> {
                    throw new CustomException("존재하지 않는 카테고리 입니다.");
                });

                categoryEntity.update(requestDto.getName(), requestDto.getSecurity(), requestDto.getPriority());
                categoryRepository.save(categoryEntity);
            }
        });
    }

    @Transactional(readOnly = true)
    public String categoryName(Long categoryId) {
        Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new CustomException("존재하는 카테고리 페이지가 아닙니다.");
        });

        return categoryEntity.getName();
    }
}
