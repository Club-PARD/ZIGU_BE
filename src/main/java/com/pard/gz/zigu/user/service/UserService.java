package com.pard.gz.zigu.user.service;

import com.pard.gz.zigu.school.entity.School;
import com.pard.gz.zigu.school.repository.SchoolRepo;
import com.pard.gz.zigu.school.service.SchoolService;
import com.pard.gz.zigu.user.dto.UserSignUpReqDto;
import com.pard.gz.zigu.user.entity.User;
import com.pard.gz.zigu.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

//    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final SchoolRepo schoolRepo;
    private final SchoolService schoolService;

    @Transactional
    public void signUpUser(UserSignUpReqDto userRegisterReqDto) {

        // 이미 가입되어 있는 메일인지 확인
        if (userRepo.existsByStudentMail(userRegisterReqDto.getStudentMail()))
            throw new IllegalStateException("이미 가입된 메일입니다");

        // 2) 학교 엔티티 확보 (찾거나 생성)
        School school = schoolService.findOrCreateByName(userRegisterReqDto.getSchoolName());

//        String encodedPw = passwordEncoder.encode(userRegisterReqDto.getPassword()); // 해시화

        User newUser = User.builder()
                .nickname(userRegisterReqDto.getNickname())
//                .password(encodedPw)
                .password(userRegisterReqDto.getPassword())
                .school(school)
                .studentMail(userRegisterReqDto.getStudentMail())
                .postList(null)
                .borrowedList(null)
                .build();
        userRepo.save(newUser);
    }
}
