package com.jungwoon.tistory_clone_springboot.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostAndLikesRespDto {
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

    public boolean getIsLikes() {
        return this.isLikes == 1;
    }

    public String getCreatedDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.createdDate);
    }

    public String getModifiedDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.modifiedDate);
    }
}
