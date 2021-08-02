package com.jungwoon.tistory_clone_springboot.domain.category;

import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import lombok.*;

import javax.persistence.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "blogId")
    @ManyToOne
    private Blog blog;

    @Column(nullable = false)
    private String name;

    private String security;

    @Column(nullable = false)
    private Integer priorityNum;
}
