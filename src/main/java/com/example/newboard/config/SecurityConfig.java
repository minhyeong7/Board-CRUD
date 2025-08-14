package com.example.newboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // 비밀번호 암호화용 Bean 등록
    // 회원가입 시 비밀번호를 해시화하고, 로그인 시 비교에 사용됨
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Filter Chain 설정
    // 모든 HTTP 요청을 필터 체인을 통해 보안 처리
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // URL 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 누구나 접근 가능한 URL (로그인 필요 없음)
                        .requestMatchers("/", "/articles/", "/articles/**", "/login", "/join", "/css/**", "/js/**").permitAll()
                        // 인증이 필요한 URL (/api/**) 로그인 되는 사람만 가능하게함
                        .requestMatchers("/api/**").authenticated()
                        // 그 외 나머지는 일단 허용
                        .anyRequest().permitAll()
                )
                // 폼 기반 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")                 // GET 요청 시 로그인 폼 제공
                        .loginProcessingUrl("/login")        // POST 요청 시 Spring Security가 로그인 처리
                        .defaultSuccessUrl("/articles", true) // 로그인 성공 시 이동할 URL, 항상 이동(true)
                        .permitAll()                          // 로그인 페이지와 로그인 처리 URL은 누구나 접근 가능
                )
                // 로그아웃 설정
                .logout(logout -> logout
                                .logoutUrl("/logout")                // 로그아웃 요청 URL
                                .logoutSuccessUrl("/articles")       // 로그아웃 성공 시 이동할 URL
                        // 세션 무효화, 인증 정보 제거 등 기본 로그아웃 동작 포함
                )
                // CSRF 보호 설정
                .csrf(csrf -> csrf
                        // CSRF 토큰을 쿠키와 헤더로 전송
                        // HttpOnly=false 설정으로 JavaScript에서 접근 가능 (필요에 따라 조정)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        // 설정 적용 후 SecurityFilterChain 객체 반환
        return http.build();
    }
}
