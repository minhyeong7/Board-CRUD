package com.example.newboard.web.api;

import com.example.newboard.service.ArticleService; // 서비스 계층 의존성 주입
import com.example.newboard.web.dto.ArticleCreateRequest; // 게시글 생성 요청 DTO
import com.example.newboard.web.dto.ArticleUpdateRequest; // 게시글 수정 요청 DTO
import jakarta.validation.Valid; // @Valid 유효성 검증
import lombok.RequiredArgsConstructor; // final 필드 생성자 자동 생성
import org.springframework.http.HttpStatus; // HTTP 상태 코드 상수
import org.springframework.http.ResponseEntity; // HTTP 응답 객체
import org.springframework.web.bind.annotation.*; // REST 컨트롤러 관련 애노테이션

@RestController // JSON 응답을 반환하는 컨트롤러
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성 → 의존성 주입
@RequestMapping("/api/articles") // 이 컨트롤러의 모든 요청 URL 앞에 /api/articles 붙음
public class ArticleApiController {

    private final ArticleService articleService; // 서비스 계층 의존성 주입




    @PostMapping // POST /api/articles → 게시글 생성 요청
    public ResponseEntity<Void> create(@Valid @RequestBody ArticleCreateRequest req) {
        // @Valid : DTO 유효성 검사(@NotBlank 등)
        // @RequestBody : JSON 요청 바디 → DTO로 변환
        articleService.create(req); // 서비스 계층 호출하여 게시글 생성
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created 상태코드로 응답, 본문 없음
    }

    @PutMapping("/{id}") // PUT /api/articles/{id} → 게시글 수정 요청
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ArticleUpdateRequest req) {
        // @PathVariable : URL 경로의 {id} 값을 매개변수로 받음
        articleService.update(id, req); // 서비스 계층 호출하여 게시글 수정
        return ResponseEntity.ok().build(); // 200 OK 상태코드로 응답, 본문 없음
    }

    @DeleteMapping("/{id}") // DELETE /api/articles/{id} → 게시글 삭제 요청
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id); // 서비스 계층 호출하여 게시글 삭제
        return ResponseEntity.noContent().build(); // 204 No Content 상태코드로 응답, 본문 없음
    }
}
