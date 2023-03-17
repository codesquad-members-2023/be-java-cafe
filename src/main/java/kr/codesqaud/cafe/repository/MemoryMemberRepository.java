package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private List<User> store = new ArrayList<>();

    @Override
    public void save(User user) {
        store.add(user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return store.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return store;
    }

    @Override
    public void updateUser(String userId, User updateUser) {
        User user = findById(userId).get();
        user.setName(updateUser.getName());
        user.setUserId(updateUser.getUserId());
        user.setPassword(updateUser.getPassword());
        user.setEmail(updateUser.getEmail());
    }

}
