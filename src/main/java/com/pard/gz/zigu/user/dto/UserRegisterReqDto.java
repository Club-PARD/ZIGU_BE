package com.pard.gz.zigu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterReqDto {
    private String studentMail;
    private String schoolName;
    private String nickname;
    private String password;
}
