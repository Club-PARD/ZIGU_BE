package com.pard.gz.zigu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSucInfoResDto { // 로그인 성공 직후 front가 필요한 거
    private Long userId;
    private String nickname;
}
