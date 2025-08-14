package com.example.newboard.config; // Spring Security 설정 관련 클래스가 위치한 패키지

import com.example.newboard.repository.UserRepository; // DB에서 사용자 정보를 조회하기 위한 Repository
import lombok.RequiredArgsConstructor; // final 필드에 대해 생성자를 자동 생성
import org.springframework.security.core.userdetails.*; // Spring Security의 UserDetailsService, UserDetails 등
import org.springframework.stereotype.Service; // Service 계층을 나타내는 스프링 빈 어노테이션

@Service // 스프링 빈으로 등록, 서비스 역할
@RequiredArgsConstructor // final 필드 userRepository를 주입하는 생성자 자동 생성
public class CustomUserDetailsService implements UserDetailsService { // Spring Security에서 사용자 정보를 로드하는 핵심 인터페이스
    private final UserRepository userRepository; // DB에서 사용자 정보를 가져오는 Repository 의존성

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username(여기서는 email)을 기반으로 사용자 정보를 DB에서 조회
        var u = userRepository.findByEmail(username) // email로 사용자 조회
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // 사용자가 없으면 예외 발생 (Spring Security에서 인증 실패 처리)

        return org.springframework.security.core.userdetails.User // 스프링 시큐리티 전용 User 객체 생성
                .withUsername(u.getEmail()) // UserDetails의 username 설정 (여기서는 email)
                .password(u.getPassword()) // 비밀번호 설정 (BCrypt 인코딩된 값)
                .roles(u.getRole()) // 권한(ROLE_...) 설정, 예: "USER" → ROLE_USER
                .build(); // UserDetails 객체 완성
    } // Authentication 객체로 생성
}

// 로그인 시도 -> 스프링이 낚아챔 -> 이클래스에서 일단 유저엔티티로 담아온 다음 UserDetails 형태로 옮겨 담아서
// 거기서 또 꺼내서 결국은 Authentication 객체로 저장
