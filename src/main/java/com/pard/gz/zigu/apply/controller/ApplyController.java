package com.pard.gz.zigu.apply.controller;

import com.pard.gz.zigu.apply.dto.ApplySaveReqDto;
import com.pard.gz.zigu.responseDto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    @Operation(summary = "신청서 (작성 후)저장", description = "유저가 빌리고 싶은 물품의 대여 신청서 저장")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Void>> saveApply(@RequestBody ApplySaveReqDto applySaveReqDto){
        // service
        return ResponseEntity.ok(new ApiResponse<>(200, true, "신청서 저장 성공", null));
    }
}
