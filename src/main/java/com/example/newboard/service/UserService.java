package com.example.newboard.service; // 서비스 계층 패키지

import com.example.newboard.domain.User; // User 엔티티
import com.example.newboard.repository.UserRepository; // User 저장/조회용 리포지토리
import com.example.newboard.web.dto.JoinRequest; // 회원 가입 요청 DTO
import lombok.RequiredArgsConstructor; // final 필드 생성자 자동 생성
import org.springframework.security.crypto.password.PasswordEncoder; // 비밀번호 암호화 인터페이스
import org.springframework.stereotype.Service; // 서비스 계층 어노테이션
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 처리 어노테이션

@Service // 스프링의 Service 컴포넌트로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성(DI 용)
public class UserService { // 회원 관련 비즈니스 로직 처리 클래스

    private final UserRepository userRepository; // 회원 정보 DB 액세스 객체
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화기

    @Transactional // 메서드 실행 시 트랜잭션 시작, 실패 시 롤백
    public void join(JoinRequest req){ // 회원 가입 처리 메서드, JoinRequest로 요청 데이터 받음

        // 이메일 중복 검사
        if (userRepository.existsByEmail(req.getEmail())) // 이미 같은 이메일이 DB에 있으면
            throw new IllegalArgumentException("이미 가입된 이메일입니다."); // 예외 발생

        // 회원 정보 저장
        userRepository.save(
                User.builder() // User 엔티티 생성
                .email(req.getEmail()) // 이메일 설정
                .password(passwordEncoder.encode(req.getPassword())) // 비밀번호 암호화 후 설정
                .name(req.getName()) // 이름 설정
                .role("USER") // 기본 권한 설정
                .build()); // User 객체 완성 후 리턴
    }
}
