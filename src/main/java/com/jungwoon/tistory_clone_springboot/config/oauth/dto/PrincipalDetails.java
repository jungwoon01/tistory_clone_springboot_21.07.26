package com.jungwoon.tistory_clone_springboot.config.oauth.dto;

import com.jungwoon.tistory_clone_springboot.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
@Data
public class PrincipalDetails implements OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> { return user.getRole().getKey(); } );

        return collection;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
