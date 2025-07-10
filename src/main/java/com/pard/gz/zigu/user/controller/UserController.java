package com.pard.gz.zigu.user.controller;

import com.pard.gz.zigu.apply.dto.ApplyListResDto;
import com.pard.gz.zigu.config.security.CustomUserDetails;
import com.pard.gz.zigu.post.dto.PostPreviewDto;
import com.pard.gz.zigu.responseDto.ApiResponse;
import com.pard.gz.zigu.user.dto.UserInfoResDto;
import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // cert로 바꿔줘?
public class UserController {
    private final UserService userService;

    @Operation(summary = "내가 올린 물품", description = "현재 로그인 한 user가 올린 모든 게시글 불러오기")
    @GetMapping("/mypost")
    public ResponseEntity<ApiResponse<List<PostPreviewDto>>> readUserPost(
            @AuthenticationPrincipal CustomUserDetails user){
        User currentUser = user.getUser();
        List<PostPreviewDto> dtos = userService.readUserPost(currentUser);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @Operation(summary = "내 프로필 조회", description = "현재 로그인 한 user의 프로필 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<UserInfoResDto>> readUserInfo(
            @AuthenticationPrincipal CustomUserDetails user){
        Long userId = user.getUser().getId();
        UserInfoResDto dto = userService.readUserInfo(userId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @Operation(summary = "회원 탈퇴", description = "현재 로그인 한 user 탈퇴 ㄹㅊㄱㄹ")
    @DeleteMapping("/quit")
    public ResponseEntity<ApiResponse<List<Void>>> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           HttpServletRequest request) {
        User currentUser = userDetails.getUser();
        userService.deleteUser(currentUser);

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(new ApiResponse<>(200, true, "회원 탈퇴 성공", null));
    }

}
