package com.jungwoon.tistory_clone_springboot.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jungwoon.tistory_clone_springboot.domain.BaseTimeEntity;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.comment.Comment;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    String content;

    String security;

    @JsonIgnoreProperties({"blogs"})
    @JoinColumn(name = "userId")
    @ManyToOne
    User user;

    @JsonIgnoreProperties({"categories", "user", "posts"})
    @JoinColumn(name = "blogId")
    @ManyToOne
    Blog blog;

    @OneToOne
    Category category;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    public void updateSecurity(String security) {
        this.security = security;
    }

    public void updatePost(Long id, String title, String content, String security, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.security = security;
        this.category = category;
    }
}
