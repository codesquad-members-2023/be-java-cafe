package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Queue<Member> store = new ConcurrentLinkedQueue<>();

    @Override
    public void save(Member user) {
        if (vaildName(user.getNickname())) {
            store.add(user);
        }
    }

    @Override
    public Optional<Member> findById(long userId) {
        return store.stream().filter(user -> user.equals(userId)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(store);
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public boolean vaildName(String userName) {
        return store.stream().noneMatch(e -> e.isNameEquals(userName));
    }
    
    @Override
    public void update(Member exMember, Member newUser) throws NoSuchElementException {
        exMember.update(newUser);
    }
}
