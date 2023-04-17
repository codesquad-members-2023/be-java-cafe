package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.UserUpdateForm;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findById(int id);

    User findByUserId(String userId);

    List<User> findAll();

    void update(int id, UserUpdateForm userUpdateForm);
}
