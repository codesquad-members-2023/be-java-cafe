package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> store = new ArrayList<>();

    @Override
    public boolean save(User user) {
        // 로직 변경으로 인해 에러 발생 제거 + 이전 기록 보관용
//        if (index == 0 && isIdDuplicate(user.getUserId())) {
//            // id 중복 체크후 저장
//            user.setUserNum(store.size() + 1);
//            store.add(user);
//
//            return true;
//        }
//        if (user.getUserNum() == index) {
//            // 인덱스 번호가 일치하면 회원정보 수정
//            store.set(Long.valueOf(index).intValue() - 1, user);
//
//            return true;
//        }
        return false;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.isIdEquals(userId))
                .findFirst();
    }

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
