package com.pard.gz.zigu.borrowed.dto;

import com.pard.gz.zigu.borrowed.controller.BorrowedController;
import com.pard.gz.zigu.borrowed.entity.enums.BorrowStatus;
import com.pard.gz.zigu.borrowed.entity.enums.UnitOfPeroid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrInfoResDto { // 빌린 내역
    private Long postId;
    private Long borrowedId; // 대여 내역의 ID
    private String firstImageUrl; // 첫번째 사진
    private String itemName; // post 제목
    private Long pricePerDay; // 일일 가격
    private Long pricePerHour; // 시간 당 가격

    private BorrowStatus borrowStatus; // 대여 상태
    private Long peroid; // 대여 기간
    private UnitOfPeroid unitOfPeroid; // 대여 단위 (일 / 시간)
    private Long totalPrice; // 총 대여 가격
}
