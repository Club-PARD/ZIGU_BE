package com.pard.gz.zigu.Image.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    // 로컬 정적 리소스 경로
    private static final String STATIC_DIR = "src/main/resources/static/images";

    // URL prefix – 브라우저가 접근할 경로
    private static final String URL_PREFIX = "/images/";

    @PostConstruct
    void ensureDir() {
        File dir = new File(STATIC_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    /**
     * 이미지를 로컬 static/images 에 저장하고,
     * 브라우저 접근용 경로(/images/filename) 반환
     */
    public String store(MultipartFile file) throws IOException {
        // ① 파일명 유니크하게 생성
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // ② 최종 저장 위치 (static/images/파일명)
        File dest = new File(STATIC_DIR, filename);

        // ③ 실제 하드디스크에 파일 복사
        file.transferTo(dest);

        // ④ 브라우저가 접근할 경로 리턴 → DB에 이 문자열만 저장
        return URL_PREFIX + filename;   //  ex) /images/1234_abcd.jpg
    }


}
