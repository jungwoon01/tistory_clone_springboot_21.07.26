package com.jungwoon.tistory_clone_springboot.domain.post;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String content;

    String security;

    @JoinColumn(name = "userId")
    @ManyToOne
    User user;

    @JoinColumn(name = "blogId")
    @ManyToOne
    Blog blog;

    @JoinColumn(name = "categoryId")
    @ManyToOne
    Category category;
}
