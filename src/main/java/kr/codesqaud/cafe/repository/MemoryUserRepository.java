package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> store = new ArrayList<>();

    @Override
    public boolean save(User user, long index) {
        if (index == 0 && isIdDuplicate(user.getUserId())) {
            // id 중복 체크후 저장
            user.setUserNum(store.size() + 1);
            store.add(user);

            return true;
        }
        if (user.getUserNum() == index) {
            // 인덱스 번호가 일치하면 회원정보 수정
            store.set(Long.valueOf(index).intValue() - 1, user);

            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findById(String userId) {
        return store.stream()
                .filter(user -> user.isIdEquals(userId))
                .findFirst();
    }

//    @Override
//    public Optional<User> findByName(String name) {
//        return store.values().stream()
//                .filter(user -> user.getName().equals(name))
//                .findAny();
//    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }

    public boolean isIdDuplicate(String userId) {
        return store.stream().noneMatch(user -> user.isIdEquals(userId));
    }

    public void clearStore() {
        store.clear();
    }
}
