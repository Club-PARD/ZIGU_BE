package com.pard.gz.zigu.apply.dto;

import com.pard.gz.zigu.apply.entity.Apply;
import com.pard.gz.zigu.borrowed.entity.enums.UnitOfPeroid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyListResDto {
    private Long postId;
    private Long userId;
    private String itemName;
    private List<ApplyInfo> applyList;

    @Getter
    @Builder
    public static class ApplyInfo {
        private Long applyId;               // 신청서 id
        private Long applierId;             // 신청자 id
        private String applierNickname;     // 신청자 닉네임
        private String firstImageUrl;       // 게시물 첫 번째 이미지(없으면 null)
        private Long period;                // 신청 기간
        private UnitOfPeroid unitOfPeriod;  // 기간 단위
        private Long totalPrice;            // 총 금액
    }
}
