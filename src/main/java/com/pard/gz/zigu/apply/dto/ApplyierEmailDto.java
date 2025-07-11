package com.pard.gz.zigu.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyierEmailDto {
    private Long applierId;
    private String studentMail;
    private String nickName;
    private String itemName;
}
