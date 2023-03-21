package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository {

    public void join(User user);

    public User findUser(String userId);

    public List<User> findAll();

    public int update(User user) ;

}
