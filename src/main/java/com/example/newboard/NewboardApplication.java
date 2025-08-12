package com.example.newboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 스프링 부트 애플리케이션의 시작점(Main 클래스)
 */
@SpringBootApplication
// @Configuration, @EnableAutoConfiguration, @ComponentScan을 포함하는 복합 애너테이션
// 스프링 부트 자동 설정 + 컴포넌트 스캔 + 설정 클래스로 인식됨
public class NewboardApplication {
	public static void main(String[] args) {
		// 스프링 부트 애플리케이션을 실행하는 메서드
		SpringApplication.run(NewboardApplication.class, args);
		// 내장 톰캣 서버가 실행되고, 스프링 컨텍스트가 초기화됨
	}
}
