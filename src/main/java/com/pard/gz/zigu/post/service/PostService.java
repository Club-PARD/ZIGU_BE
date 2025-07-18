package com.pard.gz.zigu.post.service;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.Image.repository.ImageRepo;
import com.pard.gz.zigu.Image.service.ImageStorageService;
import com.pard.gz.zigu.post.dto.*;
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
import java.util.stream.Collectors;

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
    public PostCreateResDto createPost(Long userId, PostCreateReqDto dto) throws IOException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        School userSchool = user.getSchool();
        /* 1️⃣  Post 껍데기 먼저 만든다 ― images 는 비어있는 ArrayList 로 초기화 */
        Post newPost = Post.builder()
                .writer(user)
                .isBorrowable(dto.getIsBorrowable())
                .itemName(dto.getItemName())
                .category(dto.getCategory())
                .school(userSchool)
                .pricePerHour(dto.getPricePerHour())
                .pricePerDay(dto.getPricePerDay())
                .description(dto.getDescription())
                .images(new ArrayList<>())   // ←★ 빈 리스트 주입
                .build();

        /* 2️⃣  MultipartFile 리스트 준비 */
        List<MultipartFile> files = dto.getImages() == null ? List.of() : dto.getImages();

        /* 3 하나씩 S3 업로드 → Image 엔티티 생성 → Post.images 에 add */
        for (MultipartFile mf : files) {
            if (mf.isEmpty()) continue;

            String originalFilename = mf.getOriginalFilename(); // "귀여운 고양이.jpg"
            String sanitized = originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            // S3 key – Post ID 안 쓰고 UUID/타임스탬프 등으로 폴더 구성
            String key = "posts/" + UUID.randomUUID() + "_" + sanitized;

            System.out.println("원래 파일 이름 : " + originalFilename);
            System.out.println("변경된 파일 이름 : " + sanitized);
            System.out.println("최종 파일 이름 : " + key);


            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(mf.getContentType())
                    .build();
            s3.putObject(req, RequestBody.fromInputStream(mf.getInputStream(), mf.getSize()));

            // Image 엔티티
            Image img = Image.builder()
                    .s3Key(key)
                    .post(newPost)     // 양방향 연결
                    .build();

            newPost.getImages().add(img);      // ←★ setter 없이도 가능
        }

        /* 4️⃣  저장 */
        postRepo.save(newPost);

        return PostCreateResDto.builder()
                .postId(newPost.getId())
                .build();
    }

    // User 소속 학교의 모든 게시물(preview) 홈으로 불러오기
    public PostHomeResDto readHomePosts(Long userId){
        // userId으로 School 찾고, id 따로 빼서 저장하기
        User currentUser = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        // save first user's school
        School userSchool = currentUser.getSchool();
        // after save that school's name
        String schoolName = userSchool.getSchoolName();

        List<Post> posts = postRepo.findAllBySchool(userSchool);

        // PostPreviewDto 정보 빌드
        // Post → PostPreviewDto  변환
        List<PostPreviewDto> postPreviewDtos = posts.stream()
                .map(post -> {// 1) 첫 번째 이미지 뽑기 (없으면 null)
                    Image firstImage = post.getImages()
                            .stream()
                            .findFirst()        // Optional<Image>
                            .orElse(null);      // 비어 있으면 null

                    String imageUrl = "https://gz-zigu.store/" + firstImage.getS3Key();
                    // 2) DTO 빌드
                    assert firstImage != null;
                    return PostPreviewDto.builder()
                            .post_id(post.getId())
                            .isBorrowable(post.getIsBorrowable())
                            .firstImageUrl(imageUrl)
                            .itemName(post.getItemName())
                            .category(post.getCategory())
                            .price_per_hour(post.getPricePerHour())
                            .price_per_day(post.getPricePerDay())
                            .build();
                })
                .toList();

        PostHomeResDto postHomeResDto = PostHomeResDto.builder()
                .userId(currentUser.getId())
                .schoolName(schoolName)
                .posts(postPreviewDtos)
                .build();

        return postHomeResDto;
    }

    // 상세페이지
    @Transactional(readOnly = true)
    public PostDetailResDto readDetailPost(Long postId){

        Post currentPost = postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));


        User writer = currentPost.getWriter();  // 게시글 작성한 사용자
        List<Image> images = currentPost.getImages();

        List<String> imageUrls = images.stream()
                .map(image -> "https://gz-zigu.store/" + image.getS3Key())
                .collect(Collectors.toList());


        currentPost.getImages().size();


        PostDetailResDto postDetailResDto = PostDetailResDto.builder()
                .user_id(writer.getId())
                .nickname(writer.getNickname())
                .itemName(currentPost.getItemName())
                .post_id(currentPost.getId())
                .isBorrowable(currentPost.getIsBorrowable())
                .imageUrls(imageUrls)
                .price_per_day(currentPost.getPricePerDay())
                .price_per_hour(currentPost.getPricePerHour())
                .description(currentPost.getDescription())
                .category(currentPost.getCategory())
                .build();

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("이미지 이름" + imageUrls);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        return postDetailResDto;
    }

    public void deletePost(Long postId){
        postRepo.deleteById(postId);
    }

    public List<PostSearchResDto> searchByItemName(String keyword) {
        List<Post> findedPosts = postRepo.findByItemNameContainingIgnoreCase(keyword);

        List<PostSearchResDto> dtos = findedPosts.stream()
                .map(findedPost -> {
                    Image firstImage = findedPost.getImages()
                            .stream()
                            .findFirst()        // Optional<Image>
                            .orElse(null);      // 비어 있으면 null

                    String imageUrl = "https://gz-zigu.store/" + firstImage.getS3Key();
                    // 2) DTO 빌드
                    assert firstImage != null;
                    return PostSearchResDto.builder()
                            .post_id(findedPost.getId())
                            .firstImageUrl(imageUrl)
                            .itemName(findedPost.getItemName())
                            .category(findedPost.getCategory())
                            .price_per_hour(findedPost.getPricePerHour())
                            .price_per_day(findedPost.getPricePerDay())
                            .build();
                }).toList();

        return dtos;
    }

    public PostUpdateResDto updatePost(PostUpdateReqDto dto){
        Post currentPost = postRepo.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));

        // itemName 변경
        currentPost.updateItemName(dto.getItemName());
        // 시간 당 가격 변경
        currentPost.updatePricePerHour(dto.getPricePerHour());
        // 일일 가격 변경
        currentPost.updatePricePerDay(dto.getPricePerDay());
        // 상세 설명 변경
        currentPost.updateDescription(dto.getDescription());

        postRepo.save(currentPost);

        return PostUpdateResDto.builder()
                .postId(currentPost.getId())
                .build();
    }
}
