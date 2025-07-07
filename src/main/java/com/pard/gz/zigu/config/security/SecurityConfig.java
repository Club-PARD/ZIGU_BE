package com.pard.gz.zigu.config.security;

import com.pard.gz.zigu.config.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


// 비밀번호 인코딩 설정파일(해싱)
@Configuration
@EnableWebSecurity // Spring Security 기능을 활성화
@RequiredArgsConstructor // final 필드를 가진 생성자를 자동으로 생성
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final LoginFailureHandler    loginFailureHandler;   // 아직 안 쓰지만 필요하면 연결

    /* 비밀번호 해싱 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* AuthenticationManager – DB 조회 + 해시검증 */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    /* 진짜 보안 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* 1) CORS – 새 문법 */
                .cors(Customizer.withDefaults())

                /* 2) CSRF – 프론트가 SPA라면 끈다 */
                .csrf(AbstractHttpConfigurer::disable)

                /* 3) URL 권한 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                /* 4) 폼 로그인(리다이렉트) 완전 OFF ─ 새 방식 */
                .formLogin(FormLoginConfigurer::disable)

                /* 5) HTTP Basic OFF ─ 새 방식 */
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /* CORS 허용 목록 */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",   // 개발
                "http://gz-zigu.store",    // 배포 http
                "https://gz-zigu.store"    // 배포 https
        ));
        config.setAllowCredentials(true);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

