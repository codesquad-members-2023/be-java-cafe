package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String userId);

    Optional<User> findByName(String userName);
    List<User> findAll(); // 모든 회원 리스트 반환
}
