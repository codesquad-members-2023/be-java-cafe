package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.basic.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public void join(UserDTO userDTO);

    public Optional<User> findUserById(String userId);

    public List<User> findAll();

    public int update(UserDTO userDTO) ;

}
