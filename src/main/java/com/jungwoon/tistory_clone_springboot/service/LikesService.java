package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.likes.Likes;
import com.jungwoon.tistory_clone_springboot.domain.likes.LikesRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    public final LikesRepository likesRepository;

    // 좋아요 하기
    @Transactional
    public void newLikes(PrincipalDetails principalDetails, Long postId) {
        try {
            likesRepository.mSave(principalDetails.getUser().getId(), postId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomApiException("좋아요를 하는 도중 문제가 생겼습니다.");
        }
    }

    // 좋아요 취소
    @Transactional
    public void deleteLikes(PrincipalDetails principalDetails, Long postId) {
        Likes likesEntity = likesRepository.findLikesByUserIdAndPostId(principalDetails.getUser().getId(), postId);

        if(likesEntity == null) {
            throw new CustomApiException("존재하지 않는 좋아요 입니다");
        }

        likesRepository.delete(likesEntity);
    }

}
