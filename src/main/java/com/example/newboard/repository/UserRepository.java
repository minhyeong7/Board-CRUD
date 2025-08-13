package com.example.newboard.repository; // 리포지토리 계층 패키지

import com.example.newboard.domain.User; // User 엔티티
import org.springframework.data.jpa.repository.JpaRepository; // JPA 리포지토리 인터페이스
import java.util.Optional; // Optional 클래스 (null 안전 처리)

public interface UserRepository extends JpaRepository<User,Long> { // User 엔티티를 Long 타입 PK로 관리하는 JPA 리포지토리
    Optional<User> findByEmail(String email); // 이메일로 회원 찾기 (null 가능성 → Optional로 반환)
    boolean existsByEmail(String email); // 이메일이 이미 존재하는지 여부 반환
} // JPA가 자동으로 구현체를 생성해줌
