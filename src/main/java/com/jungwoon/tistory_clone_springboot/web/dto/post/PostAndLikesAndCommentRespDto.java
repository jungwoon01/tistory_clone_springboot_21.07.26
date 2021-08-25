package com.jungwoon.tistory_clone_springboot.web.dto.post;

import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostAndLikesAndCommentRespDto {
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
    private List<CommentRespDto> comments;

    public PostAndLikesAndCommentRespDto(PostAndLikesDto dto, List<CommentRespDto> comments) {
        this.id = dto.getId();
        this.createdDate = dto.getCreatedDate();
        this.modifiedDate = dto.getModifiedDate();
        this.content = dto.getContent();
        this.security = dto.getSecurity();
        this.title = dto.getTitle();
        this.blogId = dto.getBlogId();
        this.categoryId = dto.getCategoryId();
        this.userId = dto.getUserId();
        this.isLikes = dto.getIsLikes();
        this.likesCount = dto.getLikesCount();
        this.comments = comments;
    }

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
