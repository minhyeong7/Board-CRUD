package com.example.newboard.web.dto; // 웹 계층에서 사용하는 DTO 패키지

import jakarta.validation.constraints.Email; // 이메일 형식 검증 어노테이션
import jakarta.validation.constraints.NotBlank; // 공백이나 null 허용하지 않는 검증 어노테이션
import lombok.Getter; // Getter 자동 생성
import lombok.Setter; // Setter 자동 생성

@Getter // 모든 필드에 대한 getter 메서드 자동 생성
@Setter // 모든 필드에 대한 setter 메서드 자동 생성
public class JoinRequest { // 회원 가입 요청 데이터 전송용 DTO

    @Email // 이메일 형식 검증
    @NotBlank // null 또는 공백 허용 안 함
    private String email; // 사용자가 입력한 이메일

    @NotBlank // null 또는 공백 허용 안 함
    private String password; // 사용자가 입력한 비밀번호

    @NotBlank // null 또는 공백 허용 안 함
    private String name; // 사용자가 입력한 이름
}
