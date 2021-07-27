package com.jungwoon.tistory_clone_springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByAttributesId(String attributesId);
}
