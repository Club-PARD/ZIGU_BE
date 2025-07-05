package com.pard.gz.zigu.post.controller;

import com.pard.gz.zigu.post.dto.PostCreateReqDto;
import com.pard.gz.zigu.post.dto.PostDetailResDto;
import com.pard.gz.zigu.post.dto.PostHomeResDto;
import com.pard.gz.zigu.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@RequestParam Long userId, @ModelAttribute PostCreateReqDto postCreateReqDto)
            throws IOException {
        postService.createPost(userId, postCreateReqDto);
        return ResponseEntity.ok().build();
    }

    // 현재 유저가 속한 대학교의 모든 게시물 get
    @Operation(summary = "메인(홈) 게시글 불러오기", description = "유저 ID를 받고 유저의 학교 이름과 유저가 소속된 학교의 게시글들 불러오기.")
    @GetMapping("/home")
    public ResponseEntity<PostHomeResDto> readHomePosts(@RequestParam Long userId){
        postService.readHomePosts(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "상세 페이지", description = "게시물 id 받고 상세페이지 데이터 불러오기.")
    @GetMapping("/detail")
    public ResponseEntity<PostDetailResDto> readDetailPost(@RequestParam Long postId){
        postService.readDetailPost(postId);
        return ResponseEntity.ok().build();
    }

}
