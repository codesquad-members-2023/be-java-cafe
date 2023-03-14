package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public void join(User user) {
        if (!repository.vaildName(user.getNickname())) {
            throw new IllegalArgumentException("중복된 이름이 있습니다.");
        }
            repository.save(user);
    }

    @Override
    public User findUser(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        return repository.findById(id).get();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
