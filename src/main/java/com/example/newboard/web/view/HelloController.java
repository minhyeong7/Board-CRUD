package com.example.newboard.web.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 간단한 테스트용 REST 컨트롤러.
 * "/hello" 경로로 GET 요청이 오면 "ok" 문자열을 응답함.
 */
@RestController // 이 클래스는 REST API 요청을 처리하는 컨트롤러임을 나타냄 (@Controller + @ResponseBody)
public class HelloController {

    @GetMapping("/hello") // HTTP GET 요청 중 "/hello" 경로를 이 메서드가 처리함
    public String hello() {
        return "ok"; // 클라이언트에 "ok"라는 문자열을 그대로 반환
    }
}
