package com.jungwoon.tistory_clone_springboot.config;

import com.jungwoon.tistory_clone_springboot.config.oauth.CustomOAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuthUserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // csrf 토큰 검사 x
                .csrf().disable()
                // 동일 도메인에서만 페이지 표시
                .headers().frameOptions().sameOrigin()
                .and()
                // 접근 권한 설정
                .authorizeRequests()
                .antMatchers("/", "/user/login" ,"/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                // antMatchers url 외의 주소에는 권한이 있어야 접근 가능
                .anyRequest().permitAll()
                .and()
                // 로그아웃 성공하면 "/" 로 이동
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()// oauth2 로그인 추가
                .defaultSuccessUrl("/auth/role")
                .userInfoEndpoint() // oauth2 로그인을 하면 최종응답으로 회원정보를 바로 받을 수 있다.
                .userService(customOAuth2UserService); // oauth2 로그인 서비스 등록
    }
}
