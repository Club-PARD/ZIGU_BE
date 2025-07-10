package com.pard.gz.zigu.apply.controller;

import com.pard.gz.zigu.apply.dto.ApplyListResDto;
import com.pard.gz.zigu.apply.dto.ApplySaveReqDto;
import com.pard.gz.zigu.apply.repository.ApplyRepo;
import com.pard.gz.zigu.apply.service.ApplyService;
import com.pard.gz.zigu.config.security.CustomUserDetails;
import com.pard.gz.zigu.responseDto.ApiResponse;
import com.pard.gz.zigu.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    private final ApplyService applyService;

    @Operation(summary = "신청서 (작성 후)저장", description = "유저가 빌리고 싶은 물품의 대여 신청서 저장")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Void>> saveApply(@RequestBody ApplySaveReqDto applySaveReqDto){
        System.out.println();
        System.out.println("기간 : " + applySaveReqDto.getPeroid() +" "+applySaveReqDto.getUnitOfPeroid());
        System.out.println();
        // service
        applyService.createApply(applySaveReqDto);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "신청서 저장 성공", null));
    }


    @Operation(summary = "신청서 조회(신청 내역 조회)", description = "게시한 대여물품으로 온 신청 내역 조회")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ApplyListResDto>>> readMyApplyList(
            @AuthenticationPrincipal CustomUserDetails user) {
        User currentUser = user.getUser();
        List<ApplyListResDto> dtos = applyService.getMyApplyList(currentUser);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @Operation(summary = "신청 수락", description = "게시한 대여물품으로 온 신청 하나 수락")
    @DeleteMapping("/ok")
    public ResponseEntity<ApiResponse<List<Void>>> acceptApply(@RequestParam Long applyId){
        applyService.acceptApply(applyId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "신청 수락 성공", null));
    }

    @Operation(summary = "신청 거절", description = "게시한 대여물품으로 온 신청 거절")
    @DeleteMapping("/no")
    public ResponseEntity<ApiResponse<List<Void>>> rejectApply(@RequestParam Long applyId){
        applyService.rejectApply(applyId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "신청 거절 성공", null));
    }

}
