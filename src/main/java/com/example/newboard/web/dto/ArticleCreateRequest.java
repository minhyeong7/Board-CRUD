package com.example.newboard.web.dto;

import jakarta.validation.constraints.NotBlank; // 필드가 null이거나 빈 문자열이 아니도록 검증
import lombok.Getter; // 모든 필드의 getter 메서드 자동 생성
import lombok.NoArgsConstructor; // 파라미터 없는 기본 생성자 자동 생성
import lombok.Setter;


@Setter
@Getter // title, content 필드의 getter 생성 (예: getTitle(), getContent())
@NoArgsConstructor // 기본 생성자 생성 (프레임워크에서 객체 생성할 때 필요)
public class ArticleCreateRequest { // 게시글 생성 요청을 받을 DTO (Data Transfer Object)

    @NotBlank(message = "제목은 필수입니다.") // title이 null이거나 공백이면 유효성 검사 실패
    private String title; // 게시글 제목

    @NotBlank(message = "내용은 필수입니다") // content가 null이거나 공백이면 유효성 검사 실패
    private String content; // 게시글 내용
}
