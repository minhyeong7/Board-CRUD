package com.example.newboard.repository;

import com.example.newboard.domain.Article; // Article 엔티티 import
import org.springframework.data.jpa.repository.JpaRepository; // JPA용 Repository 인터페이스 import

import java.util.Optional;
// 게시글(Article)을 DB에 저장하고 불러오는 Repository 인터페이스
public interface ArticleRepository extends JpaRepository<Article, Long> { // JpaRepository 상속받아 기본 CRUD 메서드 자동 제공

    Optional<Article> findByIdAndAuthor_Email(Long id, String email);
    long deleteByIdAndAuthor_Email(Long id, String email);


    // 아무것도 안 써도 아래 기능들이 자동으로 제공됨 👇
    // save(Article)        → 게시글 저장
    // findAll()            → 모든 게시글 목록 조회
    // findById(Long)       → 특정 게시글 조회
    // deleteById(Long)     → 게시글 삭제
    // count()              → 전체 게시글 수 조회
}
