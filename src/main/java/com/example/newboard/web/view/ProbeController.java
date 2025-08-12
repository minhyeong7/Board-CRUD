package com.example.newboard.web.view;

import com.example.newboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트용 Probe(프로브) 컨트롤러.
 * 게시글 개수를 반환하는 간단한 API를 제공.
 */
@RestController // REST API를 처리하는 컨트롤러. 반환값은 HTTP 응답 바디에 직접 쓰임
@RequiredArgsConstructor // Lombok: final 필드에 대해 생성자 자동 생성 (의존성 주입을 위한 생성자)
public class ProbeController {

    private final ArticleRepository repo;
    // 생성자를 통해 주입받는 게시글 리포지토리 (JPA 인터페이스)
    // Spring이 자동으로 구현체를 주입함 (@Repository 어노테이션 덕분)

    @GetMapping("/prove/count") // HTTP GET 요청 중 "/prove/count" 경로에 매핑됨
    public long count() {
        return repo.count(); // DB에 저장된 Article의 개수를 반환
    }
}
