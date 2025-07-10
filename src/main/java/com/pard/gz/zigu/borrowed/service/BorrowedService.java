package com.pard.gz.zigu.borrowed.service;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.Image.repository.ImageRepo;
import com.pard.gz.zigu.borrowed.dto.BorrInfoResDto;
import com.pard.gz.zigu.borrowed.entity.Borrowed;
import com.pard.gz.zigu.borrowed.repository.BorrowedRepo;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.post.repository.PostRepo;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BorrowedService {
    private final BorrowedRepo borrRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final PostRepo postRepo;

    public List<BorrInfoResDto> getBorrowedList(Long userId){
        User borrower = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 USER입니다"));

        List<Borrowed> borroweds = borrRepo.findAllByBorrower(borrower);

        return borroweds.stream().map(borrowed -> {

            Post post = borrowed.getPost();
            List<Image> images = post.getImages(); // 연관관계 설정되어 있어야 함
            String firstImageUrl = images.isEmpty() ? null : images.get(0).getS3Key(); // 또는 getPath()
            String imageUrl = "https://gz-zigu.store/" + firstImageUrl;

            return BorrInfoResDto.builder()
                    .postId(post.getId())
                    .borrowedId(borrowed.getId())
                    .firstImageUrl(imageUrl)
                    .itemName(post.getItemName())
                    .pricePerDay(post.getPricePerDay())
                    .pricePerHour(post.getPricePerHour())
                    .borrowStatus(borrowed.getBorrowStatus())
                    .peroid(borrowed.getPeroid())
                    .unitOfPeroid(borrowed.getUnitOfPeroid())
                    .totalPrice(borrowed.getPrice())
                    .build();
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<BorrInfoResDto> getLendList(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));

        return user.getPostList().stream()                        // 유저가 올린 모든 게시물
                .flatMap(post -> post.getBorrowedList().stream()     // 각 게시물의 대여 내역들을 하나로 합침
                        .map(borrowed -> {
                            List<Image> images = post.getImages();
                            String firstImageUrl = images.isEmpty() ? null : images.get(0).getS3Key();
                            String imageUrl = "https://gz-zigu.store/" + firstImageUrl;


                            return BorrInfoResDto.builder()
                                    .postId(post.getId())
                                    .borrowedId(borrowed.getId())
                                    .firstImageUrl(imageUrl)
                                    .itemName(post.getItemName())
                                    .pricePerDay(post.getPricePerDay())
                                    .pricePerHour(post.getPricePerHour())
                                    .borrowStatus(borrowed.getBorrowStatus())
                                    .peroid(borrowed.getPeroid())
                                    .unitOfPeroid(borrowed.getUnitOfPeroid())
                                    .totalPrice(borrowed.getPrice())
                                    .build();
                        })
                ).toList(); // flatMap으로 전체 Borrowed들을 하나의 List로 합쳐버ㄹ릠
    }

    @Transactional
    public void returnCompleted(Long borrowedId){
        Borrowed borrowed = borrRepo.findById(borrowedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대여내역입니다"));
        borrowed.updateToRETURN();
        borrRepo.save(borrowed);

        Post post = borrowed.getPost();
        post.updatePossible();
        postRepo.save(post);

        System.out.println();
        System.out.println("바뀐 대여 상태: "+borrowed.getBorrowStatus());
        System.out.println("반납 완료 후 해당 게시물의 상태: "+post.getIsBorrowable());
        System.out.println();
    }


}
