package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.user.User;

public interface JoinService {
    void join(User user);
    User lookupUser(String userId);
}
