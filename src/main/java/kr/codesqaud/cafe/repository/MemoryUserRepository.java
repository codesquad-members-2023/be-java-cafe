package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository {

    private final Map<Integer, User> users = new HashMap<>();
    private int sequence = 0;

    public void save(User user) {
        user.setId(++sequence);
        users.put(user.getId(), user);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

}
