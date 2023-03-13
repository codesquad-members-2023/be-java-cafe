package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRepository {
    private static Map<Long, User> store = new HashMap<>();

    

}
