package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.user.User;

public interface SignUpService {
    void join(User user);

    User findById(String userId);
}
