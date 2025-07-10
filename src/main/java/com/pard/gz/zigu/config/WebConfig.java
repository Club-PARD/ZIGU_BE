package com.pard.gz.zigu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 전체 API 경로에 대해
                .allowedOrigins("http://localhost:3000", "https://gz-zigu.store", "https://gz-zeta.vercel.app") // 프론트 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowCredentials(true) // 쿠키 등 포함 여부
                .allowedHeaders("*"); // 어떤 헤더든 허용
    }
}


