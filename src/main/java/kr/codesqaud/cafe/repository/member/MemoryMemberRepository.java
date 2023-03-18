package kr.codesqaud.cafe.repository.member;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.member.MemberRepository;
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
    public User findById(String userId) {
        return store.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .get();
    }

    @Override
    public List<User> findAll() {
        return store;
    }

    @Override
    public void updateUser(String userId, User updateUser) {
        User user = findById(userId);
        user.setName(updateUser.getName());
        user.setUserId(updateUser.getUserId());
        user.setPassword(updateUser.getPassword());
        user.setEmail(updateUser.getEmail());
    }

}
