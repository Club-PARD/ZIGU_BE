package com.pard.gz.zigu.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyOkReqDto {
    private Long applyId;
    private Long applierId;
}
