package com.example.newboard.web.view;

import com.example.newboard.service.UserService;
import com.example.newboard.web.dto.JoinRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller // 해당 클래스가 Spring MVC의 컨트롤러 역할을 한다는 어노테이션
@RequiredArgsConstructor // final 필드를 매개변수로 하는 생성자를 자동 생성 (DI 주입)
public class AuthViewController {
    private final UserService userService; // 회원 가입 로직을 처리할 서비스 클래스 주입

    @GetMapping("/login") // GET 요청으로 /login 접근 시 실행
    public String login(){
        return "login"; // login.html 뷰 페이지 반환
    }

    //JoinRequest DTO를 빈 상태로 모델에 넣어서 join.html에서 폼 바인딩 가능하게 함.
    @GetMapping("/join") // GET 요청으로 /join 접근 시 실행 (회원가입 폼 페이지)
    public String joinForm(Model model){
        model.addAttribute("joinRequest", new JoinRequest()); // 뷰에서 사용할 JoinRequest 객체 전달
        return "join"; // join.html 뷰 페이지 반환
    }

    @PostMapping("/join") // POST 요청으로 /join 접근 시 실행 (회원가입 처리)
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, // 폼 데이터 바인딩 + 유효성 검사
                       BindingResult br, // 유효성 검사 결과 저장
                       Model model){ // 뷰로 데이터 전달용

        if (br.hasErrors()) return "join"; // 유효성 에러 발생 시 회원가입 페이지로 다시 이동

        try {
            userService.join(joinRequest); // 회원가입 서비스 호출
        } catch (IllegalArgumentException e){ // 중복 이메일 등 예외 처리
            model.addAttribute("error", e.getMessage()); // 에러 메시지 모델에 추가
            return "join"; // 에러 발생 시 다시 회원가입 페이지로
        }

        return "redirect:/login"; // 성공 시 로그인 페이지로 리다이렉트
    }
}
