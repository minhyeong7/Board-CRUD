package com.example.newboard.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 게시글(Article) 엔티티 클래스.
 * JPA와 Lombok을 활용해 게시글 데이터를 DB 테이블에 매핑하고 관리함.
 */
@Entity // 이 클래스가 JPA 엔티티임을 나타냄. DB의 'article' 테이블로 매핑됨.
@Getter // 모든 필드에 대한 getter 메서드를 Lombok이 자동 생성함.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 기본 생성자 자동 생성 (JPA에서 프록시 생성을 위해 필요). 외부에서 직접 new로 생성하지 못하게 protected로 제한.

@AllArgsConstructor(access = AccessLevel.PRIVATE)
// 모든 필드를 인자로 받는 생성자 생성. 외부에서 직접 사용하는 것을 방지하기 위해 private 설정.

@Builder
// 빌더 패턴 제공. Article.builder().title("...").content("...").build() 형태로 객체 생성 가능.
public class Article {

    @Id // 이 필드가 기본 키(PK)임을 지정.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본 키 생성을 DB에 위임. (MySQL의 AUTO_INCREMENT와 유사)
    private Long id;

    @Column(nullable = false, length = 200)
    // 'title' 컬럼은 null을 허용하지 않고, 최대 길이는 200자.
    private String title;

    @Lob // Large Object. 큰 용량의 데이터를 저장할 때 사용됨. (ex. 긴 본문 텍스트)
    @Column(nullable = false)
    // 'content' 컬럼은 null을 허용하지 않음.
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // N:1 다대일 관계. 여러 게시글이 하나의 사용자(User)를 참조할 수 있음.
    // LAZY: 실제 author 데이터가 필요한 순간에 조회 (성능 최적화)

    @JoinColumn(name = "author_id", nullable = false) // 외래 키 컬럼명을 'author_id'로 지정. null 허용하지 않음.
    private User author; // 게시글을 작성한 사용자. User 엔티티와 연결됨 (외래키 관계)

    /**
     * 게시글 제목과 내용을 수정하는 메서드.
     */
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
