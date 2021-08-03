package com.jungwoon.tistory_clone_springboot.handler;

import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomValidationException;
import com.jungwoon.tistory_clone_springboot.handler.exception.NicknameValidationApiException;
import com.jungwoon.tistory_clone_springboot.util.Script;
import com.jungwoon.tistory_clone_springboot.web.dto.CMResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    // 닉네임 유효성 중복 예외 발생시 작동 핸들러
    @ExceptionHandler(NicknameValidationApiException.class)
    public ResponseEntity<?> validationException(NicknameValidationApiException e) {
        return new ResponseEntity<>(new CMResponseDto<>(-101, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    // 모든 웹 컨트롤러의 유효성 예외 발생시 작동 핸들러
    // Script 리턴
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationException(CustomValidationException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        if(e.getErrorMap() == null) {
            return new ResponseEntity<> (Script.back(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (Script.back(e.getErrorMap().toString()), HttpStatus.BAD_REQUEST);
    }

    // Script 방식
    // CustomException 이 발생하면 아래의 메서드가 실행된다.
    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        return Script.back(e.getMessage());
    }
}
