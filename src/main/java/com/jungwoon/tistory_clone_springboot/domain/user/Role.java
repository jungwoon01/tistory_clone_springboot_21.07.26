package com.jungwoon.tistory_clone_springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    HOST("ROLE_HOST", "관리자"), USER("ROLE_USER", "사용자"), VISITOR("ROLE_VISITOR", "방분자");

    private final String key;
    private final String title;
}
