package com.pard.gz.zigu.school.service;

import com.pard.gz.zigu.school.dto.SchoolCreateReqDto;
import com.pard.gz.zigu.school.entity.School;
import com.pard.gz.zigu.school.repository.SchoolRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepo schoolRepo;


    // 학교 이름으로 검색 → 없으면 새로 만들고 리턴
    @Transactional
    public School findOrCreateByName(String name) {

        System.out.println("찾을 학교 이름 : "+name);

        School userSchool = schoolRepo.findBySchoolName(name)
                .orElseGet(() -> schoolRepo.save(new School(name)));

        System.out.println("최종 학교 이름 : "+ userSchool.getSchoolName());
        return userSchool;
    }

}
