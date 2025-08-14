package com.example.newboard.service;

import com.example.newboard.domain.Article; // 게시글 엔티티 클래스
import com.example.newboard.repository.ArticleRepository; // 게시글 리포지토리 인터페이스
import com.example.newboard.repository.UserRepository;
import com.example.newboard.web.dto.ArticleCreateRequest; // 게시글 생성 요청 DTO
import com.example.newboard.web.dto.ArticleUpdateRequest;
import jakarta.transaction.Transactional; // 트랜잭션 처리 어노테이션
import lombok.RequiredArgsConstructor; // 생성자 자동 생성 (의존성 주입)
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service; // 서비스 컴포넌트 표시

import java.util.List;

@Service // 이 클래스가 서비스 계층임을 나타냄 (스프링이 자동으로 Bean으로 등록)
@RequiredArgsConstructor // final 필드를 자동 생성자 주입
public class ArticleService {
    private final ArticleRepository articleRepository; // 게시글 관련 DB 작업을 담당할 Repository 주입
    private final UserRepository userRepository;



    public List<Article> findAll() { // 전체 게시글 목록을 조회하는 메서드
        return articleRepository.findAll(); // 리포지토리의 findAll() 호출 → 게시글 리스트 반환
    }


    @Transactional
    public Long create(ArticleCreateRequest req, String email){
        var author = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return articleRepository.save(
                Article.builder()
                        .title(req.getTitle())
                        .content(req.getContent())
                        .author(author)
                        .build()
        ).getId();
    }

    public Article findById(Long id){
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found:" + id));

       // id가 존재하면 → 해당 ID에 해당하는 Article 객체를 반환합니다.
       // id가 존재하지 않으면 → IllegalArgumentException 예외를 발생시킵니다
    }


    @Transactional
    public void update(Long id, String email, ArticleUpdateRequest req){
        var article = articleRepository.findByIdAndAuthor_Email(id, email)
                .orElseThrow(() -> new AccessDeniedException("본인 글이 아닙니다."));

        article.update(req.getTitle(), req.getContent());
    }


    @Transactional
    public void delete(Long id, String email){

        if (articleRepository.deleteByIdAndAuthor_Email(id, email) == 0)
            throw new AccessDeniedException("본인 글이 아닙니다.");
    }
}





