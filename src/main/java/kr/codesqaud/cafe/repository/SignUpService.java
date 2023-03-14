package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

public interface SignUpService {
    void join(User user);

    User findById(String userId);
}
