package com.pard.gz.zigu.borrowed.controller;

import com.pard.gz.zigu.apply.dto.ApplyListResDto;
import com.pard.gz.zigu.borrowed.dto.BorrInfoResDto;
import com.pard.gz.zigu.borrowed.service.BorrowedService;
import com.pard.gz.zigu.post.service.PostService;
import com.pard.gz.zigu.responseDto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrowed")
public class BorrowedController {
    private final BorrowedService borrService;

    @Operation(summary = "빌린 내역 조회", description = "내가 빌리고 있는 물품들 조회")
    @GetMapping("/borrow")
    public ResponseEntity<ApiResponse<List<BorrInfoResDto>>> getBorrowedList(@RequestParam Long userId){
        List<BorrInfoResDto> dtos = borrService.getBorrowedList(userId);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @Operation(summary = "빌려 준 내역 조회", description = "내가 빌려주고 있는 물품들 조회")
    @GetMapping("/lend")
    public ResponseEntity<ApiResponse<List<BorrInfoResDto>>> getLendList(@RequestParam Long userId){
        List<BorrInfoResDto> dtos = borrService.getLendList(userId);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @Operation(summary = "빌려 준 내역에서 반납 요청(반납 완료 버튼)", description = "현재 내역에서 대여 상태를 반납 완료로 변경")
    @PatchMapping("/return")
    public ResponseEntity<ApiResponse<Void>> returnCompleted(@RequestParam Long borrowedId){
        borrService.returnCompleted(borrowedId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "반납 완료 성공", null));
    }
}
