package com.jungwoon.tistory_clone_springboot.web.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryAndPostCountRespDto {
    BigInteger id;
    String name;
    BigInteger postCount;
}
