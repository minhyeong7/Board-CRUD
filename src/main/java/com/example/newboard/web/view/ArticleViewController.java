package com.example.newboard.web.view;

import com.example.newboard.service.ArticleService; // 게시글 서비스 클래스 임포트
import com.example.newboard.web.dto.ArticleCreateRequest; // 게시글 생성 요청 DTO 임포트
import com.example.newboard.web.dto.ArticleUpdateRequest; // 게시글 수정 요청 DTO 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller; // HTML 뷰를 반환하는 MVC 컨트롤러
import org.springframework.ui.Model; // 뷰에 데이터를 전달하는 객체
import org.springframework.web.bind.annotation.GetMapping; // GET 요청 매핑
import org.springframework.web.bind.annotation.PathVariable; // URL 경로 변수 추출용
import org.springframework.web.bind.annotation.PostMapping; // POST 요청 매핑

@Controller // 이 클래스가 스프링 MVC에서 HTML 뷰를 반환하는 컨트롤러임을 명시
@RequiredArgsConstructor // final 필드를 가진 생성자를 자동 생성해줌 (의존성 주입에 사용)
public class ArticleViewController {

    private final ArticleService articleService; // 게시글 관련 비즈니스 로직을 처리할 서비스 객체

    @GetMapping({"/", "/articles"}) // 루트("/") 또는 "/articles" 주소로 GET 요청이 오면 실행
    public String list(Model model) {
        model.addAttribute("articles", articleService.findAll()); // 게시글 리스트를 모델에 담아 전달
        return "article-list"; // 게시글 목록 뷰 반환 (templates/article-list.html)
    }

    @GetMapping("/articles/new") // 게시글 작성 폼 요청
    public String createForm() {
        return "article-form"; // 게시글 작성 폼 뷰 반환 (templates/article-form.html)
    }

    @PostMapping("/articles") // 게시글 작성 후 저장 요청
    public String create(ArticleCreateRequest req) {
        articleService.create(req); // 게시글 생성 로직 실행
        return "redirect:/articles"; // 게시글 목록 페이지로 리다이렉트
    }

    @GetMapping("/articles/{id}") // 특정 게시글 조회 요청
    public String detail(@PathVariable Long id, Model model) {
        var article = articleService.findById(id); // ID에 해당하는 게시글 조회
        model.addAttribute("article", article); // 모델에 게시글 추가
        return "article-detail"; // 게시글 상세 뷰 반환 (templates/article-detail.html)
    }

    @GetMapping("/articles/{id}/edit") // 게시글 수정 폼 요청
    public String editForm(@PathVariable Long id, Model model) {
        var article = articleService.findById(id); // ID로 기존 게시글 조회
        model.addAttribute("article", article); // 모델에 게시글 정보 추가
        return "article-edit"; // 게시글 수정 폼 뷰 반환 (templates/article-edit.html)
    }

    @PostMapping("/articles/{id}/edit") // 게시글 수정 제출 요청
    public String edit(@PathVariable Long id, ArticleUpdateRequest req) {
        articleService.update(id, req); // 게시글 수정 처리
        return "redirect:/articles/" + id; // 수정 완료 후 해당 게시글 상세 페이지로 리다이렉트
    }

    @PostMapping("/articles/{id}/delete") // 게시글 삭제 요청
    public String delete(@PathVariable Long id) { // @PathVariable은 URL 경로에 있는 {id} 값을 자바 변수 id에 매핑해주는 역할
        articleService.delete(id); // 해당 게시글 삭제 처리
        return "redirect:/articles"; // 삭제 후 게시글 목록 페이지로 리다이렉트
    }

}
