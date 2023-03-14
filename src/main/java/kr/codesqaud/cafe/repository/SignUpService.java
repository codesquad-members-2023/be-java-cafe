package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.List;
import java.util.Optional;

public interface SignUpService {
    void join(User user);

    Optional<User> findById(String userId);

    List<User> findAll();
}
