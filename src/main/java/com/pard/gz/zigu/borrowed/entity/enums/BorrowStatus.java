package com.pard.gz.zigu.borrowed.entity.enums;

// 대여 전은 필요 없음 -> 대여가 시작되어야(최소 대여중) Borrowed가 생겨야 하므로
public enum BorrowStatus {
    BORROWED,   // 대여 중
    RETURNED    // 반납 완료
}
