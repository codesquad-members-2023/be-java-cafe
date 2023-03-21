package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository {

    public void save(User user);

    public List<User> findAllUsers();

    public User findByUserId(String userId);
}
