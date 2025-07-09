package com.example.untitled28.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
                                        // author 는 항상 존재해야 한다 = User 는 항상 존재해야 한다
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // n+1 이라는 문제가 발생할 수 있어서 추가
    @JoinColumn(name="author_id")                        // post 가 유저를 체크하기위한 용도
    private User author; // JoinColum 을 하게되면 원래 자동으로 user_id 로 생성되지만
}                        // 이것의 이름을 author_id 로 수동으로 생성해주었다.