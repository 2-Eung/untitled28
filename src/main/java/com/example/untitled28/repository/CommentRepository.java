package com.example.untitled28.repository;

import com.example.untitled28.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA 는 인터페이스로 만든다. (클래스로 만들어도 실행은 된다. 단, JPA 에서 제공하는 기능은 작동안한다.)
// 모델 명, Id 값 의 타입(기본키)
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
