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


// 비밀번호 인코딩 설정파일(해싱)
@Configuration
@EnableWebSecurity // Spring Security 기능을 활성화
@RequiredArgsConstructor // final 필드를 가진 생성자를 자동으로 생성
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(userDetailsService)   // DB-조회 로직
                .passwordEncoder(passwordEncoder());      // 해시 검증

        return builder.build();
    }

    // 실제 필터/인가 규칙
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
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
                // ↓ 폼 로그인 활성화 + 경로만 맞춰주면 끝
                .formLogin(form -> form
                        .loginPage("/auth/login")          // 로그인 페이지(GET)
                        .loginProcessingUrl("/auth/login") // 로그인 처리(POST)
                        .defaultSuccessUrl("/", true)      // 로그인 성공 시 이동할 페이지
                        .permitAll()
                )
//                .logout(logout -> logout
//                        .logoutUrl("/auth/logout")
//                        .logoutSuccessUrl("/auth/login?logout")
//                        .permitAll()
//                )
                .httpBasic(AbstractHttpConfigurer::disable); // 필요 없으면 비활성화

        return http.build();
    }

}

