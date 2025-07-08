package com.pard.gz.zigu.post.dto;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.post.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSearchResDto {
    private Long post_id;
    private String firstImageUrl;
    private String itemName;
    private Category category;
    private Long price_per_hour;
    private Long price_per_day;
}