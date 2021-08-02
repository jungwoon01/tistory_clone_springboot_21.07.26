package com.jungwoon.tistory_clone_springboot.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Data
public class SignUpRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$") // 닉네임은 영문 대/소문자, 숫자로 4~8자로 구성
    private String nickname;
}
