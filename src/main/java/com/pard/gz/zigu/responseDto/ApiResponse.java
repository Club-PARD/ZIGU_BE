package com.pard.gz.zigu.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), true, "요청 성공", data);
    }

    public static <T> ApiResponse<T> fail(String msg) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), false, msg, null);
    }
}

