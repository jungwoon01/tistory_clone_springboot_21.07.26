package com.jungwoon.tistory_clone_springboot.domain.blog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @JsonIgnoreProperties({"blogs"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"blog"})
    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER)
    private List<Category> categories;

    @JsonIgnoreProperties({"blog"})
    @OneToMany(mappedBy = "blog")
    private List<Post> posts;
}
