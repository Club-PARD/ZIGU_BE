package com.pard.gz.zigu.user.controller;

import com.pard.gz.zigu.apply.dto.ApplyListResDto;
import com.pard.gz.zigu.config.security.CustomUserDetails;
import com.pard.gz.zigu.post.dto.PostPreviewDto;
import com.pard.gz.zigu.responseDto.ApiResponse;
import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    // TODO : 여기서부터 구현
    @Operation(summary = "내가 올린 물품", description = "현재 로그인 한 user가 올린 모든 게시글 불러오기")
    @GetMapping("/mypost")
    public ResponseEntity<ApiResponse<List<PostPreviewDto>>> readUserPost(
            @AuthenticationPrincipal CustomUserDetails user){
        User currentUser = user.getUser();
        List<PostPreviewDto> dtos = userService.readUserPost(currentUser);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }


}
