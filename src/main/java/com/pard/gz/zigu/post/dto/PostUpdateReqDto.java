package com.pard.gz.zigu.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateReqDto {
    // 수정하려는 post의 id
    private Long postId;

    private String itemName;
    private Long pricePerHour;
    private Long pricePerDay;
    private String description;
}
