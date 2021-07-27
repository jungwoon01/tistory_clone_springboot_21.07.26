package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomValidationException;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 닉네임 중복 체크 서비스
    @Transactional(readOnly = true)
    public boolean duplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 유저 등록 서비스 (닉네임등록, 권한 VISITOR->USER 변경)
    @Transactional
    public User signUp(String nickname, PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();

        // 중복된 닉네임일 때
        if(userRepository.existsByNickname(nickname)) {
            throw new CustomValidationException("중복된 닉네임입니다.", null);
        }

        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> {
            throw new RuntimeException("UserService.signUp() : 존재하지않는 유저 입니다.");
        });
        userEntity.signUp(nickname); // 닉네임 수정
        return userEntity;
    }
}
