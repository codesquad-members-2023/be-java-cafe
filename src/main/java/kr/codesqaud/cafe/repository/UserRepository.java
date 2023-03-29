package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public void join(User user);

    public Optional<User> findUserById(String userId);

    public List<User> findAll();

    public int update(String userId, User user) ;

}
