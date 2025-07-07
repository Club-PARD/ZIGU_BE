package com.pard.gz.zigu.post.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHomeResDto { // 해당 학교의 모든 게시물
    private Long userId;
    private String schoolName;
    private List<PostPreviewDto> posts;
}
