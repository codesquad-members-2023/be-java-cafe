package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.model.User;

public interface JoinService {
    void join(User user);
    User lookupUser(String userId);
    List<User> lookupAllUser();
    void updateUser(String userId, String password, String newPassword, String name, String email);
}
