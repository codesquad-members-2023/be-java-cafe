package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<String, User> store = new HashMap<>();
    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public User findById(String memberId) {
        return store.get(memberId);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    // 테스트용
    public void clear() {
        store.clear();
    }
}
