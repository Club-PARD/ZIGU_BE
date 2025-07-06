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
    public void signUpUser(UserSignUpReqDto userSignUpReqDto) {

        System.out.println(userSignUpReqDto.getStudentMail());

        boolean exists = userRepo.existsByStudentMail(userSignUpReqDto.getStudentMail());
        System.out.println("exists = " + exists);
        // 이미 가입되어 있는 메일인지 확인
//        if (userRepo.existsByStudentMail(userSignUpReqDto.getStudentMail()))
//            throw new IllegalStateException("이미 가입된 메일입니다");
//        else
//            ;


        // 2) 학교 엔티티 확보 (찾거나 생성)
        School school = schoolService.findOrCreateByName(userSignUpReqDto.getSchoolName());

//        String encodedPw = passwordEncoder.encode(userRegisterReqDto.getPassword()); // 해시화

        System.out.println(school.getSchoolName());

        User newUser = User.builder()
                .nickname(userSignUpReqDto.getNickname())
//                .password(encodedPw)
                .password(userSignUpReqDto.getPassword())
                .school(school)
                .studentMail(userSignUpReqDto.getStudentMail())
                .postList(null)
                .borrowedList(null)
                .build();

        System.out.println("저장될 유저 : 닉넴 - " + newUser.getNickname() + ", school이름 - "+ newUser.getSchool().getSchoolName());
        userRepo.save(newUser);
        userRepo.flush(); // 이거 넣으면 터질 때 바로 예외 나옴
    }
}
