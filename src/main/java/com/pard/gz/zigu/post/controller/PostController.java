package com.pard.gz.zigu.post.controller;

import com.pard.gz.zigu.post.dto.PostCreateReqDto;
import com.pard.gz.zigu.post.dto.PostDetailResDto;
import com.pard.gz.zigu.post.dto.PostHomeResDto;
import com.pard.gz.zigu.post.service.PostService;
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

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam Long userId, @ModelAttribute PostCreateReqDto postCreateReqDto)
            throws IOException {
        postService.createPost(userId, postCreateReqDto);
        return ResponseEntity.ok().build();
    }

    // 현재 유저가 속한 대학교의 모든 게시물 get
    @GetMapping("/home")
    public ResponseEntity<PostHomeResDto> readHomePosts(@RequestParam Long userId){
        postService.readHomePosts(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<PostDetailResDto> readDetailPost(@RequestParam Long postId){
        postService.readDetailPost(postId);
        return ResponseEntity.ok().build();
    }

}
