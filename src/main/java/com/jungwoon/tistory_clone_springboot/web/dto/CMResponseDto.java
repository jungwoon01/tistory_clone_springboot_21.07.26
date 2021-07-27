package com.jungwoon.tistory_clone_springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Api Controller 응답을 보내기 위한 DTO
 * ResponseEntity<>() 의 첫번째 파라미터에 사용된다.
* */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMResponseDto<T> {

    private Integer code;
    private String message;
    private T data;
}
