package com.jungwoon.tistory_clone_springboot.handler.exception;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

    // 객체를 구분할 때 ( JVM 한테 중요 )
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message); // 부모의 message 필드에 저장
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
