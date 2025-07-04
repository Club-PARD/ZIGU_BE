package com.pard.gz.zigu.post.service;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.Image.repository.ImageRepo;
import com.pard.gz.zigu.Image.service.ImageStorageService;
import com.pard.gz.zigu.post.dto.PostCreateReqDto;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.post.repository.PostRepo;
import com.pard.gz.zigu.school.entity.School;
import com.pard.gz.zigu.school.repository.SchoolRepo;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final SchoolRepo schoolRepo;
    private final ImageStorageService imageStorageService;

    @Transactional
    public void createPost(Long userId, PostCreateReqDto postCreateReqDto) throws IOException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));



        Post newPost = Post.builder()
                .writer(user)
                .isBorrowable(postCreateReqDto.getIsBorrowable())
                .itemName(postCreateReqDto.getItemName())
                .category(postCreateReqDto.getCategory())
                .pricePerHour(postCreateReqDto.getPricePerHour())
                .pricePerDay(postCreateReqDto.getPricePerDay())
                .description(postCreateReqDto.getDescription())
                .caution(postCreateReqDto.getCaution())
                .borrowedList(null)
                .build();

        // 2) 이미지들 루프 (최대 5장)
        for (MultipartFile file : postCreateReqDto.getImages()) {
            if (file.isEmpty()) continue;

            // 2-1) 로컬 static/images/ 에 저장 → 경로 문자열 받기
            String imageUrl = imageStorageService.store(file);

            // 2-2) Post와 묶인 Image 엔티티 생성
            Image image = new Image(imageUrl, newPost); // post_id FK는 JPA가 나중에 채움

            new Image(imageUrl, newPost);  // ← 저장은 postRepo.save(post)로 한 방에
        }

        postRepo.save(newPost);
    }

    public void readPostBySchool(String schoolName){
        // schoolName으로 School 찾고, id 따로 빼서 저장하기
        Optional CurrentSchool = schoolRepo.findBySchoolName(schoolName);
        // post 리스트 = findByschoolId
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


    }


}
