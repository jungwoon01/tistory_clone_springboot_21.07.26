package com.jungwoon.tistory_clone_springboot.config;

import com.jungwoon.tistory_clone_springboot.resolver.ClientIpResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final ClientIpResolver clientIpResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(clientIpResolver);
    }
}