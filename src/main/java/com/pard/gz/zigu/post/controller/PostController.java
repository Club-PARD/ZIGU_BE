package com.pard.gz.zigu.post.controller;

import com.pard.gz.zigu.post.dto.PostCreateReqDto;
import com.pard.gz.zigu.post.dto.PostReadAllResDto;
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

//    @PostMapping("/create")
//    public void createPost(@RequestParam Long userId, @RequestBody PostCreateReqDto postCreateReqDto){
//        postService.createPost(userId, postCreateReqDto);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam Long userId, @ModelAttribute PostCreateReqDto postCreateReqDto)
            throws IOException {
        postService.createPost(userId, postCreateReqDto);
        return ResponseEntity.ok().build();
    }

    // 현재 유저가 속한 대학교의 모든 게시물 get
    @GetMapping("/allPost")
    public ResponseEntity<List<PostReadAllResDto>> readAllPost(@RequestParam Long userId){
        postService.readAllbySchool(userId);
        return ResponseEntity.ok().build();
    }
}
