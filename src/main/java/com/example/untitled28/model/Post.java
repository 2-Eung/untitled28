package com.example.untitled28.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성 시간

          //처음엔 null 이었다가 유저아이디가 생성되면 작동한다. // author 는 항상 존재해야 한다 = User 는 항상 존재해야 한다
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY : n+1 이라는 문제가 발생할 수 있어서 추가
    @JoinColumn(name="author_id")                        // post 가 유저를 체크하기위한 용도
    private User author;                                 // JoinColum 을 하게되면 원래 자동으로 user_id 로 생성되지만
                                                         // 이것의 이름을 author_id 로 수동으로 생성해주었다.

    // Post 와 Comment 의 관계 (양방향 이므로 양쪽 모두 연결되어야 한다.) n:1 에서 1 의 관점
    // mappedBy : comment 의 post 와 관계됨, cascade : 나(post), orphan : 관계(comment)
    // cascade : post 가 삭제될때 어떻게 할지 , orphan : comment 가 삭제될때 어떻게 할지
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // 보통 이 세가지 조건을 준다.
    @OrderBy("createdAt ASC")                                                      // 생성일자 기준 오름차순 정렬
    private List<Comment> comments;                                                // 갯수가 많으니 List 를 사용한다.
}