package com.pard.gz.zigu.post.controller;

import com.pard.gz.zigu.config.security.CustomUserDetails;
import com.pard.gz.zigu.post.dto.*;
import com.pard.gz.zigu.responseDto.ApiResponse;
import com.pard.gz.zigu.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "유저 ID를 받고 새 게시글 생성띠띠.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostCreateResDto>> create(@RequestParam Long userId, @ModelAttribute PostCreateReqDto postCreateReqDto)
            throws IOException {
        PostCreateResDto dto = postService.createPost(userId, postCreateReqDto);
        return ResponseEntity.ok(ApiResponse.success(dto));
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

    @Operation(summary = "현재 게시글 삭제", description = "게시물 id 받고 그 게시물 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<List<Void>>> deletePost(Long postId){
        System.out.println("받은 postId : "+postId);
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "게시글 삭제 성공", null));
    }

    @Operation(summary = "게시물 검색", description = "키워드(ex. 충전기, 캐리어)로 물품 게시물 검색")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostSearchResDto>>> searchPost(
            @RequestParam String keyword) {
        List<PostSearchResDto> result = postService.searchByItemName(keyword);
        return ResponseEntity.ok(ApiResponse.success(result));
    }


}
