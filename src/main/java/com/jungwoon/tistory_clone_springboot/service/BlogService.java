package com.jungwoon.tistory_clone_springboot.service;

import com.jungwoon.tistory_clone_springboot.config.oauth.dto.PrincipalDetails;
import com.jungwoon.tistory_clone_springboot.domain.blog.Blog;
import com.jungwoon.tistory_clone_springboot.domain.blog.BlogRepository;
import com.jungwoon.tistory_clone_springboot.domain.category.Category;
import com.jungwoon.tistory_clone_springboot.domain.category.CategoryRepository;
import com.jungwoon.tistory_clone_springboot.domain.post.Post;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomException;
import com.jungwoon.tistory_clone_springboot.handler.exception.CustomValidationException;
import com.jungwoon.tistory_clone_springboot.web.dto.blog.*;
import com.jungwoon.tistory_clone_springboot.web.dto.category.CategoryAndPostCountRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.post.PostRespDto;
import com.jungwoon.tistory_clone_springboot.web.dto.user.UserBlogCountDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final EntityManager em; // 모든 Repository 는 EntityManager 를 구현해서 만들어 있는 구현체

    // 블로그 생성
    @Transactional
    public void create(BlogCreateRequestDto requestDto, PrincipalDetails principalDetails) {

        String blogName = requestDto.getName();
        String blogUrl = requestDto.getUrl() + ".tistory.com";

        // 중복 유효성 검사
        if( blogRepository.existsByName(blogName) || blogRepository.existsByUrl(blogUrl)){
            throw new CustomValidationException("블로그 이름이나 url이 중복됩니다.", null);
        }

        // 블로그 수 검사(5개 이하)
        if(!isCreateBlog(principalDetails.getUser().getId())) {
            throw new CustomException("개설 가능한 블로그 수를 초과했습니다.");
        }

        blogRepository.mSave(blogName, blogUrl, principalDetails.getUser().getId());
    }

    // 블로그 userId로 목록 가져오기
    @Transactional(readOnly = true)
    public List<BlogListResponseDto> blogList(Long userId) {
        List<BlogListResponseDto> respBlogs = new ArrayList<>();

        List<Blog> blogEntities = blogRepository.getByUserId(userId);

        blogEntities.forEach(entity -> {
            respBlogs.add(BlogListResponseDto.builder()
                    .name(entity.getName())
                    .url(entity.getUrl())
                    .build());
        });

        return respBlogs;
    }

    // 주소로 블로그 가져오기
    @Transactional(readOnly = true)
    public BlogAndCategoryRespDto blogManage(String url) {

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("잘못된 블로그 주소 입니다.");
        });

        return new BlogAndCategoryRespDto(blogEntity);
    }

    // 블로그 countDto
    @Transactional(readOnly = true)
    public UserBlogCountDto count(PrincipalDetails principalDetails) {
        UserBlogCountDto countDto = new UserBlogCountDto();
        Integer count;

        count = blogRepository.countBlogByUserId(principalDetails.getUser().getId());

        count = 5 - count;
        if(count > 0) countDto.setCanCreate(true);
        countDto.setCount(count);

        return countDto;
    }

    // 블로그 만들 수 있는지
    @Transactional(readOnly = true)
    public boolean isCreateBlog(Long userId) {
        int count = blogRepository.countBlogByUserId(userId);
        return count <= 5;
    }

    // 블로그와 그 블로그의 글 목록 가져오기
    @Transactional(readOnly = true)
    public BlogAndPostsRespDto blogAndPosts(String url) {
        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("현재 주소의 블로그를 찾을 수 없습니다.");
        });

        List<PostRespDto> postRespDtos = new ArrayList<>();

        List<Post> posts = blogEntity.getPosts();


        posts.forEach(post -> {
            postRespDtos.add(PostRespDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .category(post.getCategory() != null ? post.getCategory().getName() : "카테고리 없음")
                    .security(post.getSecurity())
                    .userNickname(post.getUser().getNickname())
                    .createdDate(post.getCreatedDate())
                    .build()
            );
        });

        BlogAndPostsRespDto dto = BlogAndPostsRespDto.builder()
                .id(blogEntity.getId())
                .name(blogEntity.getName())
                .url(blogEntity.getUrl())
                .posts(postRespDtos)
                .build();

        return dto;
    }


    // blog url 로 선택된 블로그의 블로그 정보와 카테고리 정보를 리턴
    @Transactional(readOnly = true)
    public void selectedCategoryBlog(String url, String category) {
        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 주소입니다.");
        });

        List<Category> categories = categoryRepository.findAllByBlogId(blogEntity.getId());

        int a = categories.get(1).getPosts().size();
    }

    // 블로그 사이드바에 필요한 데이터를 리턴하는 메소드
    @Transactional
    public BlogSidebarRespDto blogSidebar(String url) {

        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT c.id as id, c.name as name, COUNT(p.id) as postCount ");
        sb.append("FROM category c INNER JOIN post p ");
        sb.append("ON c.id = p.categoryId ");
        sb.append("WHERE c.blogId = (SELECT id FROM blog WHERE url = ?) ");
        sb.append("AND p.security = '공개' AND c.security = '공개' ");
        sb.append("GROUP BY p.categoryId ");
        sb.append("ORDER BY c.priorityNum ASC");

        // 물음표 = url

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, url);

        // 쿼리 실행(qlrm 라이브러리 필요 - Dto 에 DB 결과를 매핑하기 위해서)
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<CategoryAndPostCountRespDto> categoryAndPostCountRespDtos = resultMapper.list(query, CategoryAndPostCountRespDto.class);

        Blog blogEntity = blogRepository.findByUrl(url).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 블로그 주소입니다.");
        });

        return BlogSidebarRespDto.builder()
                .id(blogEntity.getId())
                .name(blogEntity.getName())
                .url(blogEntity.getUrl())
                .categories(categoryAndPostCountRespDtos)
                .build();
    }
}
