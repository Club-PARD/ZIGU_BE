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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {
    private final S3Client s3; // Bean
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final SchoolRepo schoolRepo;
    private final ImageStorageService imageStorageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void createPost(Long userId, PostCreateReqDto dto) throws IOException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // post 아이디 필요하니까 일단 생성
        Post newPost = new Post();

        // 2) 이미지들 S3 업로드
        List<Image> imgs = new ArrayList<>();

        // 이미지 하나씩 꺼내서 암호화
        for (MultipartFile mf : dto.getImages()) {
            if (mf.isEmpty()) continue; // null 처리용

            // (1) S3 key 생성
            String uuid = UUID.randomUUID().toString(); // 파일 이름 중복 막기 위해 UUID 붙임
            // "posts/게시글ID/랜덤이름_원래파일이름"
            // key는 S3 내부에서 "파일의 정확한 위치"를 의미
            String key  = "posts/" + newPost.getId() + "/" + uuid + "_" + mf.getOriginalFilename();

            // (2) putObject

            // PutObjectRequest -> S3에 사진 저장할 때 필요한 request 객체
            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key) // 파일 저장 경로
                    .contentType(mf.getContentType()) // 이미지 확장자 종류
                    .acl(ObjectCannedACL.PRIVATE)      // 비공개
                    .build();

            // S3에 업로드
//            mf.getInputStream() = 파일 내용
//            mf.getSize() = 파일 크기
            s3.putObject(req, RequestBody.fromInputStream(mf.getInputStream(), mf.getSize()));

            // (3) Image 엔티티 생성
            imgs.add(Image.builder()
                    .s3Key(key)
                    .post(newPost)
                    .build());
        }

        newPost = Post.builder()
                .writer(user)
                .isBorrowable(dto.getIsBorrowable())
                .itemName(dto.getItemName())
                .images(imgs)
                .category(dto.getCategory())
                .pricePerHour(dto.getPricePerHour())
                .pricePerDay(dto.getPricePerDay())
                .description(dto.getDescription())
                .caution(dto.getCaution())
                .borrowedList(null)
                .build();

        postRepo.save(newPost);
    }

//    public void readPostBySchool(String schoolName){
//        // schoolName으로 School 찾고, id 따로 빼서 저장하기
//        Optional CurrentSchool = schoolRepo.findBySchoolName(schoolName);
//        // post 리스트 = findByschoolId
//        User user = userRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//
//    }


}
