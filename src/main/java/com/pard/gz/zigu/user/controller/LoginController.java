package com.pard.gz.zigu.user.controller;

import com.pard.gz.zigu.config.security.CustomUserDetails;
import com.pard.gz.zigu.responseDto.ApiResponse;
import com.pard.gz.zigu.user.dto.LoginReqDto;
import com.pard.gz.zigu.user.dto.UserSucInfoResDto;
import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.service.LoginService;
import com.pard.gz.zigu.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;
    private final LoginService loginService;

    /** 회원가입 */
    @Operation(summary = "회원가입")
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody UserSignUpReqDto dto) {
        userService.signUpUser(dto);
        // 201 Created + Location 헤더도 고려 가능
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /** 로그인 */
    @Operation(summary = "로긘")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserSucInfoResDto>> login(
            @Valid @RequestBody LoginReqDto dto,
            HttpServletRequest request
    ) {
        try {
            UserSucInfoResDto hiUser = loginService.login(dto, request);
            return ResponseEntity.ok(ApiResponse.success(hiUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(ApiResponse.fail(e.getMessage()));
        }
    }

    // 세션 유지 확인용
    @Operation(summary = "현재 로그인된 유저 정보 조회(home에서 새로고침)")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<String>> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(userDetails.getUsername()));
    }

}
