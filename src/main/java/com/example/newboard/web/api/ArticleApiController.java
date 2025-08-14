package com.example.newboard.web.api;

import com.example.newboard.domain.Article;
import com.example.newboard.service.ArticleService; // 게시글 관련 비즈니스 로직을 담당하는 서비스
import com.example.newboard.web.dto.ArticleCreateRequest; // 게시글 생성 요청을 담는 DTO
import com.example.newboard.web.dto.ArticleUpdateRequest; // 게시글 수정 요청을 담는 DTO
import jakarta.validation.Valid; // 요청 DTO에 대해 유효성 검사 수행
import lombok.RequiredArgsConstructor; // final 필드 기반 생성자 자동 생성
import org.springframework.http.ResponseEntity; // HTTP 응답을 표현하는 객체
import org.springframework.security.core.Authentication; // 로그인한 사용자 정보에 접근
import org.springframework.web.bind.annotation.*; // REST API 관련 애노테이션들

import java.net.URI;

@RestController // RESTful API 컨트롤러. 반환값은 JSON 또는 HTTP 응답으로 처리됨.
@RequiredArgsConstructor // 생성자를 통해 articleService를 의존성 주입함.
@RequestMapping("/api/articles") // 공통 URL prefix 지정. 이 컨트롤러는 "/api/articles"로 시작하는 요청 처리.
public class ArticleApiController {

    private final ArticleService articleService; // 게시글 비즈니스 로직을 처리하는 서비스 클래스

    /**
     * 게시글 생성 요청 처리
     * POST /api/articles
     *
     * @param req  클라이언트로부터 전달받은 게시글 제목, 내용
     * @param auth 현재 로그인한 사용자 정보를 담은 인증 객체
     * @return     생성된 게시글의 URI 경로를 포함한 201 응답
     */
    @PostMapping
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleCreateRequest req, Authentication auth) {
        // 로그인한 사용자의 이름을 기반으로 게시글 작성 처리
        Long id = articleService.create(req, auth.getName());

        // 생성된 게시글의 URI를 Location 헤더에 포함시켜 응답
        return ResponseEntity.created(URI.create("/articles/" + id)).build();
    }

    /**
     * 게시글 수정 요청 처리
     * PUT /api/articles/{id}
     *
     * @param id    수정할 게시글 ID
     * @param req   수정할 제목과 내용
     * @param auth  현재 로그인한 사용자 정보
     * @return      204 No Content 응답 (본문 없음)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody ArticleUpdateRequest req,
                                       Authentication auth) {
        // 작성자 검증 및 게시글 수정 처리
        articleService.update(id, auth.getName(), req);

        return ResponseEntity.noContent().build(); // 204 응답
    }

    /**
     * 게시글 삭제 요청 처리
     * DELETE /api/articles/{id}
     *
     * @param id    삭제할 게시글 ID
     * @param auth  현재 로그인한 사용자 정보
     * @return      204 No Content 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        // 작성자 검증 및 게시글 삭제 처리
        articleService.delete(id, auth.getName());

        return ResponseEntity.noContent().build(); // 204 응답
    }
}
