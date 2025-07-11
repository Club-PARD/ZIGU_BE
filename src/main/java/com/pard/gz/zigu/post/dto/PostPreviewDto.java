package com.pard.gz.zigu.post.dto;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.entity.enums.Category;
import com.pard.gz.zigu.post.entity.enums.IsBorrowable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostPreviewDto {
    private Long post_id;
    private IsBorrowable isBorrowable;
    private String firstImageUrl;
    private String itemName;
    private Category category;
    private Long price_per_hour;
    private Long price_per_day;
}
