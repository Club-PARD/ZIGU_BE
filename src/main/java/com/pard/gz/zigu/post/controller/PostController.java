package com.pard.gz.zigu.post.controller;

import com.pard.gz.zigu.config.security.CustomUserDetails;
import com.pard.gz.zigu.responseDto.ApiResponse;
import com.pard.gz.zigu.post.dto.PostCreateReqDto;
import com.pard.gz.zigu.post.dto.PostDetailResDto;
import com.pard.gz.zigu.post.dto.PostHomeResDto;
import com.pard.gz.zigu.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "유저 ID를 받고 새 게시글 생성띠띠.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> create(@RequestParam Long userId, @ModelAttribute PostCreateReqDto postCreateReqDto)
            throws IOException {
        postService.createPost(userId, postCreateReqDto);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "게시글 post 성공", null));
    }

    // 현재 유저가 속한 대학교의 모든 게시물 get
    @Operation(summary = "메인(홈) 게시글 불러오기", description = "유저 ID를 받고 유저의 학교 이름과 유저가 소속된 학교의 게시글들 불러오기.")
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<PostHomeResDto>> readHomePosts(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUser().getId();
        PostHomeResDto homeDto = postService.readHomePosts(userId);
        return ResponseEntity.ok(ApiResponse.success(homeDto));
    }

    @Operation(summary = "상세 페이지", description = "게시물 id 받고 상세페이지 데이터 불러오기.")
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<PostDetailResDto>> readDetailPost(@RequestParam Long postId){
        PostDetailResDto detailResDto = postService.readDetailPost(postId);
        return ResponseEntity.ok(ApiResponse.success(detailResDto));
    }



}
