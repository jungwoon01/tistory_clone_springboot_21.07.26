package com.jungwoon.tistory_clone_springboot.domain.comment;

import com.jungwoon.tistory_clone_springboot.domain.BaseTimeEntity;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String author;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String content;

    @JoinColumn(name = "postId")
    @ManyToOne
    Post post;
}
