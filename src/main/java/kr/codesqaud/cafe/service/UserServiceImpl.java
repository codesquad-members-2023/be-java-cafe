package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository repository = new MemoryUserRepository();

    @Override
    public void join(User user) {
        repository.save(user);
    }

    @Override
    public User findUser(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
