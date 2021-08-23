package com.jungwoon.tistory_clone_springboot.domain.likes;

import com.jungwoon.tistory_clone_springboot.domain.BaseTimeEntity;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @JoinColumn(name = "postId")
    @ManyToOne
    private Post post;
}
