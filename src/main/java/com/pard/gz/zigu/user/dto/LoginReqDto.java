package com.pard.gz.zigu.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginReqDto {
    @NotBlank(message = "이메일은 필수임")
    private String studentMail;

    @NotBlank(message = "비밀번호는 필수임")
    private String password;
}

