package com.pard.gz.zigu.Image.dto;

import com.pard.gz.zigu.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImagePathReqDto {
    private Post post;
    private String s3Key;
}
