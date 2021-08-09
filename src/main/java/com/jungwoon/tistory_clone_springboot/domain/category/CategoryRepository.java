package com.jungwoon.tistory_clone_springboot.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteById(Long id);
}
