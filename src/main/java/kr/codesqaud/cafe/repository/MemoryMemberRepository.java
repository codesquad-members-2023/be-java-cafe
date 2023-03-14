package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private List<User> store = new ArrayList<>();
    private int sequence = 0;
    @Override
    public void save(User user) {
        user.setSequence(++sequence);
        store.add(user);
    }

    @Override
    public Optional<User> findById(String memberId) {
        return store.stream()
                .filter(user -> user.getName().equals(memberId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return store;
    }

    // 테스트용
    public void clear() {
        store.clear();
    }
}
