package com.example.newboard.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 게시글(Article) 엔티티 클래스.
 * JPA와 Lombok을 활용해 게시글 데이터를 DB에 매핑하고 관리함.
 */
@Entity // 이 클래스가 JPA 엔티티임을 나타냄 (DB 테이블로 매핑됨)
@Getter // 모든 필드의 getter 메서드 자동 생성 (Lombok)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 생성. protected로 제한하여 외부에서 직접 객체 생성 방지
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 모든 필드를 매개변수로 받는 생성자 생성. private으로 제한하여 빌더 패턴 사용 유도
@Builder // 빌더 패턴으로 객체 생성 가능하게 함 , 다양한 함수 제공



public class Article {

    @Id // 이 필드가 테이블의 기본 키(PK)임을 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 DB에서 자동 생성 (MySQL의 AUTO_INCREMENT와 유사)
    private Long id;

    @Column(nullable = false, length = 200) // title 컬럼은 null 불가, 최대 길이 200자로 제한됨
    private String title;

    @Lob // Large Object: 긴 텍스트를 위한 어노테이션 (ex. CLOB)
    @Column(nullable = false) // content 컬럼은 null 불가
    private String content;

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }


}
