package com.example.untitled28.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity                       // JPA 필수구성요소
@Table(name="users")          // user 라는 이름으로 테이블이 생성됨
@Data                         // 게터, 세터, final 등 편의성 제공
@NoArgsConstructor            // 스프링부트 가 필요로할 수도 있는 생성자
@AllArgsConstructor           // 우리가 사용해야할 생성자
@Builder                      // 각 필드에 대입하기 쉽게해줌 (선택적 대입도 가능)
public class User {
    @Id                                                 // 이것이 이 클래스 ( 모델 ) 의 Id 역할
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이전처럼 1 씩증가하는 방식
    private Integer id;

    // 일반적인 것들은 Column [컬럼] 이라고 한다.
    @Column(nullable = false, unique = true, length = 50) // 비어있으면 안되고, 유니크한 값이고, 길이는 50 이내
    private String username;                              // 데이터베이스 선에서의 조건

    @Column(nullable = false)
    private String password;
}
