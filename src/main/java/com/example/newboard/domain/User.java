package com.example.newboard.domain; // 엔티티 클래스가 위치하는 패키지

import jakarta.persistence.*; // JPA 어노테이션
import lombok.*; // Lombok 어노테이션

@Entity // JPA 엔티티임을 선언 (DB 테이블과 매핑)
@Table( // 엔티티와 매핑될 테이블 설정
        name = "users", // 테이블명 지정 (기본은 클래스명 소문자)
        uniqueConstraints = @UniqueConstraint(columnNames = "email") // email 컬럼에 유니크 제약조건 설정
)
@Getter // 모든 필드에 대해 getter 메서드 자동 생성
@NoArgsConstructor // 파라미터 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
@Builder // 빌더 패턴 적용 (User.builder()...build())
public class User {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성 (AUTO_INCREMENT 방식)
    private Long id; // 회원 고유 ID

    @Column(nullable = false, length = 100) // null 불가, 최대 길이 100
    private String email; // 회원 이메일

    @Column(nullable = false, length = 60) // null 불가, 최대 길이 60 (BCrypt 해시 길이에 맞춤)
    private String password; // 비밀번호 (BCrypt 해시값 저장)

    @Column(nullable = false, length = 50) // null 불가, 최대 길이 50
    private String name; // 회원 이름

    @Column(nullable = false, length = 20) // null 불가, 최대 길이 20
    private String role; // 회원 역할 (예: "USER", "ADMIN")
}
