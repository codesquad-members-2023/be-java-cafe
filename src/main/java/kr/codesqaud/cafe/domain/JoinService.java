package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.user.User;

public interface JoinService {
    void join(User user);
    Optional<User> lookupUser(String userId);
    List<User> lookupAllUser();
}
