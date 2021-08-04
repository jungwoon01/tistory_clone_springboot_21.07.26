package com.jungwoon.tistory_clone_springboot.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserBlogCountDto {
    private Integer count;
    private boolean canCreate;
}
