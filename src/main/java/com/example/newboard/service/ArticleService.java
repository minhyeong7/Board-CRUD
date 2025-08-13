package com.example.newboard.service;

import com.example.newboard.domain.Article; // 게시글 엔티티 클래스
import com.example.newboard.repository.ArticleRepository; // 게시글 리포지토리 인터페이스
import com.example.newboard.web.dto.ArticleCreateRequest; // 게시글 생성 요청 DTO
import com.example.newboard.web.dto.ArticleUpdateRequest;
import jakarta.transaction.Transactional; // 트랜잭션 처리 어노테이션
import lombok.RequiredArgsConstructor; // 생성자 자동 생성 (의존성 주입)
import org.springframework.stereotype.Service; // 서비스 컴포넌트 표시

import java.util.List;

@Service // 이 클래스가 서비스 계층임을 나타냄 (스프링이 자동으로 Bean으로 등록)
@RequiredArgsConstructor // final 필드를 자동 생성자 주입
public class ArticleService {
    private final ArticleRepository articleRepository; // 게시글 관련 DB 작업을 담당할 Repository 주입

    public List<Article> findAll() { // 전체 게시글 목록을 조회하는 메서드
        return articleRepository.findAll(); // 리포지토리의 findAll() 호출 → 게시글 리스트 반환
    }

    @Transactional // 이 메서드 실행 중 예외가 발생하면 자동으로 롤백됨 (DB 무결성 보장)
    public void create(ArticleCreateRequest req) { // 게시글 생성 메서드, 클라이언트 요청 DTO를 받아 처리
        articleRepository.save( // DB에 새 게시글 저장

                Article.builder() // Article 객체를 builder 패턴으로 생성
                        .title(req.getTitle()) // 요청 DTO로부터 제목 설정
                        .content(req.getContent()) // 요청 DTO로부터 내용 설정
                        .build() // Article 객체 생성 완료
        );
    }

    public Article findById(Long id){
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found:" + id));

       // id가 존재하면 → 해당 ID에 해당하는 Article 객체를 반환합니다.
       // id가 존재하지 않으면 → IllegalArgumentException 예외를 발생시킵니다
    }

    @Transactional
    public void update(Long id, ArticleUpdateRequest req){ // 게시글 수정 메서드
        var article = findById(id); // 수정할 게시글 조회 Article article = articleService.findById(id);
        article.update(req.getTitle(), req.getContent()); // 조회한 엔티티에 제목/내용 업데이트 요청
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id); // 해당 ID의 게시글을 DB에서 삭제
    }




}
