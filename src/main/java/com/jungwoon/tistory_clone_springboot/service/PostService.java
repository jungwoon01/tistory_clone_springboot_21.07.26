package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.category.CategoryRepository;
import com.jungwoon.tistory_clone_springboot.domain.comment.Comment;
import com.jungwoon.tistory_clone_springboot.domain.comment.CommentRepository;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.domain.post.PostRepository;
import com.jungwoon.tistory_clone_springboot.domain.user.User;
import com.jungwoon.tistory_clone_springboot.domain.user.UserRepository;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomApiException;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.web.dto.comment.CommentRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.*;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final EntityManager em; // 모든 Repository 는 EntityManager 를 구현해서 만들어 있는 구현체

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
    public List<PostAndLikesAndCommentRespDto> posts(String url, HttpSession httpSession, Pageable pageable) {
        List<PostAndLikesAndCommentRespDto> respDtos = new ArrayList<>();

        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT p.*, ");
        sb.append("(SELECT EXISTS (SELECT * FROM likes WHERE postId = p.id AND userId = ?)) AS isLikes, ");
        sb.append("(SELECT COUNT(*) FROM likes WHERE postId = p.id) AS likesCount ");
        sb.append("FROM post p ");
        sb.append("WHERE p.blogId = (SELECT id FROM blog WHERE url=?) ");
        sb.append("AND p.security = '공개' ");
        sb.append("ORDER BY p.createdDate DESC ");
        sb.append("LIMIT ? OFFSET ?");

        // 물음표
        // 1. : userId
        // 2. : url
        // 3. : size
        // 4. : offset

        Long userId = 0L;
        if(httpSession.getAttribute("principal") != null) {
            userId = ((PrincipalDetails)httpSession.getAttribute("principal")).getUser().getId();
        }

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, userId)
                .setParameter(2, url)
                .setParameter(3, pageable.getPageSize())
                .setParameter(4, pageable.getOffset());

        // 쿼리 실행(qlrm 라이브러리 필요 - Dto 에 DB 결과를 매핑하기 위해서)
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<PostAndLikesDto> dtos = resultMapper.list(query, PostAndLikesDto.class);

        // 댓글 set
        dtos.forEach(post -> {
            respDtos.add(new PostAndLikesAndCommentRespDto(post, getCommentsRespDtoInPost(post)));
        });

        return respDtos;
    }

    // 블로그에 대한 선택된 카테고리의 글 리스트 리턴
    @Transactional(readOnly = true)
    public List<PostAndLikesAndCommentRespDto> posts(String url, HttpSession httpSession, String category, Pageable pageable) {
        List<PostAndLikesAndCommentRespDto> respDtos = new ArrayList<>();

        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT p.*, ");
        sb.append("(SELECT EXISTS (SELECT * FROM likes WHERE postId = p.id AND userId = ?)) AS isLikes, ");
        sb.append("(SELECT COUNT(*) FROM likes WHERE postId = p.id) AS likesCount ");
        sb.append("FROM post p ");
        sb.append("WHERE p.security = '공개' ");
        sb.append("AND p.categoryId = (SELECT id FROM category WHERE name = ? AND blogId = (SELECT id FROM blog WHERE url = ?)) ");
        sb.append("ORDER BY p.createdDate DESC ");
        sb.append("LIMIT ? OFFSET ?");

        // 물음표
        // 1. : userId
        // 2. : category
        // 3. : url
        // 4. : size
        // 5. : offset

        // 로그인한 유저의 id값 가져오기
        Long userId = 0L;
        if(httpSession.getAttribute("principal") != null) {
            userId = ((PrincipalDetails)httpSession.getAttribute("principal")).getUser().getId();
        }

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, userId)
                .setParameter(2, category)
                .setParameter(3, url)
                .setParameter(4, pageable.getPageSize())
                .setParameter(5, pageable.getOffset());

        // 쿼리 실행(qlrm 라이브러리 필요 - Dto 에 DB 결과를 매핑하기 위해서)
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<PostAndLikesDto> dtos = resultMapper.list(query, PostAndLikesDto.class);

        // 댓글 set
        dtos.forEach(post -> {
            respDtos.add(new PostAndLikesAndCommentRespDto(post, getCommentsRespDtoInPost(post)));
        });

        return respDtos;
    }

    // 글에 대한 댓글 리스트 리턴
    private List<CommentRespDto> getCommentsRespDtoInPost(PostAndLikesDto dtos) {
        List<CommentRespDto> commentRespDtos = new ArrayList<>();

        List<Comment> commentEntities = commentRepository.getAllByPostId(dtos.getId().longValue());

        commentEntities.forEach(commentEntity -> {
            commentRespDtos.add(CommentRespDto.builder()
                    .id(commentEntity.getId())
                    .modifiedDate(commentEntity.getModifiedDate())
                    .createdDate(commentEntity.getCreatedDate())
                    .postId(commentEntity.getPost().getId())
                    .content(commentEntity.getContent())
                    .author(commentEntity.getAuthor())
                    .build());
        });

        return commentRespDtos;
    }

    @Transactional(readOnly = true)
    public Integer allPostCount(String url) {
        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 입니다.");
        });

        return postRepository.countAllByBlog(blogEntity);
    }

    @Transactional(readOnly = true)
    public Integer categoryPostCount(String url, Long categoryId) {
        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 입니다.");
        });

        Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 입니다.");
        });

        return postRepository.countAllByBlogAndCategory(blogEntity, categoryEntity);
    }
}
