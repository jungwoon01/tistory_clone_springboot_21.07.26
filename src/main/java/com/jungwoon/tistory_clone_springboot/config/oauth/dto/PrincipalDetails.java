package com.jungwoon.tistory_clone_springboot.config.oauth.dto;

import com.jungwoon.tistory_clone_springboot.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements OAuth2User, Serializable {
    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user, Map<String, Object> attributes, HttpSession httpSession) {
        this.user = user;
        this.attributes = attributes;
        httpSession.setAttribute("principal", this); // 세션에 저장
    }

    public void setUser(User user, HttpSession httpSession) {
        this.user = user;
        httpSession.setAttribute("principal", this); // 세션에 저장
    }

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
