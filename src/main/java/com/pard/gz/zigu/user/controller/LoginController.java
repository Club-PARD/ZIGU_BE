package com.pard.gz.zigu.user.controller;

import com.pard.gz.zigu.commonDto.ApiResponse;
import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody UserSignUpReqDto userSignUpReqDto){
        userService.signUpUser(userSignUpReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "회원가입 성공", null));
    }

}
