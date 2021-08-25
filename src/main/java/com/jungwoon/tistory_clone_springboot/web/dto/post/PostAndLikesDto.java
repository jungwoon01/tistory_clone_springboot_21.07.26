package com.jungwoon.tistory_clone_springboot.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostAndLikesDto {
    private BigInteger id;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String content;
    private String security;
    private String title;
    private BigInteger blogId;
    private BigInteger categoryId;
    private BigInteger userId;
    private Integer isLikes;
    private BigInteger likesCount;
}
