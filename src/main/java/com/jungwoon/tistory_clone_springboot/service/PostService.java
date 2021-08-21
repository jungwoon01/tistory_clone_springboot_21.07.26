package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.category.CategoryRepository;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.post.PostRepository;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomApiException;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;

    // post 작성 서비스
    @Transactional
    public void write(PostCreateRequestDto postCreateRequestDto, PrincipalDetails principalDetails, String url) {
        System.out.println("서비스 진입");
        Long categoryId = postCreateRequestDto.getCategory();

        User userEntity = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(() -> {
            throw new CustomException("로그인 후 진행해주세요");
        });

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 입니다.");
        });

        // 카테고리를 선택 하지 않았을 경우
        if(categoryId == 0) {
            postRepository.save(postCreateRequestDto.toEntity(userEntity, blogEntity));
            System.out.println("카테고리 없는 서비스 끝");
            return;
        }

        // 카테고리를 선택 했을 경우
        Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 카테고리 입니다.");
        });

        postRepository.save(postCreateRequestDto.toEntity(categoryEntity, userEntity, blogEntity));
        System.out.println("카테고리 있는 서비스 끝");
    }


    // 글 목록 가져오기
    @Transactional(readOnly = true)
    public List<PostRespDto> posts(String url) {

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("현재 주소의 블로그를 찾을 수 없습니다.");
        });

        List<Post> posts = blogEntity.getPosts();

        List<PostRespDto> postRespDtos = new ArrayList<>();

        posts.forEach(post -> {
            postRespDtos.add(PostRespDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .category(post.getCategory() == null ? "카테고리 없음" : post.getCategory().getName())
                    .security(post.getSecurity())
                    .userNickname(post.getUser().getNickname())
                    .createdDate(post.getCreatedDate())
                    .modifiedDate(post.getModifiedDate())
                    .build()
            );
        });

        return postRespDtos;
    }

    @Transactional
    public void securityUpdate(SecurityUpdateRequestDto dto, String url) {
        if(!dto.getSecurity().equals("공개") && !dto.getSecurity().equals("비공개")) {
            throw new CustomException("포스트 시큐리티 변경 오류!! 잘못된 접근입니다.");
        }

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 입니다.");
        });

        Post postEntity = postRepository.findByIdAndBlog(dto.getId(), blogEntity);

        if(postEntity == null) {
            throw new CustomException("잘못된 블로그 주소와 포스트 id 입니다");
        }

        postEntity.updateSecurity(dto.getSecurity());
    }

    @Transactional(readOnly = true)
    public PostRespDto post(Long id) {

        Post postEntity = postRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 글 입니다.");
        });

        return new PostRespDto(postEntity);
    }

    // 글 수정
    @Transactional
    public void modifyPost(PostUpdateRequestDto dto) {
        Post postEntity = postRepository.findById(dto.getId()).orElseThrow(()-> {
            throw new CustomException("존재하지 않는 post id 입니다.");
        });

        // 카테고리를 선택하지 않았을 때 (페이지에서는 '카테고리' 셀렉트 선택한 것으로 보임)
        if(dto.getCategoryId() == 0) {
            postEntity.updatePost(dto.getId(), dto.getTitle(), dto.getContent(), dto.getSecurity(), null);
            return;
        }

        // 카테고리를 선택했을 때
        Category categoryEntity = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->{
            throw new CustomException("존재하지 않는 카테고리입니다.");
        });

        postEntity.updatePost(dto.getId(), dto.getTitle(), dto.getContent(), dto.getSecurity(), categoryEntity);
    }

    @Transactional
    // 글 삭제
    public void deletePost(Long postId) {

        Post postEntity = postRepository.findById(postId).orElseThrow(() -> {
            throw new CustomApiException("글을 삭제할 수 없습니다.\n존재하지 않는 글 입니다.");
        });

        postRepository.delete(postEntity);
    }

    // 블로그에 대한 글 리스트 리턴
    @Transactional(readOnly = true)
    public List<PostAndCommentRespDto> postsAndComments(String url) {

        List<Post> postEntities = postRepository.mFindAllByBlogUrl(url);

        List<PostAndCommentRespDto> postAndCommentRespDtos = new ArrayList<>();

        postEntities.forEach(post -> {
            postAndCommentRespDtos.add(PostAndCommentRespDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .security(post.getSecurity())
                    .createdDate(post.getCreatedDate())
                    .modifiedDate(post.getModifiedDate())
                    .comments(getCommentsRespDtoInPost(post))
                    .build()
            );
        });

        return postAndCommentRespDtos;
    }

    // 블로그에 대한 선택 카테고리 글 리스트 리턴
    @Transactional(readOnly = true)
    public List<PostAndCommentRespDto> postsAndComments(String url, Long categoryId) {

        List<Post> postEntities = postRepository.mFindAllByCategoryIdAndBlogUrl(categoryId, url);

        List<PostAndCommentRespDto> postAndCommentRespDtos = new ArrayList<>();

        postEntities.forEach(post -> {
            postAndCommentRespDtos.add(PostAndCommentRespDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .security(post.getSecurity())
                    .createdDate(post.getCreatedDate())
                    .modifiedDate(post.getModifiedDate())
                    .comments(getCommentsRespDtoInPost(post))
                    .build()
            );
        });

        return postAndCommentRespDtos;
    }

    // 글에 대한 댓글 리스트 리턴
    private List<CommentRespDto> getCommentsRespDtoInPost(Post post) {
        List<CommentRespDto> commentRespDtos = new ArrayList<>();

        post.getComments().forEach(comment -> {
            commentRespDtos.add(CommentRespDto.builder()
                    .id(comment.getId())
                    .author(comment.getAuthor())
                    .content(comment.getContent())
                    .postId(comment.getPost().getId())
                    .createdDate(comment.getCreatedDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build());
        });

        return commentRespDtos;
    }
}
