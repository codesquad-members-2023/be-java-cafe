package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    List<User> findAllUsers();

    User findUserById(long id);

    Optional<User> findUserWithMatchedPassword(String userId, String password);

    void update(User user, long id);
}
