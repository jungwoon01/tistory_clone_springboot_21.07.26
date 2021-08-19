package com.jungwoon.tistory_clone_springboot.domain.category;

import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteById(Long id);

    List<Category> findAllByBlogId(Long id);
}
