package com.pard.gz.zigu.apply.dto;

import com.pard.gz.zigu.apply.entity.enums.ApplyStatus;
import com.pard.gz.zigu.borrowed.entity.enums.UnitOfPeroid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyApplyResDto {
    private Long postId;
    private String firstImageUrl;
    private UnitOfPeroid unitOfPeroid;
    private Long peroid;
    private Long totalPrice;
    private ApplyStatus applyStatus;
}
