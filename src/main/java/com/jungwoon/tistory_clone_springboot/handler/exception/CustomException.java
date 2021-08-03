package com.jungwoon.tistory_clone_springboot.handler.exception;

public class CustomException extends RuntimeException{

    // 객체를 구분할 때 ( JVM 한테 중요 )
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message); // 부모의 message 필드에 저장
    }

}
