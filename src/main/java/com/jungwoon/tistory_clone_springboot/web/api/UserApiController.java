package com.jungwoon.tistory_clone_springboot.web.api;

import com.jungwoon.tistory_clone_springboot.service.UserService;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import com.jungwoon.tistory_clone_springboot.web.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    // 닉네임 유효성,중복 체크
    @PostMapping("/api/user/duplicate")
    public ResponseEntity<?> duplicate(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {

        if(userService.duplicate(signUpRequestDto.getNickname()))
            return new ResponseEntity<>(new CMResponseDto<>(0, "닉네임 중복", null), HttpStatus.OK);

        return new ResponseEntity<>(new CMResponseDto<>(1, "닉네임 사용 가능", null), HttpStatus.OK);
    }

}
