package com.pard.gz.zigu.user.controller;

import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Void> signUp(@RequestBody UserSignUpReqDto userSignUpReqDto){
        userService.signUpUser(userSignUpReqDto);
        return ResponseEntity.ok().build();
    }

}
