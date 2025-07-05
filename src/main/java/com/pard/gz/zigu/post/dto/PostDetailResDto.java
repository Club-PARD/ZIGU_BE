package com.pard.gz.zigu.post.dto;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResDto {
    private List<Image> images = new ArrayList<>();
    private Long price_per_hour;
    private Long price_per_day;
    private String description;
    private Category category;
}

// 잠만 프리뷰 게시글 첫번째 이미지 하나만 해야하잖아
