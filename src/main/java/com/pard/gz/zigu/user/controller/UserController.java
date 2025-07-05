package com.pard.gz.zigu.user.controller;

import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // cert로 바꿔줘?
public class UserController {
    private final UserService userService;



//    @GetMapping("/{userId}")
//    public UserResDto.ReadUser getUser(@PathVariable Long userId) {
//        return userService.readUser(userId);
//    }



}
