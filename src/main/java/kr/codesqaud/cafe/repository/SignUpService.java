package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.List;

public interface SignUpService {
    void join(User user);

    User findById(String userId);

    List<User> findAll();
}
