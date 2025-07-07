package com.pard.gz.zigu.apply.service;

import com.pard.gz.zigu.apply.dto.ApplySaveReqDto;
import com.pard.gz.zigu.apply.entity.Apply;
import com.pard.gz.zigu.apply.repository.ApplyRepo;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.post.repository.PostRepo;
import com.pard.gz.zigu.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ApplyService {

    private final ApplyRepo applyRepo;
    private final PostRepo postRepo;
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

}
