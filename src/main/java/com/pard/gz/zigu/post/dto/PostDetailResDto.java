package com.pard.gz.zigu.post.dto;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.entity.enums.Category;
import com.pard.gz.zigu.post.entity.enums.IsBorrowable;
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
    private Long user_id;
    private String nickname;
    private String itemName;
    private Long post_id;
    private IsBorrowable isBorrowable;
    private List<String> imageUrls = new ArrayList<>();
    private Long price_per_hour;
    private Long price_per_day;
    private String description;
    private Category category;
}

