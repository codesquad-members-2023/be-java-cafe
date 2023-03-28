package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    List<User> findAllUsers();

    User findUserById(long id);

    User findDBUser(String userId, String password);

    void update(User user, long id);
}
