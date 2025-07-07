package com.pard.gz.zigu.apply.dto;

import com.pard.gz.zigu.borrowed.entity.enums.UnitOfPeroid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplySaveReqDto {
    private Long userId;
    private Long postId;
    private UnitOfPeroid unitOfPeroid;
    private Long peroid;
    private Long totalPrice;
}
