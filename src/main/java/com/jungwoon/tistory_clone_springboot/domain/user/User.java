package com.jungwoon.tistory_clone_springboot.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jungwoon.tistory_clone_springboot.domain.BaseTimeEntity;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 api 사이트
    @Column(nullable = false)
    private String registrationId;

    // oAuth attributes Id
    @Column(nullable = false)
    private String attributesId;

    // 이메일
    @Column(nullable = false)
    private String email;

    // 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 닉네임
    private String nickname;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"user"})
    private List<Blog> blogs;

    @Builder
    public User(String attributesId, String email, String nickname, Role role, String registrationId) {
        this.attributesId = attributesId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.registrationId = registrationId;
    }

    public void signUp(String nickname) {
        this.nickname = nickname;
        this.role = Role.USER;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", registrationId='" + registrationId + '\'' +
                ", attributesId='" + attributesId + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
