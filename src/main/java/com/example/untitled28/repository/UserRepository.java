package com.example.untitled28.repository;

import com.example.untitled28.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JPA 는 인터페이스로 만든다. (클래스로 만들어도 실행은 된다. 단, JPA 에서 제공하는 기능은 작동안한다.)
// 모델 명, Id 값 의 타입(기본키)
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); // JPA 가 자동으로 만들어준다
    // 유저가 있거나 없을수도 있어서 Optional 을 사용했다. (조회결과가 없을수도 있기때문)
}
