package com.pard.gz.zigu.school.controller;

import com.pard.gz.zigu.school.repository.SchoolRepo;
import com.pard.gz.zigu.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController {
    private final SchoolService schoolService;

    private final SchoolRepo schoolRepo;
}
