package com.pard.gz.zigu.apply.service;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.Image.repository.ImageRepo;
import com.pard.gz.zigu.apply.dto.ApplyListResDto;
import com.pard.gz.zigu.apply.dto.ApplySaveReqDto;
import com.pard.gz.zigu.apply.entity.Apply;
import com.pard.gz.zigu.apply.repository.ApplyRepo;
import com.pard.gz.zigu.borrowed.entity.Borrowed;
import com.pard.gz.zigu.borrowed.entity.enums.BorrowStatus;
import com.pard.gz.zigu.borrowed.repository.BorrowedRepo;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.post.repository.PostRepo;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplyService {

    private final ApplyRepo applyRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final BorrowedRepo borrowedRepo;

    @Transactional
    public void createApply(ApplySaveReqDto dto){

        Post currentPost = postRepo.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다"));

        User applier = currentPost.getWriter();

        Apply newApply = Apply.builder()
                .post(currentPost)
                .applier(applier)
                .unitOfPeroid(dto.getUnitOfPeroid())
                .peroid(dto.getPeroid())
                .totalPrice(dto.getTotalPrice())
                .build();

        // 리포에 저장
        applyRepo.save(newApply);
    }

    @Transactional(readOnly = true)
    public List<ApplyListResDto> getMyApplyList(User user) {

        List<Post> myPosts = postRepo.findAllByWriter(user); // 1. 내가 쓴 글 전부 찾기

        return myPosts.stream().map(post -> {

            List<Apply> applyList = applyRepo.findByPost(post);     // 2. 해당 글에 들어온 신청서들
            List<Image> imageList = imageRepo.findByPost(post);     // 3. 해당 글의 이미지 리스트

            String firstImageUrl = imageList.isEmpty() ? null : imageList.get(0).getS3Key();

            List<ApplyListResDto.ApplyInfo> applyInfoList = applyList.stream()
                    .map(apply -> ApplyListResDto.ApplyInfo.builder()
                            .applierNickname(apply.getApplier().getNickname())
                            .firstImageUrl(firstImageUrl)
                            .period(apply.getPeroid())
                            .unitOfPeriod(apply.getUnitOfPeroid())
                            .totalPrice(apply.getTotalPrice())
                            .build())
                    .toList();

            return ApplyListResDto.builder()
                    .postId(post.getId())
                    .itemName(post.getItemName())
                    .applyList(applyInfoList)
                    .build();

        }).toList();
    }


    public void acceptApply(Long applierId){
        Apply apply = applyRepo.findById(applierId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청내역입니다"));

        // 해당 물품의 대여상태 상태 변경(가능 -> 불가능)
        Post currentPost = apply.getPost();
        currentPost.updateImpossible();
        postRepo.save(currentPost);

        // 대여 내역 생성
        Borrowed newBorrowed = Borrowed.builder()
                .post(currentPost)
                .borrower(apply.getApplier())
                .peroid(apply.getPeroid())
                .unitOfPeroid(apply.getUnitOfPeroid())
                .price(apply.getTotalPrice())
                .borrowStatus(BorrowStatus.BORROWED) // 대여 중으로 상태 설정
                .build();
        borrowedRepo.save(newBorrowed);

        // 수락 후 대여 내역으로 저장했으니 신청서는 삭제
        applyRepo.delete(apply);
    }

    public void rejectApply(Long applyId){
        Apply refusedApply = applyRepo.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청입니다"));
        applyRepo.delete(refusedApply);
    }

}
