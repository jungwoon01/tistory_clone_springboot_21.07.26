package com.jungwoon.tistory_clone_springboot.domain.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jungwoon.tistory_clone_springboot.domain.BaseTimeEntity;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Category extends BaseTimeEntity {
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

    @JsonIgnoreProperties({"category"})
    @OneToMany(mappedBy = "category")
    private List<Post> posts;

    public Category update(String name, String security, Integer priorityNum) {
        this.name = name;
        this.security = security;
        this.priorityNum = priorityNum;
        return this;
    }
}
