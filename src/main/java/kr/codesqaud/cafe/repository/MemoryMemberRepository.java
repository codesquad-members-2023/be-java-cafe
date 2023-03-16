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
    public Optional<User> findById(String memberId) {
        return store.stream()
                .filter(user -> user.getUserId().equals(memberId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return store;
    }

}
