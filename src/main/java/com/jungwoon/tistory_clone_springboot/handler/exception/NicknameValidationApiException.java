package com.jungwoon.tistory_clone_springboot.handler.exception;

import java.util.HashMap;
import java.util.Map;

public class NicknameValidationApiException extends RuntimeException{

    // 객체를 구분할 때 ( JVM 한테 중요 )
    private static final long serialVersionUID = 1L;

    Map<String, String> errorMap;

    public NicknameValidationApiException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() { return errorMap; }
}
